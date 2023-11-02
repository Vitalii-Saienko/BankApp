package com.example.bankapp.entity;

import com.example.bankapp.entity.enums.ManagerStatus;
import com.example.bankapp.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "managers")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID managerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "managerId", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private Set<Product> productSet = new HashSet<>();

    @OneToMany(mappedBy = "managerId", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private Set<Client> clientSet = new HashSet<>();

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "manager_status")
    private ManagerStatus managerStatus;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return Objects.equals(managerId, manager.managerId) && Objects.equals(firstName, manager.firstName) && Objects.equals(lastName, manager.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(managerId, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "managerId=" + managerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", productSet=" + productSet +
                ", clientSet=" + clientSet +
                ", managerStatus=" + managerStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
