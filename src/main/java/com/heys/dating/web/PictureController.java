package com.heys.dating.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/p")
public interface PictureController {

	@RequestMapping(value = "/{login}/full.png", method = RequestMethod.GET, produces = "image/png")
	byte[] viewFullProfilePng(@PathVariable String login);

	@RequestMapping(value = "/{login}/icon.png", method = RequestMethod.GET, produces = "image/png")
	byte[] viewIconProfilePng(@PathVariable String login);

	@RequestMapping(value = "/{login}/preview.png", method = RequestMethod.GET, produces = "image/png")
	byte[] viewPreviewProfilePng(@PathVariable String login);

}
