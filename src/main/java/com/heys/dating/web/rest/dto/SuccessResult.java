package com.heys.dating.web.rest.dto;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class SuccessResult {

	@XmlElement
	private boolean success;

	@XmlElement
	private List<String> links;

	public SuccessResult(final boolean success, final String... links) {
		this.success = success;
		this.links = Arrays.asList(links);
	}

	public List<String> getLinks() {
		return links;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setLinks(final List<String> links) {
		this.links = links;
	}

	public void setSuccess(final boolean success) {
		this.success = success;
	}

}
