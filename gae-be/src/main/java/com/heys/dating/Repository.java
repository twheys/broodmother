package com.heys.dating;

import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import com.googlecode.objectify.Key;

public interface Repository<TType extends AbstractEntity> {

	int count();

	boolean exists(final Long id);

	Map<Key<TType>, TType> findAll(final List<Key<TType>> keys);

	TType findOne(final Key<TType> key);

	TType findOne(final Long id);

	void validate(final TType entity, final Class<?>... groups)
			throws ConstraintViolationException;

	TType save(final TType entity);

	TType saveAsync(final TType entity);

	TType validateAndSave(final TType entity);

	TType validateAndSaveAsync(final TType entity);

	Map<Key<TType>, TType> save(final Iterable<TType> entities);

	Iterable<TType> saveAsync(final Iterable<TType> entities);

	void delete(final Iterable<Key<TType>> entities);

	void delete(final Key<TType> key);

	void delete(final Long id);

	void delete(final TType entity);
}