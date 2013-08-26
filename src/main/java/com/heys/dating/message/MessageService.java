package com.heys.dating.message;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.heys.dating.member.Member;
import com.heys.dating.message.dto.ThreadDTO;
import com.heys.dating.message.dto.ThreadListDTO;

public interface MessageService {

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

	int getUnreadCount(@NotNull final Member member);
}
