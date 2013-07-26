package com.heys.dating.impl.gae.repository;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.LoadType;
import com.heys.dating.AbstractEntity;
import com.heys.dating.Repository;

public class AbstractDatastoreRepository<TType extends AbstractEntity>
		implements Repository<TType> {

	protected final Class<TType> type;
	private Validator validator;

	public AbstractDatastoreRepository(final Class<TType> type) {
		ObjectifyService.register(type);
		this.type = type;

		final ValidatorFactory factory = Validation
				.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.heys.dating.domain.Repository#count()
	 */
	@Override
	public int count() {
		return load().count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.heys.dating.domain.Repository#delete(java.lang.Iterable)
	 */
	@Override
	public void delete(final Iterable<Key<TType>> keys) {
		ofy().delete().keys(keys).now();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.heys.dating.domain.Repository#delete(com.googlecode.objectify.Key)
	 */
	@Override
	public void delete(final Key<TType> key) {
		ofy().delete().key(key).now();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.heys.dating.domain.Repository#delete(java.lang.Long)
	 */
	@Override
	public void delete(final Long id) {
		ofy().delete().type(type).id(id).now();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.heys.dating.domain.Repository#delete(TType)
	 */
	@Override
	public void delete(final TType entity) {
		ofy().delete().entity(entity).now();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.heys.dating.domain.Repository#exists(java.lang.Long)
	 */
	@Override
	public boolean exists(final Long id) {
		return null != load().id(id).now();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.heys.dating.domain.Repository#findAll(java.util.List)
	 */
	@Override
	public Map<Key<TType>, TType> findAll(final List<Key<TType>> keys) {
		return ofy().load().keys(keys);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.heys.dating.domain.Repository#findOne(com.googlecode.objectify.Key)
	 */
	@Override
	public TType findOne(final Key<TType> key) {
		return ofy().load().key(key).now();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.heys.dating.domain.Repository#findOne(java.lang.Long)
	 */
	@Override
	public TType findOne(final Long id) {
		return load().id(id).now();
	}

	protected LoadType<TType> load() {
		return ofy().load().type(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.heys.dating.domain.Repository#save(java.lang.Iterable)
	 */
	@Override
	public Map<Key<TType>, TType> save(final Iterable<TType> entities)
			throws ConstraintViolationException {
		return ofy().save().entities(entities).now();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.heys.dating.domain.Repository#save(S)
	 */
	@Override
	public TType save(final TType entity) throws ConstraintViolationException {
		validate(entity);
		ofy().save().entity(entity).now();
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.heys.dating.domain.Repository#save(java.lang.Iterable)
	 */
	@Override
	public Iterable<TType> saveAsync(final Iterable<TType> entities)
			throws ConstraintViolationException {
		ofy().save().entities(entities);
		return entities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.heys.dating.domain.Repository#save(S)
	 */
	@Override
	public TType saveAsync(final TType entity)
			throws ConstraintViolationException {
		ofy().save().entity(entity);
		return entity;
	}

	@Override
	public void validate(final TType entity, final Class<?>... groups)
			throws ConstraintViolationException {
		final Set<ConstraintViolation<TType>> violations = validator.validate(
				entity, groups);
		if (!violations.isEmpty())
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
	}

	@Override
	public TType validateAndSave(final TType entity) {
		validate(entity);
		return save(entity);
	}

	@Override
	public TType validateAndSaveAsync(final TType entity) {
		validate(entity);
		return saveAsync(entity);
	}
}
