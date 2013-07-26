package com.heys.dating.impl.gae.repository;

import org.springframework.stereotype.Repository;

import com.heys.dating.deeplink.Deeplink;
import com.heys.dating.deeplink.DeeplinkRepository;

@Repository
public class DeeplinkRepositoryDatastoreImpl extends
		AbstractDatastoreRepository<Deeplink> implements DeeplinkRepository {

	public DeeplinkRepositoryDatastoreImpl() {
		super(Deeplink.class);
	}

}
