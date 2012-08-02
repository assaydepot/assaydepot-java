package com.assaydepot.result;

import java.util.Map;

public class ProviderRef extends BaseResult {

	private String snippet;
	private String permission;
	private Float score;
	private Map<String,String> locations;
	
	public String getSnippet() {
		return snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public Map<String, String> getLocations() {
		return locations;
	}
	public void setLocations(Map<String, String> locations) {
		this.locations = locations;
	}
	
	
}
