package com.assaydepot.result;

import java.util.List;

public class WareRef extends BaseResult {

	private Float price;
	private Integer turnAroundTime;
	private String Type;
	private String snippet;
	private List<String> providerIds;
	private List<String> providerNames;
	private Float score;
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Integer getTurnAroundTime() {
		return turnAroundTime;
	}
	public void setTurnAroundTime(Integer turnAroundTime) {
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
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	
}
