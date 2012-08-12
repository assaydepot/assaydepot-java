package com.assaydepot;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import com.assaydepot.conf.Configuration;
import com.assaydepot.result.Provider;
import com.assaydepot.result.ProviderRef;
import com.assaydepot.result.Results;

public class AssayDepotTreeImpl implements AssayDepot {

	private static final String BASE_PROVIDERS_QUERY_URL = "https://www.assaydepot.com/api/providers.json";
	private static final String BASE_PROVIDER_URL = "https://www.assaydepot.com/api/providers/";
	
	private Configuration conf;
	
	AssayDepotTreeImpl( Configuration conf ) {
		this.conf = conf;
	}

	public Provider getProvider( String id ) throws JsonParseException, IOException {
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
		provider.setServiceAreas( doGetArray( pNode.path( "service_areas" )));
		provider.setHeadquarters( pNode.path( "headquarters" ).getTextValue() );
		provider.setLaboratories( doGetArray( pNode.path( "laboratories" )));
		provider.setYearEstablished( pNode.path( "year_established" ).getIntValue() );
		provider.setNumEmployees( pNode.path( "number_of_employees" ).asText() );
		provider.setDescription( pNode.path( "description" ).getTextValue() );
		provider.setKeywords( doGetArray( pNode.path( "keywords" )));
		provider.setCertifications( doGetArray( pNode.path( "certifications" )));
		provider.setProfessionalAssociations( doGetArray( pNode.path( "professional_associations" )));
		provider.setPermission( pNode.path( "permission" ).getTextValue() );
		provider.setOrigin( pNode.path( "origin" ).getTextValue() );
		provider.setGreen( pNode.path( "green" ).asBoolean() );
		provider.setGreenExplanation( pNode.path( "green_explaination" ).getTextValue() );
		provider.setDiversity( pNode.path( "diversity" ).asBoolean() );
		provider.setDiversityExplanation( pNode.path( "diversity_explaination" ).getTextValue() );
		provider.setCreatedAt( pNode.path( "created_at" ).asText() );
		provider.setUpdatedAt( pNode.path( "updated_at" ).asText() );
		provider.setUrls( doUrls( pNode.path( "urls") ));
		
		return provider;
	}
	
	private List<String> doGetArray( JsonNode arrayNode ) {
		List<String> list = new ArrayList<String>();
		for( int i=0; i < arrayNode.size(); i++ ) {
			list.add( arrayNode.get( i ).getTextValue() );
		}
		return list;
	}
	
	public Results getProviderRefs(String query) throws JsonParseException, IOException {
		StringBuilder urlBuilder = new StringBuilder( BASE_PROVIDERS_QUERY_URL );
		if( query != null ) {
			urlBuilder.append( "?q=" ).append( query );
		}
		if( conf.getApiToken() != null ) {
			urlBuilder.append( "&access_token=" ).append( conf.getApiToken() );
		}

		JsonNode rootNode = doParseURL( urlBuilder.toString() );		

		Results results = new Results();
		results.setProviderRefs( new ArrayList<ProviderRef>() );

		results.setTotal( rootNode.path( "total" ).getIntValue() );
		results.setPage( rootNode.path( "page" ).getIntValue() );
		results.setPerPage( rootNode.path( "per_page" ).getIntValue() );
		results.setQueryTime( rootNode.path( "query_time" ).getDoubleValue() );
		results.setFacets( doFacets( rootNode.path( "facets" )));
		results.setProviderRefs( doProviderRefs( rootNode.path( "provider_refs" )));
		
		return results;
	}

	private JsonNode doParseURL( String urlString ) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		URL url = new URL( urlString );
		JsonFactory f = new JsonFactory();
		JsonParser jp = f.createJsonParser( url.openStream() );
		return mapper.readTree( jp );
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
			newRef.setUrls( doUrls( node.path( "urls" ) ));
			
			providerRefs.add( newRef );
		}
		return providerRefs;
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
	
	private Map<String,String> doUrls( JsonNode urlNode ) {
		Iterator<String> fieldNames = urlNode.getFieldNames();
		String fieldName = null;
		Map<String,String> urlMap = new HashMap<String,String>();
		while( fieldNames.hasNext() ) {
			fieldName = fieldNames.next();
			urlMap.put( fieldName, urlNode.path( fieldName ).getTextValue() );
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

	public Results getWares(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
