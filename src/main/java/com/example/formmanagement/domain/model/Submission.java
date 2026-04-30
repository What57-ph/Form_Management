package com.example.formmanagement.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;
import java.time.Instant;

@Entity(name = "submissions")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "form_id")
    Form form;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL)
    List<SubmissionValue> submissionValues;

    @Column(name = "submit_at")
    Instant submitAt;

    @PrePersist
    public void handleBeforeSubmit(){
        this.submitAt = Instant.now();
    }
}
