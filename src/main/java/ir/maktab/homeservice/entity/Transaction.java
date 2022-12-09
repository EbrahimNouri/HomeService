package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.id.TransactionId;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@IdClass(TransactionId.class)
public class Transaction implements Serializable {

    @EmbeddedId
    private TransactionId transactionId;

    private Double transfer;

}
