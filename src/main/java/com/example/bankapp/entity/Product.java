package com.example.bankapp.entity;

import com.example.bankapp.entity.enums.Currency;
import com.example.bankapp.entity.enums.Status;
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
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Manager managerId;

    @OneToMany(mappedBy = "productId", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.DETACH}, fetch = FetchType.LAZY)
    private Set<Agreement> agreementSet = new HashSet<>();

    @Column(name = "product_name")
    private String productName;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "product_status")
    private Status productStatus;

    @Column(name = "currency_code")
    @Enumerated(EnumType.ORDINAL)
    private Currency currencyCode;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "product_limit")
    private BigDecimal productLimit;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) && Objects.equals(productName, product.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", managerId=" + managerId +
                ", agreementSet=" + agreementSet +
                ", productName='" + productName + '\'' +
                ", productStatus=" + productStatus +
                ", currencyCode=" + currencyCode +
                ", interestRate=" + interestRate +
                ", productLimit=" + productLimit +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
