package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.base.Person;
import ir.maktab.homeservice.entity.enums.TransactionType;
import ir.maktab.homeservice.entity.id.TransactionId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@IdClass(TransactionId.class)
public class Transaction implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    private Person expert;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    private Person user;

    @Id
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
