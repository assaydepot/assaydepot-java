package com.assaydepot;

import junit.framework.TestCase;

import com.assaydepot.conf.Configuration;
import com.assaydepot.result.Provider;
import com.assaydepot.result.Results;
import com.assaydepot.result.Ware;

import java.net.URL;
import java.io.InputStream;
import java.util.Properties;

public class AssayDepotTest extends TestCase {
	protected Configuration conf;

  protected void setUp() throws Exception {
  	ClassLoader loader = Thread.currentThread().getContextClassLoader(); 
  	InputStream stream = loader.getResourceAsStream("assaydepot.properties");
  	Properties prop = new Properties();
  	prop.load(stream);
		conf = new Configuration();
		conf.setUrl(new URL(prop.getProperty("assaydepot.url")));
		conf.setApiToken(prop.getProperty("assaydepot.api_token"));
  }

	public void testProviderQuery() throws Exception {
		AssayDepot assayDepot = AssayDepotFactory.getAssayDepot( conf );
		Results  results = assayDepot.getProviderRefs( "antibody" );
		assertEquals( results.getFacets().size() > 0, true );
		assertEquals( results.getProviderRefs().size() > 0, true );
	}

	public void testGetProvider() throws Exception {
		String providerId = "5dad9ca114072e30801bc31de19fae1d";
		AssayDepot assayDepot = AssayDepotFactory.getAssayDepot( conf );
		Provider  provider = assayDepot.getProvider( providerId );
		assertEquals( provider.getId(), providerId );
		assertEquals( provider.getKeywords().size() > 0, true );
		assertEquals( provider.getCertifications().size() > 0, true );
		assertEquals( provider.getUrls().size() > 0, true );
	}

	public void testGetWareRefs() throws Exception {
		AssayDepot assayDepot = AssayDepotFactory.getAssayDepot( conf );
		Results  results = assayDepot.getWareRefs( "antibody" );
		assertEquals( results.getWareRefs().size() > 0, true );
		assertEquals( results.getFacets().size() > 0, true );
		assertEquals( results.getWareRefs().get(0).getUrls().size() > 0, true );
	}

	public void testGetWare() throws Exception {
		String wareId = "00ca892864cb56087711f438764ea31e";
		AssayDepot assayDepot = AssayDepotFactory.getAssayDepot( conf );
		Ware  ware = assayDepot.getWare( wareId );
		assertEquals( ware.getId(), wareId );
		assertEquals( ware.getSnippet().contains( "Take your antibody-based therapeutic"), true );
		assertEquals( ware.getTurnAroundTime().size() > 2, true );
		assertEquals( ware.getUrls().size() > 0, true );
	}
	
}
