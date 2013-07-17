package com.heys.dating.domain.member;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.heys.dating.domain.AbstractEntity;

@Entity
@Cache
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Gallery extends AbstractEntity {
	private static final long serialVersionUID = 7282661284457916312L;

	private String name;
	private List<Key<Picture>> pictures = Lists.newArrayList();
}
