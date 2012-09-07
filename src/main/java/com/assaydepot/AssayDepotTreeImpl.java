package com.assaydepot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.assaydepot.conf.Configuration;
import com.assaydepot.result.Provider;
import com.assaydepot.result.ProviderRef;
import com.assaydepot.result.Results;
import com.assaydepot.result.Ware;
import com.assaydepot.result.WareRef;

public class AssayDepotTreeImpl implements AssayDepot {

	private static final String BASE_PROVIDER_REF_QUERY_URL = "https://www.assaydepot.com/api/providers.json";
	private static final String BASE_WARE_REF_QUERY_URL = "https://www.assaydepot.com/api/wares.json";
	private static final String BASE_PROVIDER_URL = "https://www.assaydepot.com/api/providers/";
	private static final String BASE_WARE_URL = "https://www.assaydepot.com/api/wares/";
	
	private static final Logger log = Logger.getLogger( AssayDepot.class );
	private Configuration conf;
	
	AssayDepotTreeImpl( Configuration conf ) {
		this.conf = conf;
	}
	
	public Results getProviderRefsByFacets( List <String> facetNames, List<String> facetValues, String query ) {
		StringBuilder urlBuilder = new StringBuilder( BASE_PROVIDER_REF_QUERY_URL );
		if( conf.getApiToken() != null ) {
			urlBuilder.append( "?access_token=" ).append( conf.getApiToken() );
		}
		urlBuilder.append( buildFacetString( facetNames, facetValues ));
		if( query != null ) {
			urlBuilder.append( "?q=" ).append( query );
		}
		return getProviderRefsByURL( urlBuilder.toString() );
	}
	
	public Results getWareRefsByFacets( List<String> facetNames, List<String> facetValues, String query ) {
		StringBuilder urlBuilder = new StringBuilder( BASE_WARE_REF_QUERY_URL );
		if( conf.getApiToken() != null ) {
			urlBuilder.append( "?access_token=" ).append( conf.getApiToken() );
		}
		urlBuilder.append( buildFacetString( facetNames, facetValues ));
		if( query != null ) {
			urlBuilder.append( "?q=" ).append( query );
		}
		return getWareRefsByURL( urlBuilder.toString() );
	}
	

	public Provider getProvider( String id )  {
		StringBuilder urlBuilder = new StringBuilder( BASE_PROVIDER_URL );
		if( id != null ) {
			urlBuilder
				.append( id )
				.append( ".json?access_token=" )
				.append( conf.getApiToken() );
			
		}
		
		JsonNode rootNode = doParseURL( urlBuilder.toString() );
		JsonNode pNode = rootNode.path( "provider" );
		Provider provider = new Provider();
		provider.setServiceAreas( new ArrayList<String>() );
		provider.setLaboratories( new ArrayList<String>() );
		provider.setKeywords( new ArrayList<String>() );
		provider.setCertifications( new ArrayList<String>() );
		provider.setProfessionalAssociations( new ArrayList<String>() );
		
		provider.setId( pNode.path( "id" ).getTextValue() );
		provider.setSlug( pNode.path( "slug" ).getTextValue() );
		provider.setName( pNode.path( "name" ).getTextValue() );
		provider.setPhoneNumber( pNode.path( "phone_number" ).getTextValue() );
		provider.setWebsite( pNode.path( "website" ).getTextValue() );
		provider.setServiceAreas( doArray( pNode.path( "service_areas" )));
		provider.setHeadquarters( pNode.path( "headquarters" ).getTextValue() );
		provider.setLaboratories( doArray( pNode.path( "laboratories" )));
		provider.setYearEstablished( pNode.path( "year_established" ).getIntValue() );
		provider.setNumEmployees( pNode.path( "number_of_employees" ).asText() );
		provider.setDescription( pNode.path( "description" ).getTextValue() );
		provider.setKeywords( doArray( pNode.path( "keywords" )));
		provider.setCertifications( doArray( pNode.path( "certifications" )));
		provider.setProfessionalAssociations( doArray( pNode.path( "professional_associations" )));
		provider.setPermission( pNode.path( "permission" ).getTextValue() );
		provider.setOrigin( pNode.path( "origin" ).getTextValue() );
		provider.setGreen( pNode.path( "green" ).asBoolean() );
		provider.setGreenExplanation( pNode.path( "green_explaination" ).getTextValue() );
		provider.setDiversity( pNode.path( "diversity" ).asBoolean() );
		provider.setDiversityExplanation( pNode.path( "diversity_explaination" ).getTextValue() );
		provider.setCreatedAt( pNode.path( "created_at" ).asText() );
		provider.setUpdatedAt( pNode.path( "updated_at" ).asText() );
		provider.setUrls( doStringMap( pNode.path( "urls") ));
		
		return provider;
	}
	
	private List<String> doArray( JsonNode arrayNode ) {
		List<String> list = new ArrayList<String>();
		for( int i=0; i < arrayNode.size(); i++ ) {
			list.add( arrayNode.get( i ).getTextValue() );
		}
		return list;
	}
	
	
	public Results getProviderRefs(String query) {
		StringBuilder urlBuilder = new StringBuilder( BASE_PROVIDER_REF_QUERY_URL );
		if( query != null ) {
			urlBuilder.append( "?q=" ).append( query );
		}
		if( conf.getApiToken() != null ) {
			urlBuilder.append( "&access_token=" ).append( conf.getApiToken() );
		}

		return getProviderRefsByURL( urlBuilder.toString() );
	}
	
	private Results getProviderRefsByURL( String url ) {
		JsonNode rootNode = doParseURL( url );		

		Results results = new Results();

		results.setTotal( rootNode.path( "total" ).getIntValue() );
		results.setPage( rootNode.path( "page" ).getIntValue() );
		results.setPerPage( rootNode.path( "per_page" ).getIntValue() );
		results.setQueryTime( rootNode.path( "query_time" ).getDoubleValue() );
		results.setFacets( doFacets( rootNode.path( "facets" )));
		results.setProviderRefs( doProviderRefs( rootNode.path( "provider_refs" )));
		
		return results;
		
	}
	private JsonNode doParseURL( String urlString ) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = null;
		URL url = null;
		try {
			url = new URL( urlString );
			JsonFactory f = new JsonFactory();
			JsonParser jp = f.createJsonParser( url.openStream() );
			rootNode = mapper.readTree( jp );
		} catch (JsonProcessingException jpex ) {
			log.error("Problem accessing json are you getting json back on this url ["+urlString+"] ?", jpex);
		} catch (MalformedURLException malex) {
			log.error( "Problem accessing url ["+urlString+"]", malex );
		} catch (IOException ioex ) {
			if( ioex.getMessage().contains( "500" ) && ioex.getMessage().contains("HTTP response")) {
				log.error( "It's possible the facet you are requesting does not exist for this type or the server is busy.", ioex );
			} else {
				log.error("Most likely problem with url or problem accessing internet", ioex );
			}
		}
		return rootNode;
	}
	
	private List<ProviderRef> doProviderRefs( JsonNode pRefNode ) {
		ProviderRef newRef = null;
		int numRefs = pRefNode.size();
		JsonNode node = null;
		List<ProviderRef> providerRefs = new ArrayList<ProviderRef>();
		for( int i=0; i < numRefs; i++ ) {
			node = pRefNode.get( i );
			newRef = new ProviderRef();
			newRef.setId( node.path("id").getTextValue() );
			newRef.setSlug( node.path("slug").getTextValue() );
			newRef.setName( node.path("name").getTextValue() );
			newRef.setSnippet( node.path("snippet").getTextValue() );
			newRef.setPermission( node.path("permission").getTextValue() );
			newRef.setScore( node.path("score").getDoubleValue() );
			newRef.setLocations( doLocations( node.path( "locations" )));
			newRef.setUrls( doStringMap( node.path( "urls" ) ));
			
			providerRefs.add( newRef );
		}
		return providerRefs;
	}
	
	/**
	 * Takes in a list of names/values and pairs them up to make a good url.  if the list
	 * sizes don't match up does the best it can by matching up the smallest number of pairs.
	 * 
	 * @param facetNames
	 * @param facetValues
	 * @return
	 */
	private String buildFacetString(List<String> facetNames, List<String>facetValues ) {
		
		StringBuilder builder = new StringBuilder();

		if( facetNames != null && facetValues != null ) 
		{
			int pairsCount = -1;
			if( facetNames.size() != facetValues.size() ) {
				pairsCount = facetNames.size() > facetValues.size() ? facetValues.size() : facetNames.size();
				log.warn( "facetNames and facetValues lists were of different sizes, your query may not be accurate" );
			} else {
				pairsCount = facetNames.size();
			}
			for( int i=0; i < pairsCount; i++ ) {
				try {
					builder.append( "&facets[").append( facetNames.get( i )).append( "][]=" )
						.append( URLEncoder.encode( facetValues.get( i ), "UTF-8" ));
				} catch (UnsupportedEncodingException ignore) {
				}
			}

		} else {
			log.error( "facetNames or facetValues was null, you are defaulting to a regular query using no facet matching" );
		}

		return builder.toString();
	}
	
	private List<Map<String,String>> doLocations( JsonNode locNode ) {
		int numLocs = locNode.size();
		List <Map<String,String>> locList = new ArrayList<Map<String,String>>( numLocs );
		Map<String,String> locMap = null;
		JsonNode node = null;
		for( int i=0; i < numLocs; i++ ) {
			node = locNode.get( i );
			locMap = new HashMap<String,String>();
			locMap.put( "text", node.path( "text" ).asText() );
			locMap.put( "latitude", node.path( "latitude" ).asText() );
			locMap.put( "longitude", node.path( "longitude" ).asText() );
			locList.add( locMap );
		}
		return locList; 
	}
	
	private Map<String,String> doStringMap( JsonNode node ) {
		Iterator<String> fieldNames = node.getFieldNames();
		String fieldName = null;
		Map<String,String> urlMap = new HashMap<String,String>();
		while( fieldNames.hasNext() ) {
			fieldName = fieldNames.next();
			urlMap.put( fieldName, node.path( fieldName ).getTextValue() );
		}
		return urlMap;
	}
	
	private Map<String,Map<String,String>> doFacets( JsonNode facetNode ) {
		JsonNode innerNode = null;
		Iterator<String> fieldNames = facetNode.getFieldNames();
		String fieldName = null;
		Iterator<String> innerFieldNames = null;
		String innerName = null;
		Map<String,Map<String,String>> facets = new HashMap<String,Map<String,String>>();

		while( fieldNames.hasNext() ) {
			fieldName = fieldNames.next();
			facets.put( fieldName, new HashMap<String,String>() );
			innerNode = facetNode.path( fieldName );
			innerFieldNames = facetNode.path( fieldName ).getFieldNames();
			while( innerFieldNames.hasNext() ) {
				innerName = innerFieldNames.next();
				// must use .asText() here because getTextValue() returns nulls for numbers and such
				facets.get( fieldName ).put( innerName, innerNode.path( innerName ).asText() );
			}
		}
		return facets;
	}

	public Results getWareRefs(String query) {
			StringBuilder urlBuilder = new StringBuilder( BASE_WARE_REF_QUERY_URL );
			if( query != null ) {
				urlBuilder.append( "?q=" ).append( query );
			}
			if( conf.getApiToken() != null ) {
				urlBuilder.append( "&access_token=" ).append( conf.getApiToken() );
			}
			return getWareRefsByURL( urlBuilder.toString() );
	}
	
	private Results getWareRefsByURL( String url ) {
		JsonNode rootNode = doParseURL( url );		

		Results results = new Results();

		results.setTotal( rootNode.path( "total" ).getIntValue() );
		results.setPage( rootNode.path( "page" ).getIntValue() );
		results.setPerPage( rootNode.path( "per_page" ).getIntValue() );
		results.setQueryTime( rootNode.path( "query_time" ).getDoubleValue() );
		results.setFacets( doFacets( rootNode.path( "facets" )));
		results.setWareRefs( doWareRefs( rootNode.path( "ware_refs" )));
		
		return results;		
	}
	
	private List<WareRef> doWareRefs( JsonNode pRefNode ) {
		WareRef newRef = null;
		int numRefs = pRefNode.size();
		JsonNode node = null;
		List<WareRef> wareRefs = new ArrayList<WareRef>();
		for( int i=0; i < numRefs; i++ ) {
			node = pRefNode.get( i );
			newRef = new WareRef();
			newRef.setId( node.path("id").getTextValue() );
			newRef.setSlug( node.path("slug").getTextValue() );
			newRef.setName( node.path("name").getTextValue() );
			newRef.setPrice( node.path("price").getDoubleValue() );
			newRef.setType( node.path("type").getTextValue() );
			newRef.setTurnAroundTime( doStringMap( node.path( "turn_around_time" )));
			newRef.setSnippet( node.path("snippet").getTextValue() );
			newRef.setProviderIds( doArray( node.path( "provider_ids" )));
			newRef.setProviderNames( doArray( node.path( "provider_names" )));
			newRef.setScore( node.path( "score" ).getDoubleValue());
			newRef.setUrls( doStringMap( node.path( "urls" ) ));
			
			wareRefs.add( newRef );
		}
		return wareRefs;
	}

	public Ware getWare(String id) {
		
		StringBuilder urlBuilder = new StringBuilder( BASE_WARE_URL );
		if( id != null ) {
			urlBuilder
				.append( id )
				.append( ".json?access_token=" )
				.append( conf.getApiToken() );
			
		}
		
		JsonNode rootNode = doParseURL( urlBuilder.toString() );
		JsonNode wNode = rootNode.path( "ware" );
		Ware ware = new Ware();
		
		ware.setId( wNode.path( "id" ).getTextValue() );
		ware.setSlug( wNode.path( "slug" ).getTextValue() );
		ware.setName( wNode.path( "name" ).getTextValue() );
		ware.setPrice( wNode.path( "price" ).getDoubleValue() );
		ware.setTurnAroundTime( doStringMap( wNode.path( "turn_around_time" )));
		ware.setType( wNode.path( "type" ).getTextValue() );
		ware.setSnippet( wNode.path("snippet").getTextValue() );
		ware.setKeywords( doArray( wNode.path( "keywords" )));
		ware.setPromoDescription( wNode.path( "promo_description" ).getTextValue() );
		ware.setContactEmails( doArray( wNode.path( "contact_emails" )));
		ware.setResponsibleEmails( doArray( wNode.path( "responsible_emails" )));
		ware.setPermission( wNode.path( "permission" ).getTextValue() );
		ware.setFirstPublishedAt( wNode.path( "first_published_at" ).asText());
		ware.setProteinType( wNode.path( "protein_type" ).getTextValue());
		ware.setClonality( wNode.path( "clonality" ).getTextValue() );
		ware.setClonality( wNode.path( "cell_source" ).getTextValue() );
		ware.setSpecies( wNode.path( "species" ).getTextValue() );
		ware.setTissue( wNode.path( "tissue" ).getTextValue() );
		ware.setAmount( wNode.path( "amount" ).getTextValue() );
		ware.setProteinTag( wNode.path( "protein_tag" ).getTextValue() );
		ware.setAntigenSpecies( wNode.path( "antigen_species" ).getTextValue() );
		ware.setProductApplications( doArray( wNode.path( "product_applications" )));
		ware.setIgType( wNode.path( "ig_type" ).getTextValue() );
		ware.setPurificationMethod( wNode.path( "purification_method" ).getTextValue() );
		ware.setPeptideType( wNode.path( "peptide_type" ).getTextValue() );
		ware.setCasNumber( wNode.path( "cas_number" ).asText() );
		ware.setUnspsc( wNode.path( "unspsc" ).getTextValue() );
		ware.setSupplierPartId( wNode.path( "supplier_part_id" ).asText() );		
		ware.setCreatedAt( wNode.path( "created_at" ).asText() );
		ware.setUpdatedAt( wNode.path( "updated_at" ).asText() );
		ware.setUrls( doStringMap( wNode.path( "urls") ));
		
		return ware;
	}

	public Collection<String> getAvailableWareRefFacetNames() {
		Results results = getWareRefs("antibody");
		return results.getFacets().keySet();
	}

	public Collection<String> getAvailableProviderRefFacetNames() {
		Results results = getProviderRefs("antibody");
		return results.getFacets().keySet();
	}

}
