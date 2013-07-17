package com.heys.dating.domain.validation;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.google.appengine.repackaged.org.joda.time.DateMidnight;

public class OverAgeValidator implements ConstraintValidator<OverAge, Date> {

	private int minimumAge;

	@Override
	public void initialize(final OverAge constraintAnnotation) {
		minimumAge = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(final Date value,
			final ConstraintValidatorContext context) {
		final DateMidnight age = new DateMidnight(value);
		return age.isBefore(new DateMidnight().minusYears(minimumAge).plusDays(
				1));
	}
}
