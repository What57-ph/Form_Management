package com.example.formmanagement.domain.model;

import com.example.formmanagement.utils.enums.FieldType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Entity(name = "fields")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String label;

    @Enumerated(EnumType.STRING)
    FieldType type;

    @Column(name = "order_display")
    Integer order;

    Boolean required;

    String options;

    @ManyToOne
    @JoinColumn(name = "form_id")
    Form form;

    @OneToMany(mappedBy = "field")
    List<SubmissionValue> submissionValues;

    @Column(name = "created_at")
    Instant createdAt;
    @Column(name = "updated_at")
    Instant updatedAt;

    @PrePersist
    public void handleBeforeCreate() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void handleBeforeUpdate() {
        this.updatedAt = Instant.now();
    }
}
