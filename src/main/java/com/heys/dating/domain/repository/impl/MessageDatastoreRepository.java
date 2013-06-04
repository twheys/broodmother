package com.heys.dating.domain.repository.impl;

import org.springframework.stereotype.Repository;

import com.heys.dating.domain.AbstractRepository;
import com.heys.dating.domain.message.Message;
import com.heys.dating.domain.repository.MessageRepository;

@Repository
public class MessageDatastoreRepository extends AbstractRepository<Message>
		implements MessageRepository {

	public MessageDatastoreRepository() {
		super(Message.class);
	}

}
