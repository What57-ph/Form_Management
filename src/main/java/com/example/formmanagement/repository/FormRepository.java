package com.example.formmanagement.repository;

import com.example.formmanagement.domain.model.Form;
import com.example.formmanagement.utils.enums.FormStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    boolean existsByTitle(String title);
    Page<Form> findAllByStatus(FormStatus status, Pageable pageable);
}
