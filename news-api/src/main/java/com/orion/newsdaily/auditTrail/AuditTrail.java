package com.orion.newsdaily.auditTrail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orion.newsdaily.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_trails")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class AuditTrail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String action;
    @NonNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "fk_user_id", nullable = false)
    @JsonIgnore
    private User user;

}
