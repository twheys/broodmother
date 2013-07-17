package com.heys.dating.service.dto.message;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement
@Data
public class ThreadPreviewDTO {
	List<String> participants;
	String subject;
	String lastSender;
	String lastMessage;
	Date lastUpdated;
	Boolean unread;
	Long threadId;
}
