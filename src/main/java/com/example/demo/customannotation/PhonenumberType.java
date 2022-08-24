package com.example.demo.customannotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.example.demo.customvalidator.PhoneNumberValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PhonenumberType {
	// trường message là bắt buộc, khai báo nội dung sẽ trả về khi field k hợp lệ
	String message() default "must be phone number";

	// Cái này là bắt buộc phải có để Hibernate Validator có thể hoạt động
	Class<?>[] groups() default {};

	// Cái này là bắt buộc phải có để Hibernate Validator có thể hoạt động
	Class<? extends Payload>[] payload() default {};
}
