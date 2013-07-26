package com.heys.dating.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.appengine.repackaged.org.joda.time.DateTime;

@Component("calUtil")
public class CalendarUtil {

	private static final String BIRTHDATE_FORMAT = "yyyy-MM-dd";

	public List<Integer> getSelectableYears() {
		final List<Integer> years = new ArrayList<Integer>();
		final int currentYear = new DateTime(new Date()).minusYears(18)
				.getYear();
		for (int i = 0; i < 99; i++) {
			years.add(currentYear - i);
		}
		return years;
	}

	public String parseBirthdate(final String day, final String month,
			final String year) {
		return new DateTime(new Date()).toString(BIRTHDATE_FORMAT);
	}
}
