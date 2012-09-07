package com.assaydepot;

import java.util.Collection;
import java.util.List;

import com.assaydepot.result.Provider;
import com.assaydepot.result.Results;
import com.assaydepot.result.Ware;

public interface AssayDepot {
  
	public Results getProviderRefs( String query );
	public Results getProviderRefsByFacets( List<String> facetNames, List<String> facetValues, String query );
	public Provider getProvider( String id );
	public Results getWareRefs( String query );
	public Results getWareRefsByFacets( List<String> facetNames, List<String> facetValues, String query );
	public Ware getWare( String id );
	public Collection<String> getAvailableWareRefFacetNames();	
	public Collection<String> getAvailableProviderRefFacetNames();	
}
