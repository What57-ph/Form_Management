package com.example.formmanagement;

import com.example.formmanagement.utils.exception.FieldValidationException;
import com.example.formmanagement.utils.validation.FieldValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class FieldValidatorTest {
    @Test
    void validateText_whenValueIsEmpty() {
        assertDoesNotThrow(() -> FieldValidator.validateText(""));
    }

    @Test
    void validateText_whenValueIsExactly200characters() {
        String value = "v".repeat(200);
        assertDoesNotThrow(() -> FieldValidator.validateText(value));
    }

    @Test
    void validateText_whenValueIsExceeds200characters() {
        String value = "v".repeat(201);
        FieldValidationException exception = assertThrows(
                FieldValidationException.class,
                () -> FieldValidator.validateText(value)
        );
        assertEquals("Text cannot has more than 200 characters.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "50", "99.9", "100"})
    void validateNumber_whenValueInRange(String value) {
        assertDoesNotThrow(() -> FieldValidator.validateNumber(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "101"})
    void validateNumber_whenValueNotInRange(String value) {
        FieldValidationException exception = assertThrows(
                FieldValidationException.class,
                () -> FieldValidator.validateNumber(value)
        );
        assertEquals("Invalid number.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaaaa", "", "NaN", " "})
    void validateNumber_whenValueIsNotANumber(String value) {
        FieldValidationException exception = assertThrows(
                FieldValidationException.class,
                () -> FieldValidator.validateNumber(value)
        );
        assertEquals("Invalid number.", exception.getMessage());
    }

    @Test
    void validateDate_whenDateIsToday() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertDoesNotThrow(() -> FieldValidator.validateDate(today));
    }

    @Test
    void validateDate_whenDateIsInFuture() {
        String future = LocalDate.now().plusDays(20).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertDoesNotThrow(() -> FieldValidator.validateDate(future));
    }

    @Test
    void validateDate_whenDateIsInPast() {
        String past = LocalDate.now().minusDays(20).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        FieldValidationException exception = assertThrows(
                FieldValidationException.class,
                () -> FieldValidator.validateDate(past)
        );
        assertEquals("Cannot choose date in the past.", exception.getMessage());
    }

    @Test
    void validateDate_whenDateFormatIsInvalid(){
        assertThrows(Exception.class, () -> FieldValidator.validateDate("02-05-2026"));
        assertThrows(Exception.class, () -> FieldValidator.validateDate("aaaaaa"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"#000000", "#FFFFFF", "#1A2B3C", "#aabbcc", "#AbCdEf"})
    void validateColor_whenValueValid(String value){
        assertDoesNotThrow(() -> FieldValidator.validateColor(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"red", "blue", "yellow", "orange", "aaaaa"})
    void validateColor_whenValueInvalid(String value){
        FieldValidationException exception = assertThrows(
                FieldValidationException.class,
                () -> FieldValidator.validateColor(value)
        );
        assertEquals("Color code must match format HEX (#RRGGBB)", exception.getMessage());
    }

    @Test
    void validateSelect_whenValueMatchesOptions(){
        assertDoesNotThrow(() -> FieldValidator.validateSelect(List.of("Option A", "Option B"), "Option A"));
    }

    @Test
    void validateSelect_whenValueNotMatchesOptions(){
        FieldValidationException exception = assertThrows(
                FieldValidationException.class,
                () -> FieldValidator.validateSelect(List.of("Option A", "Option B"), "Option C")
        );
        assertEquals("Must choose 1 of available options.", exception.getMessage());
    }

    @Test
    void validateSelect_whenHasNoOption(){
        FieldValidationException exception = assertThrows(
                FieldValidationException.class,
                () -> FieldValidator.validateSelect(List.of(),"Option A")
        );
        assertEquals("Field select has no option.", exception.getMessage());
    }

    @Test
    void validateSelect_whenOptionIsNull(){
        FieldValidationException exception = assertThrows(
                FieldValidationException.class,
                () -> FieldValidator.validateSelect(null,"Option A")
        );
        assertEquals("Field select has no option.", exception.getMessage());
    }

}
