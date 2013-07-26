package com.heys.dating.message.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;

@XmlRootElement
@Data
@AllArgsConstructor
public class ThreadDTO {
	String subject;
	Date startDate;
	List<ParticipantDTO> participants;
	List<MessageDTO> messages;
}
