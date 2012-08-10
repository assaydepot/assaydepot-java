package com.assaydepot.result;

import java.util.List;
import java.util.Map;

public class ProviderResult extends BaseResult {
	
	private List< Map<String,String>> locations;
	
	public List< Map<String, String>> getLocations() {
		return locations;
	}
	public void setLocations(List< Map<String, String>> locations) {
		this.locations = locations;
	}

}
