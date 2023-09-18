package com.orion.newsdaily.AuditTrail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_trails")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class AuditTrail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String action;
    @NotNull
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "fk_user_id", nullable = false)
    @JsonIgnore
    private Long userId;

}
