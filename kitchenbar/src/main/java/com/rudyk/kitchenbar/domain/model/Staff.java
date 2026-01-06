package com.rudyk.kitchenbar.domain.model;



import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a kitchen or bar staff member.
 * Can be used for assigning tickets.
 */
@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "role", nullable = false)
    private String role; // e.g., "chef", "bartender", "grill"

    protected Staff() {
        // JPA
    }

    public Staff(String name, String role) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.role = Objects.requireNonNull(role, "role must not be null");
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public void changeRole(String newRole) {
        this.role = Objects.requireNonNull(newRole, "role must not be null");
    }
}