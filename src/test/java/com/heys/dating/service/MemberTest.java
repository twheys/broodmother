package com.heys.dating.service;

import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;

import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.google.appengine.labs.repackaged.com.google.common.collect.Maps;
import com.google.appengine.repackaged.org.joda.time.DateMidnight;
import com.heys.dating.domain.member.Gender;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.repository.impl.MemberRepositoryDatastoreImpl;
import com.heys.dating.domain.repository.impl.ProfileRepositoryDatastoreImpl;
import com.heys.dating.manager.MemberManager;
import com.heys.dating.manager.impl.MemberManagerImpl;
import com.heys.dating.manager.impl.PictureManagerImpl;
import com.heys.dating.test.GaeTest;

public class MemberTest extends GaeTest {

	protected static final String LOGIN = "login";
	protected static final String PASSWORD = "password";
	protected static final String EMAIL = "email";
	protected static final String BIRTHDATE = "birthdate";
	protected static final String ZIPCODE = "zipCode";
	protected static final String GENDER = "gender";
	protected static final String PGENDER = "partnerGender";
	protected static final String PAGEMIN = "parterAgeMin";
	protected static final String PAGEMAX = "partnerAgeMax";
	protected static final String LOCALE = "locale";

	protected MemberManager memberMananger;

	private static int nextUserInc = 1;

	public MemberTest() {
		super();
	}

	protected Map<String, Object> getLongRegValues() {
		final Map<String, Object> validVaues = getShortRegValues();
		validVaues.put(ZIPCODE, "12345");
		validVaues.put(GENDER, Gender.FEMALE);
		validVaues.put(PGENDER, Lists.newArrayList(Gender.MALE));
		validVaues.put(PAGEMIN, 18);
		validVaues.put(PAGEMAX, 35);
		return validVaues;
	}

	protected Map<String, Object> getShortRegValues() {
		final Map<String, Object> validVaues = Maps.newHashMap();
		final String name = nextUserName();
		validVaues.put(LOGIN, name);
		validVaues.put(PASSWORD, "Test1231");
		validVaues.put(EMAIL, name + "@test.com");
		validVaues.put(BIRTHDATE, new DateMidnight().minusYears(21));
		validVaues.put(LOCALE, new Locale("de", "DE"));
		return validVaues;
	}

	protected Member longReg(final Map<String, Object> registration) {
		return memberMananger.register((String) registration.get(LOGIN),
				(String) registration.get(PASSWORD),
				(String) registration.get(EMAIL),
				(DateMidnight) registration.get(BIRTHDATE),
				(String) registration.get(ZIPCODE),
				(Locale) registration.get(LOCALE),
				(Gender) registration.get(GENDER),
				(List<Gender>) registration.get(PGENDER),
				(Integer) registration.get(PAGEMIN),
				(Integer) registration.get(PAGEMAX));
	}

	protected String nextUserName() {
		return "testUser" + nextUserInc++;
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		memberMananger = new MemberManagerImpl();
		setField(memberMananger, "memberRepository",
				new MemberRepositoryDatastoreImpl());
		setField(memberMananger, "profileRepository",
				new ProfileRepositoryDatastoreImpl());
		setField(memberMananger, "passwordEncoder",
				new PlaintextPasswordEncoder());
		setField(memberMananger, "pictureManager", new PictureManagerImpl());
		final ReflectionSaltSource salt = new ReflectionSaltSource();
		salt.setUserPropertyToUse("salt");
		setField(memberMananger, "saltSource", salt);
	}

	protected Member shortReg(final Map<String, Object> registration) {
		return memberMananger.register((String) registration.get(LOGIN),
				(String) registration.get(PASSWORD),
				(String) registration.get(EMAIL),
				(DateMidnight) registration.get(BIRTHDATE),
				(Locale) registration.get(LOCALE));
	}

}