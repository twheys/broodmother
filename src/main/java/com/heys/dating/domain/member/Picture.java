package com.heys.dating.domain.member;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.google.appengine.api.datastore.ShortBlob;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.heys.dating.domain.AbstractEntity;

@Entity
@Cache
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Picture extends AbstractEntity {
	private static final long serialVersionUID = 8418671229417665202L;

	private ShortBlob full;
	private ShortBlob preview;
	private ShortBlob icon;
	private String description;
	private PictureType type;
	private FileType fileType;
	private PictureStatus state;
}
