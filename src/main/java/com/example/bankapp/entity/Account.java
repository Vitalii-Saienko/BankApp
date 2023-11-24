package com.example.bankapp.entity;

import com.example.bankapp.entity.enums.AccountStatus;
import com.example.bankapp.entity.enums.AccountType;
import com.example.bankapp.entity.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID accountUUID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client clientId;

    @Column(name = "account_type")
    @Enumerated(EnumType.ORDINAL)
    private AccountType accountType;

    @Column(name = "account_status")
    @Enumerated(EnumType.ORDINAL)
    private AccountStatus accountStatus;

    @Column(name = "account_balance")
    private BigDecimal accountBalance;

    @Column(name = "currency_code")
    @Enumerated(EnumType.ORDINAL)
    private Currency currencyCode;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "accountId", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.DETACH}, fetch = FetchType.LAZY)
    private Set<Agreement> agreementId = new HashSet<>();

    @OneToMany(mappedBy = "debitAccountId", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.DETACH}, fetch = FetchType.LAZY)
    private Set<Transaction> debitTransactionSet = new HashSet<>();

    @OneToMany(mappedBy = "creditAccountId", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.DETACH}, fetch = FetchType.LAZY)
    private Set<Transaction> creditTransactionSet = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountUUID, account.accountUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountUUID);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountUUID=" + accountUUID +
                ", clientId=" + clientId +
                ", accountType=" + accountType +
                ", accountStatus=" + accountStatus +
                ", accountBalance=" + accountBalance +
                ", currencyCode=" + currencyCode +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
