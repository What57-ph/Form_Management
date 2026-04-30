package com.example.formmanagement.service;

import com.example.formmanagement.domain.model.Field;
import com.example.formmanagement.repository.FieldRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class FieldService {
    FieldRepository fieldRepository;

    public boolean validateField(Field field)  {
        return true;
    }
}
