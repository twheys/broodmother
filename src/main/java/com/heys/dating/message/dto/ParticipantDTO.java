package com.heys.dating.message.dto;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDTO {
	Long id;
	String login;
	String profileVanity;
	Long pictureId;
}
