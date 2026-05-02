package com.example.formmanagement.utils.validation;

import com.example.formmanagement.domain.model.Field;
import com.example.formmanagement.utils.exception.FieldValidationException;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FieldValidator {
    public static void validateText(String value) throws FieldValidationException {
        if (value.length() > 200){
            throw new FieldValidationException("Text cannot has more than 200 characters.");
        }
    }

    public static void validateNumber(String value) throws FieldValidationException {
        double number;
        try {
            number = Double.parseDouble(value);
        } catch (NumberFormatException e){
            throw new FieldValidationException("Invalid number.");
        }

        if (Double.isNaN(number) || Double.isInfinite(number) || number < 0 || number > 100){
            throw new FieldValidationException("Invalid number.");
        }
    }

    public static void validateDate(String value) throws FieldValidationException {
        LocalDate date = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (date.isBefore(LocalDate.now())){
            throw new FieldValidationException("Cannot choose date in the past.");
        }
    }

    public static void validateColor(String value) throws FieldValidationException {
        if (!value.matches("^#([A-Fa-f0-9]{6})$")) {
            throw new FieldValidationException("Color code must match format HEX (#RRGGBB)");
        }
    }

    public static void validateSelect(List<String> options, String value) throws FieldValidationException {

        if (options == null || options.isEmpty()){
            throw new FieldValidationException("Field select has no option.");
        }

        if (!options.contains(value.trim())){
            throw new FieldValidationException("Must choose 1 of available options.");
        }

    }
}
