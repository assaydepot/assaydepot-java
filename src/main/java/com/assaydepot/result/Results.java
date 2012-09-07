package com.assaydepot.result;

import java.util.List;
import java.util.Map;

public class Results {

	private Integer total;
	private Integer page;
	private Integer perPage;
	private Double queryTime;
	private Map<String,Map<String,String>> facets;
	private List<WareRef> wareRefs;
	private List<ProviderRef> providerRefs;
	private List<Ware> wares;
	private List<Provider> providers;
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPerPage() {
		return perPage;
	}
	public void setPerPage(Integer perPage) {
		this.perPage = perPage;
	}
	public Double getQueryTime() {
		return queryTime;
	}
	public void setQueryTime(Double queryTime) {
		this.queryTime = queryTime;
	}
	public Map<String,Map<String, String>> getFacets() {
		return facets;
	}
	public void setFacets(Map<String,Map<String, String>> facets) {
		this.facets = facets;
	}
	public List<WareRef> getWareRefs() {
		return wareRefs;
	}
	public void setWareRefs(List<WareRef> wareRefs) {
		this.wareRefs = wareRefs;
	}
	public List<ProviderRef> getProviderRefs() {
		return providerRefs;
	}
	public void setProviderRefs(List<ProviderRef> providerRefs) {
		this.providerRefs = providerRefs;
	}	
	
}
