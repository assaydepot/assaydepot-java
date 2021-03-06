package com.assaydepot.result;

import java.util.List;
import java.util.Map;

public class WareRef extends BaseResult {

	private Double price;
	private Map<String,String> turnAroundTime;
	private String Type;
	private String snippet;
	private List<String> providerIds;
	private List<String> providerNames;
	private Double score;
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Map<String,String> getTurnAroundTime() {
		return turnAroundTime;
	}
	public void setTurnAroundTime(Map<String,String> turnAroundTime) {
		this.turnAroundTime = turnAroundTime;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getSnippet() {
		return snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	public List<String> getProviderIds() {
		return providerIds;
	}
	public void setProviderIds(List<String> providerIds) {
		this.providerIds = providerIds;
	}
	public List<String> getProviderNames() {
		return providerNames;
	}
	public void setProviderNames(List<String> providerNames) {
		this.providerNames = providerNames;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	
}
