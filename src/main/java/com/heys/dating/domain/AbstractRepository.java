package com.heys.dating.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.datastore.Key;

@Transactional
public abstract class AbstractRepository<TType extends AbstractEntity> {
	protected final Class<TType> type;

	@PersistenceContext
	protected EntityManager entityManager;

	public AbstractRepository(final Class<TType> type) {
		this.type = type;
	}

	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void delete(final Iterable<? extends TType> entities) {
		// TODO Auto-generated method stub

	}

	public void delete(final Key id) {
		final TType entity = findOne(id);
		delete(entity);

	}

	public void delete(final TType entity) {
		entityManager.remove(entity);
	}

	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	public void deleteAllInBatch() {
		// TODO Auto-generated method stub

	}

	public void deleteInBatch(final Iterable<TType> entities) {
		// TODO Auto-generated method stub

	}

	public boolean exists(final Key id) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<TType> findAll() {
		return entityManager.createQuery("from " + type.getName(), type)
				.getResultList();
	}

	public Iterable<TType> findAll(final Iterable<Key> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<TType> findAll(final Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TType> findAll(final Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	public TType findOne(final Key key) {
		return entityManager.find(type, key);
	}

	public void flush() {
		// TODO Auto-generated method stub

	}

	public <S extends TType> List<S> save(final Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	public <S extends TType> S save(final S entity) {
		entityManager.persist(entity);
		return entity;
	}

	public TType saveAndFlush(final TType entity) {
		save(entity);
		flush();
		return entity;
	}

	public void update(final TType entity) {
		entityManager.merge(entity);
	}
}
