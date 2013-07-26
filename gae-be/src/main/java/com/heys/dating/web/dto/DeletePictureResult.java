package com.heys.dating.web.dto;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class DeletePictureResult extends SuccessResult {

	public DeletePictureResult(final String pictureKey) {
		super(true);
	}
}