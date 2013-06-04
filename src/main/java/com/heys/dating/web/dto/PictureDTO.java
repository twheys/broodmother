package com.heys.dating.web.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.heys.dating.domain.member.FileType;
import com.heys.dating.domain.member.Picture;

@XmlRootElement(name = "picture")
public class PictureDTO {
	private byte[] file;
	private FileTypeDTO type;

	public PictureDTO(final Picture picture) {
		file = picture.getFull().getBytes();

		final FileType fileType = picture.getFileType();
		type = FileTypeDTO.valueOf(fileType.toString());
	}

	public byte[] getFile() {
		return file;
	}

	public FileTypeDTO getType() {
		return type;
	}

	public void setFile(final byte[] file) {
		this.file = file;
	}

	public void setType(final FileTypeDTO type) {
		this.type = type;
	}
}
