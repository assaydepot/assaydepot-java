package com.assaydepot;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	
	public void testGetProviderRefsByFacet() throws Exception {
		Configuration conf = new Configuration();
		conf.setApiToken("5ae0a040967efe332d237277afb6beca");
		AssayDepot assayDepot = AssayDepotFactory.getAssayDepot( conf );
		List<String> facetNames = new ArrayList<String>();
		List<String> facetValues = new ArrayList<String>();
		facetNames.add( "countries_facet" );
		facetValues.add( "United States" );
		Results results = assayDepot.getProviderRefsByFacets(facetNames, facetValues, null );
		assertEquals( results.getTotal() > 3000, true );
		assertEquals( results.getFacets().containsKey( facetNames.get(0) ), true );
	
		int oldResultTotal = results.getTotal();
		results = assayDepot.getProviderRefsByFacets(facetNames, facetValues, "antibody");
		assertEquals( results.getTotal() < oldResultTotal, true );
		assertEquals( results.getFacets().containsKey( facetNames.get(0) ), true );

	}
	
	public void testGetWareRefsByFacet() throws Exception {
		Configuration conf = new Configuration();
		conf.setApiToken("5ae0a040967efe332d237277afb6beca");
		AssayDepot assayDepot = AssayDepotFactory.getAssayDepot( conf );
		List<String> facetNames = new ArrayList<String>();
		List<String> facetValues = new ArrayList<String>();
		facetNames.add( "countries" );
		facetValues.add( "United States" );
		Results results = assayDepot.getWareRefsByFacets(facetNames, facetValues, null );
		assertEquals( results.getTotal() > 200, true );
		assertEquals( results.getFacets().containsKey( facetNames.get(0) ), true );
		
		int oldResultTotal = results.getTotal();
		results = assayDepot.getWareRefsByFacets(facetNames, facetValues, "antibody" );
		assertEquals( results.getTotal() < oldResultTotal, true );
		assertEquals( results.getFacets().containsKey( facetNames.get(0) ), true );

	}

}
