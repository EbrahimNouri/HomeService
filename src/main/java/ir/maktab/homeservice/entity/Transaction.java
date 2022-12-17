package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.enums.TransactionType;
import ir.maktab.homeservice.entity.id.TransactionId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "expert_id")
    private Expert expert;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

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
}
