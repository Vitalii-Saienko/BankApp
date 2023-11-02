package com.example.bankapp.entity;

import com.example.bankapp.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "agreements")
public class Agreement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID agreementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product productId;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "agreement_status")
    private Status agreementStatus;

    @Column(name = "agreement_sum")
    private BigDecimal agreementSum;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agreement agreement = (Agreement) o;
        return Objects.equals(agreementId, agreement.agreementId) && Objects.equals(accountId, agreement.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agreementId, accountId);
    }

    @Override
    public String toString() {
        return "Agreement{" +
                "agreementId=" + agreementId +
                ", accountId=" + accountId +
                ", productId=" + productId +
                ", interestRate=" + interestRate +
                ", agreementStatus=" + agreementStatus +
                ", agreementSum=" + agreementSum +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
