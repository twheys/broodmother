package com.heys.dating.test;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.google.appengine.labs.repackaged.com.google.common.collect.Maps;
import com.google.appengine.repackaged.org.joda.time.DateMidnight;
import com.heys.dating.member.Gender;
import com.heys.dating.member.Member;
import com.heys.dating.member.MemberService;

@Ignore
public class MemberTest extends GaeTest {

	protected static final String BIRTHDATE = "birthdate";
	protected static final String EMAIL = "email";
	protected static final String GENDER = "gender";
	protected static final String LOCALE = "locale";
	protected static final String LOGIN = "login";
	protected static final String PAGEMAX = "partnerAgeMax";
	protected static final String PAGEMIN = "parterAgeMin";
	protected static final String PASSWORD = "password";
	protected static final String PGENDER = "partnerGender";
	protected static final String ZIPCODE = "zipCode";
	
	private static int nextUserInc = 1;

	@Autowired
	protected MemberService memberSvc;

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

	@SuppressWarnings("unchecked")
	protected Member longReg(final Map<String, Object> registration) {
		return memberSvc.register((String) registration.get(LOGIN),
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
	}

	protected Member shortReg(final Map<String, Object> registration) {
		return memberSvc.register((String) registration.get(LOGIN),
				(String) registration.get(PASSWORD),
				(String) registration.get(EMAIL),
				(DateMidnight) registration.get(BIRTHDATE),
				(Locale) registration.get(LOCALE));
	}

}