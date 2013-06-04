package com.heys.dating.domain;

import java.util.List;

import javax.validation.ConstraintViolationException;

import com.googlecode.objectify.Key;

public interface Repository<TType extends AbstractEntity> {

	public int count();

	public void delete(Iterable<? extends TType> entities);

	public void delete(Long id);

	public void delete(TType entity);

	public boolean exists(Long id);

	public List<TType> findAll(List<Key<TType>> keys);

	public TType findOne(Key<TType> key);

	public TType findOne(Long id);

	public <S extends TType> Iterable<S> save(Iterable<S> entities);

	public <S extends TType> S save(S entity);

	public <S extends TType> Iterable<S> saveAsync(Iterable<S> entities);

	public <S extends TType> S saveAsync(S entity);

	void validate(TType entity) throws ConstraintViolationException;

}