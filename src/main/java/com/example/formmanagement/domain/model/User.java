package com.example.formmanagement.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Entity(name = "users")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;
    String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    Role role;

    @OneToMany(mappedBy = "user")
    List<Submission> submissions;
}
