package com.heys.dating.domain;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.DeleteType;
import com.googlecode.objectify.cmd.LoadType;

public abstract class AbstractRepository<TType extends AbstractEntity>
		implements Repository<TType> {

	protected final Class<TType> type;
	private Validator validator;

	public AbstractRepository(final Class<TType> type) {
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

	protected DeleteType delete() {
		return ofy().delete().type(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.heys.dating.domain.Repository#delete(java.lang.Iterable)
	 */
	@Override
	public void delete(final Iterable<? extends TType> entities) {
		ofy().delete().entities(entities).now();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.heys.dating.domain.Repository#delete(java.lang.Long)
	 */
	@Override
	public void delete(final Long id) {
		delete().id(id).now();
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
	@SuppressWarnings("unchecked")
	@Override
	public List<TType> findAll(final List<Key<TType>> keys) {
		return (List<TType>) ofy().load().keys(keys);
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
	public <S extends TType> Iterable<S> save(final Iterable<S> entities) {
		ofy().save().entities(entities).now();
		return entities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.heys.dating.domain.Repository#save(S)
	 */
	@Override
	public <S extends TType> S save(final S entity) {
		ofy().save().entity(entity).now();
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.heys.dating.domain.Repository#save(java.lang.Iterable)
	 */
	@Override
	public <S extends TType> Iterable<S> saveAsync(final Iterable<S> entities) {
		ofy().save().entities(entities);
		return entities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.heys.dating.domain.Repository#save(S)
	 */
	@Override
	public <S extends TType> S saveAsync(final S entity) {
		ofy().save().entity(entity);
		return entity;
	}

	@Override
	public void validate(final TType entity)
			throws ConstraintViolationException {
		validator.validate(entity);
	}
}
