package com.assaydepot.result;

import java.util.Map;

public class BaseResult {

	private String id;
	private String slug;
	private String name;
	private Map<String,String> urls;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, String> getUrls() {
		return urls;
	}
	public void setUrls(Map<String, String> urls) {
		this.urls = urls;
	}
	
}
