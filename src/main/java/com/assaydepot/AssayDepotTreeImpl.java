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
import com.assaydepot.result.BaseResult;
import com.assaydepot.result.Provider;
import com.assaydepot.result.ProviderRef;
import com.assaydepot.result.ProviderResult;
import com.assaydepot.result.Results;

public class AssayDepotTreeImpl implements AssayDepot {

	private static final String BASE_URL_STRING = "https://www.assaydepot.com/api/providers.json";
	private Configuration conf;
	
	AssayDepotTreeImpl( Configuration conf ) {
		this.conf = conf;
	}

	public Results getProviderRefs(String query) throws JsonParseException, IOException {
		StringBuilder urlBuilder = new StringBuilder( BASE_URL_STRING );
		if( query != null ) {
			urlBuilder.append( "?q=" ).append( query );
		}
		if( conf.getApiToken() != null ) {
			urlBuilder.append( "&access_token=" ).append( conf.getApiToken() );
		}

		ObjectMapper mapper = new ObjectMapper();
		URL url = new URL( urlBuilder.toString() );
		JsonFactory f = new JsonFactory();
		JsonParser jp = f.createJsonParser( url.openStream() );
		JsonNode rootNode = mapper.readTree( jp );
		

		Results results = new Results();
		results.setProviderRefs( new ArrayList<ProviderRef>() );
		results.setProviders( new ArrayList<Provider>() );
		results.setFacets( new HashMap<String,Map<String,String>>() );

		results.setTotal( rootNode.path( "total" ).getIntValue() );
		results.setPage( rootNode.path( "page" ).getIntValue() );
		results.setPerPage( rootNode.path( "per_page" ).getIntValue() );
		results.setQueryTime( rootNode.path( "query_time" ).getDoubleValue() );

		doFacets( rootNode.path( "facets"), results );
	
		doProviderRefs( rootNode.path( "provider_refs" ), results );
		
		return results;
	}

	private void doProviderRefs( JsonNode pRefNode, Results results ) {
		ProviderRef newRef = null;
		int numRefs = pRefNode.size();
		JsonNode node = null;
		for( int i=0; i < numRefs; i++ ) {
			node = pRefNode.get( i );
			newRef = new ProviderRef();
			newRef.setId( node.path("id").getTextValue() );
			newRef.setSlug( node.path("slug").getTextValue() );
			newRef.setName( node.path("name").getTextValue() );
			newRef.setSnippet( node.path("snippet").getTextValue() );
			newRef.setPermission( node.path("permission").getTextValue() );
			newRef.setScore( node.path("score").getDoubleValue() );
			doLocations( node.path( "locations" ), newRef );
			doUrls( node.path( "urls" ), newRef );
			
			results.getProviderRefs().add( newRef );
		}
	}
	
	private void doLocations( JsonNode locNode, ProviderResult pResult ) {
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
		pResult.setLocations( locList ); 
	}
	
	private void doUrls( JsonNode urlNode, BaseResult baseResult ) {
		Iterator<String> fieldNames = urlNode.getFieldNames();
		String fieldName = null;
		Map<String,String> urlMap = new HashMap<String,String>();
		while( fieldNames.hasNext() ) {
			fieldName = fieldNames.next();
			urlMap.put( fieldName, urlNode.path( fieldName ).getTextValue() );
		}
		baseResult.setUrls( urlMap );
	}
	
	private void doFacets( JsonNode facetNode, Results results ) {
		JsonNode innerNode = null;
		Iterator<String> fieldNames = facetNode.getFieldNames();
		String fieldName = null;
		Iterator<String> innerFieldNames = null;
		String innerName = null;

		while( fieldNames.hasNext() ) {
			fieldName = fieldNames.next();
			results.getFacets().put( fieldName, new HashMap<String,String>() );
			innerNode = facetNode.path( fieldName );
			innerFieldNames = facetNode.path( fieldName ).getFieldNames();
			while( innerFieldNames.hasNext() ) {
				innerName = innerFieldNames.next();
				// must use .asText() here because getTextValue() returns nulls for numbers and such
				results.getFacets().get( fieldName ).put( innerName, innerNode.path( innerName ).asText() );
			}
		}
	}

	public Results getWares(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
