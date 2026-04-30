package com.example.formmanagement.domain.model;

import com.example.formmanagement.utils.enums.FormStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Entity(name = "forms")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String description;
    @Column(name = "order_display")
    String order;
    @Enumerated(EnumType.STRING)
    FormStatus status;

    @OneToMany(mappedBy = "form")
    List<Field> fields;

    @OneToMany(mappedBy = "form")
    List<Submission> submissions;

    @Column(name = "created_at")
    Instant createdAt;
    @Column(name = "updated_at")
    Instant updatedAt;

    @PrePersist
    public void handleBeforeCreate(){
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void handleBeforeUpdate(){
        this.updatedAt = Instant.now();
    }

}
