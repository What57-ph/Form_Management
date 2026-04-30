package com.example.formmanagement.repository;

import com.example.formmanagement.domain.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    Optional<Field> findByLabel(String label);
}
