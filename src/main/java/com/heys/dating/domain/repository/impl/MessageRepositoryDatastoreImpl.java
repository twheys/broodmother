package com.heys.dating.domain.repository.impl;

import org.springframework.stereotype.Repository;

import com.heys.dating.domain.AbstractDatastoreRepository;
import com.heys.dating.domain.message.Message;
import com.heys.dating.domain.repository.MessageRepository;

@Repository
public class MessageRepositoryDatastoreImpl extends AbstractDatastoreRepository<Message>
		implements MessageRepository {

	public MessageRepositoryDatastoreImpl() {
		super(Message.class);
	}

}
