package com.assaydepot.result;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class Provider extends ProviderResult {

	private String phoneNumber;
	private String website;
	private List<String> serviceAreas;
	private String headquarters;
	private List<String> laboratories;
	private Integer yearEstablished;
	private String numEmployees;
	private String description;
	private String htmlDescription;
	private List<String> keywords;
	private List<String> certifications;
	private List<String> professionalAssociations;
	private String permission;
	private String origin;
	private Boolean green;
	private String greenExplanation;
	private Boolean diversity;
	private String diversityExplanation;
	private String createdAt;
	private String updatedAt;

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public List<String> getServiceAreas() {
		return serviceAreas;
	}
	public void setServiceAreas(List<String> serviceAreas) {
		this.serviceAreas = serviceAreas;
	}
	public String getHeadquarters() {
		return headquarters;
	}
	public void setHeadquarters(String headquarters) {
		this.headquarters = headquarters;
	}
	public List<String> getLaboratories() {
		return laboratories;
	}
	public void setLaboratories(List<String> laboratories) {
		this.laboratories = laboratories;
	}
	public Integer getYearEstablished() {
		return yearEstablished;
	}
	public void setYearEstablished(Integer yearEstablished) {
		this.yearEstablished = yearEstablished;
	}
	public String getNumEmployees() {
		return numEmployees;
	}
	public void setNumEmployees(String numEmployees) {
		this.numEmployees = numEmployees;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHtmlDescription() {
		return htmlDescription;
	}
	public void setHtmlDescription(String htmlDescription) {
		this.htmlDescription = htmlDescription;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	public List<String> getCertifications() {
		return certifications;
	}
	public void setCertifications(List<String> certifications) {
		this.certifications = certifications;
	}
	public List<String> getProfessionalAssociations() {
		return professionalAssociations;
	}
	public void setProfessionalAssociations(List<String> professionalAssociations) {
		this.professionalAssociations = professionalAssociations;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public Boolean getGreen() {
		return green;
	}
	public void setGreen(Boolean green) {
		this.green = green;
	}
	public String getGreenExplanation() {
		return greenExplanation;
	}
	public void setGreenExplanation(String greenExplanation) {
		this.greenExplanation = greenExplanation;
	}
	public Boolean getDiversity() {
		return diversity;
	}
	public void setDiversity(Boolean diversity) {
		this.diversity = diversity;
	}
	public String getDiversityExplanation() {
		return diversityExplanation;
	}
	public void setDiversityExplanation(String diversityExplanation) {
		this.diversityExplanation = diversityExplanation;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}	
	
}
