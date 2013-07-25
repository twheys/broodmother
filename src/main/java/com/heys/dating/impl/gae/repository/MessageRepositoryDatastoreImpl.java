package com.heys.dating.impl.gae.repository;

import org.springframework.stereotype.Repository;

import com.heys.dating.message.Message;
import com.heys.dating.message.MessageRepository;

@Repository
public class MessageRepositoryDatastoreImpl extends AbstractDatastoreRepository<Message>
		implements MessageRepository {

	public MessageRepositoryDatastoreImpl() {
		super(Message.class);
	}

}
