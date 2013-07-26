package com.heys.dating.deeplink;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Load;
import com.heys.dating.AbstractEntity;
import com.heys.dating.member.Member;

@Entity
@Cache
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class Deeplink extends AbstractEntity {
	private static final long serialVersionUID = -5710783101566242225L;

	@NotNull
	private DeeplinkAction action;
	@NotNull
	private Date expiration;
	@NotNull
	@Load
	private Ref<Member> member;

}
