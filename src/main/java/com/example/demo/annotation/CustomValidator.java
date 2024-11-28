package com.example.demo.annotation;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomValidator implements ConstraintValidator<CustomValidation, String>
{
    public boolean isValid(String colorName, ConstraintValidatorContext cxt) {
        List list = Arrays.asList(new String[]{"RED","GREEN","BLUE"});
        return list.contains(colorName);
    }
}
