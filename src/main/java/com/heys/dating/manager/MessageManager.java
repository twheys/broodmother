package com.heys.dating.manager;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.message.Message;
import com.heys.dating.domain.message.Thread;
import com.heys.dating.service.dto.message.ThreadDTO;
import com.heys.dating.service.dto.message.ThreadListDTO;

public interface MessageManager {

	ThreadDTO getMessagesForThread(@NotNull final Member member,
			@NotNull final Thread thread, final Integer count,
			final Integer offset);

	ThreadListDTO getNewThreadsForMember(@NotNull final Member member,
			final Integer limit, final Integer offset);

	Message send(@NotNull final Member sender, @NotNull final Member recipient,
			@NotEmpty @Length(min = 1, max = 1024) final String text);

	Message send(@NotNull final Member sender, final List<Member> recipients,
			@NotEmpty @Length(min = 1, max = 1024) final String text);

	Message send(@NotNull final Member sender, @NotNull final Member recipient,
			@NotEmpty @Length(min = 1, max = 128) final String subject,
			@NotEmpty @Length(min = 1, max = 1024) final String text);

	Message send(@NotNull final Member sender, final List<Member> recipients,
			@NotEmpty @Length(min = 1, max = 128) final String subject,
			@NotEmpty @Length(min = 1, max = 1024) final String text);

	Message send(@NotNull final Member sender, @NotNull final Thread thread,
			@NotEmpty @Length(min = 1, max = 1024) final String text);

	void sendAsync(@NotNull final Member recipient,
			@NotNull final Thread thread, @NotNull final Message message);
}
