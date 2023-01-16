package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.base.BaseEntity;
import ir.maktab.homeservice.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Transaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private Expert expert;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @CreationTimestamp
    @Column(name = "local_date_time")
    private LocalDateTime localDateTime;

    private Double transfer;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @PrePersist
    void autoSet(){
        localDateTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transaction that = (Transaction) o;
        return expert != null && Objects.equals(expert, that.expert)
                && user != null && Objects.equals(user, that.user)
                && localDateTime != null && Objects.equals(localDateTime, that.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expert, user, localDateTime);
    }
}
