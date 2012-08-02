package com.assaydepot;

import java.net.URL;
import java.util.ArrayList;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import com.assaydepot.conf.Configuration;
import com.assaydepot.result.Provider;
import com.assaydepot.result.ProviderRef;
import com.assaydepot.result.Results;

public class AssayDepotImpl implements AssayDepot {

	private static final String BASE_URL_STRING = "https://www.assaydepot.com/api/providers.json";
	private Configuration conf;
	
	AssayDepotImpl( Configuration conf ) {
		
	}
	public ProviderRef getProvidersByQuery( String query ) {
		StringBuilder urlBuilder = new StringBuilder( BASE_URL_STRING );
		if( query != null ) {
			urlBuilder.append( "?q=" ).append( query );
		}
		if( conf.getApiToken() != null ) {
			urlBuilder.append( "&auth_token=" ).append( conf.getApiToken() );
		}

		URL url = new URL( urlBuilder.toString() );
		JsonFactory f = new JsonFactory();
		JsonParser jp = f.createJsonParser( url.openStream() );
		
		Results results = new Results();
		results.setProviderRefs( new ArrayList<ProviderRef>() );
		results.setProviders( new ArrayList<Provider>() );
		
		jp.nextToken(); // will return JsonToken.START_OBJECT (verify?)
		while (jp.nextToken() != JsonToken.END_OBJECT) {
			String fieldName = jp.getCurrentName();
			jp.nextToken(); // move to value, or START_OBJECT/START_ARRAY
			//
			// Build the Results object
			//
			if( "total".equals( fieldName )) {
				results.setTotal( jp.getIntValue() );
			} else if( "page".equals( fieldName )) {
				results.setPage( jp.getIntValue() );
			}
			if ("total".equals(fieldname)) { // contains an object
				while (jp.nextToken() != JsonToken.END_OBJECT) {
					String namefield = jp.getCurrentName();
					jp.nextToken(); // move to value
					if ("first".equals(namefield)) {
						name.setFirst(jp.getText());
					} else if ("last".equals(namefield)) {
						name.setLast(jp.getText());
					} else {
						throw new IllegalStateException("Unrecognized field '"+fieldname+"'!");
					}
				}
				user.setName(name);
			} else if ("gender".equals(fieldname)) {
				user.setGender(User.Gender.valueOf(jp.getText()));
			} else if ("verified".equals(fieldname)) {
				user.setVerified(jp.getCurrentToken() == JsonToken.VALUE_TRUE);
			} else if ("userImage".equals(fieldname)) {
				user.setUserImage(jp.getBinaryValue());
			} else {
				throw new IllegalStateException("Unrecognized field '"+fieldname+"'!");
			}
		}
		jp.close(); // ensure resources get cleaned up timely and properly
		
	}

}
