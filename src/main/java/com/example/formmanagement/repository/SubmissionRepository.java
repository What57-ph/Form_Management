package com.example.formmanagement.repository;

import com.example.formmanagement.domain.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission,Long> {
}
