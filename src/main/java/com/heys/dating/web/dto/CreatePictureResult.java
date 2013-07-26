package com.heys.dating.web.dto;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class CreatePictureResult extends SuccessResult {

	public CreatePictureResult(final String pictureKey) {
		super(true);
	}

}
