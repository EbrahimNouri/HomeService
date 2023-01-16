package ir.maktab.homeservice.entity.id;

import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@EqualsAndHashCode
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class TransactionId implements Serializable {

    @ManyToOne
    private Expert expert;

    @ManyToOne
    private User user;

    private LocalDateTime localDateTime;
}
