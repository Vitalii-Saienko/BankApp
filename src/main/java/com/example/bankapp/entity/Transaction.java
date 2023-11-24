package com.example.bankapp.entity;

import com.example.bankapp.entity.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debit_account_id", referencedColumnName = "id")
    private Account debitAccountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_account_id", referencedColumnName = "id")
    private Account creditAccountId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "transaction_status")
    private TransactionStatus transactionStatus;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transaction_description")
    private String description;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", debitAccountId=" + debitAccountId +
                ", creditAccountId=" + creditAccountId +
                ", transactionType=" + transactionStatus +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
