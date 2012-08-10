package com.assaydepot;

import junit.framework.TestCase;

import com.assaydepot.conf.Configuration;
import com.assaydepot.result.Results;

public class AssayDepotTest extends TestCase {

	public void testProviderQuery() throws Exception {
		Configuration conf = new Configuration();
		conf.setApiToken("5ae0a040967efe332d237277afb6beca");
		AssayDepot assDeep = AssayDepotFactory.getAssayDepot( conf );
		Results  results = assDeep.getProviders( "antibody" );
		assertEquals( results.getFacets().size() > 0, true );
		assertEquals( results.getProviderRefs().size() > 0, true );
	}
}
