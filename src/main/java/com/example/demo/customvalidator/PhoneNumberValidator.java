package com.example.demo.customvalidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.demo.customannotation.PhonenumberType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhonenumberType, String> {
	private static final String regexPattern = "/^(\\+?84|0)((3([2-9]))|(5([2689]))|(7([0|6-9]))|(8([1-9]))|(9([0-9])))([0-9]{7})/";
	private static final Pattern PHONENUMBER_REGEX = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.isEmpty())
			return false;

		Matcher m = PHONENUMBER_REGEX.matcher(value);
		return m.matches();
	}
}
