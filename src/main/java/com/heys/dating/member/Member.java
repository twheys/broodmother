package com.heys.dating.member;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Sets;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnSave;
import com.heys.dating.AbstractEntity;
import com.heys.dating.member.validator.OverAge;
import com.heys.dating.profile.Profile;

@Entity
@Cache
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = { "login", "email" }, callSuper = true)
@ToString(exclude = { "password" })
public class Member extends AbstractEntity {

	private static final long serialVersionUID = 8438508214003815930L;

	/**
	 * Uppercased Login property for insensitive indexing.
	 */
	@Index
	private String loginIgnoreCase;

	/**
	 * Uppercased email property for case insensitive indexing.
	 */
	@Index
	private String emailIgnoreCase;

	@NotNull(message = "notnull")
	@Length(min = 5, max = 32, message = "length 5 32")
	@Pattern(regexp = "^[a-zA-Z]([-_][^-_]|[a-zA-Z0-9]*)+$", message = "pattern")
	private String login;

	@NotNull(message = "notnull")
	@Email(message = "email")
	private String email;

	@NotNull(message = "notnull")
	private String password;

	/**
	 * Transient field that is used for validation.
	 */
	@Length(min = 6, max = 132, message = "length 6 132")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).+$", message = "pattern")
	private transient String rawPassword;

	@NotNull(message = "notnull")
	@OverAge(value = 18, message = "minage")
	private Date birthdate;

	@NotNull(message = "notnull")
	private String locale;
	private boolean hasExpiredCredentials;
	private boolean isActivated;
	private boolean isBanned;
	private boolean isEnabled;
	private boolean isLocked;
	private Set<MemberRole> privileges = Sets.newHashSet();
	private Ref<Profile> profile;

	/**
	 * Initialize member with basic properties.
	 * 
	 * @param login
	 * @param email
	 * @param birthdate
	 * @param locale
	 */
	public Member(final String login, final String email, final Date birthdate,
			final String locale) {
		super();
		this.login = login;
		this.email = email;
		this.birthdate = birthdate;
		this.locale = locale;

		password = "";

		hasExpiredCredentials = false;
		isActivated = false;
		isBanned = false;
		isEnabled = true;
		isLocked = false;

		privileges = Sets.newHashSet(MemberRole.ROLE_USER);
	}

	@Override
	@OnSave
	public void onSave() {
		super.onSave();
		loginIgnoreCase = StringUtils.upperCase(login);
		emailIgnoreCase = StringUtils.upperCase(email);
		rawPassword = null;
	}
}
