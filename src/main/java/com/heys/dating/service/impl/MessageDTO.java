package com.heys.dating.service.impl;

public class MessageDTO {
	private String from;
	private String status;
	private String text;

	public String getFrom() {
		return from;
	}

	public String getStatus() {
		return status;
	}

	public String getText() {
		return text;
	}

	public void setFrom(final String from) {
		this.from = from;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public void setText(final String text) {
		this.text = text;
	}
}
