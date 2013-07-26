package com.heys.dating.message.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
	Long participantId;
	String text;
	Date sentTime;
	Date readTime;
	List<String> pictureUrls;
}
