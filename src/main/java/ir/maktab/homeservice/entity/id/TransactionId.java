package ir.maktab.homeservice.entity.id;

import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TransactionId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "expert_id")
    private Expert expert;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime localDateTime;
}
