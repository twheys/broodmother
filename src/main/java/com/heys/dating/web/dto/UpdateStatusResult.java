package com.heys.dating.web.dto;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class UpdateStatusResult extends SuccessResult {

	public UpdateStatusResult(final String pictureKey) {
		super(true);
	}
}