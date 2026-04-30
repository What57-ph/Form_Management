package com.example.formmanagement.repository;

import com.example.formmanagement.domain.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
}
