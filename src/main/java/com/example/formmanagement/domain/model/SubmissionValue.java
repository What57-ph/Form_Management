package com.example.formmanagement.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "submission_value")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubmissionValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String value;

    @ManyToOne
    @JoinColumn(name = "field_id")
    Field field;

    @ManyToOne
    @JoinColumn(name = "submission_id")
    Submission submission;
}
