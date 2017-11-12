package com.n26.transactions.annotations;

import java.time.Instant;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TimestampValidator implements ConstraintValidator<ValidTimestamp, Long> {

	@Override
	public void initialize(ValidTimestamp arg0) {

	}

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext arg1) {
		return value == null || value > ((Instant.now().getEpochSecond()) - 60);
	}

}
