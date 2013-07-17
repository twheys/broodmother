package com.heys.dating.domain;

import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import com.googlecode.objectify.Key;

public interface Repository<TType extends AbstractEntity> {

	int count();

	void delete(final Iterable<TType> entities);

	void delete(final Long id);

	void delete(final TType entity);

	boolean exists(final Long id);

	Map<Key<TType>, TType> findAll(final List<Key<TType>> keys);

	TType findOne(final Key<TType> key);

	TType findOne(final Long id);

	Map<Key<TType>, TType> save(@Valid final Iterable<TType> entities);

	TType save(@Valid final TType entity);

	Iterable<TType> saveAsync(@Valid final Iterable<TType> entities);

	TType saveAsync(@Valid final TType entity);

	void validate(final TType entity, final Class<?>... groups)
			throws ConstraintViolationException;

}