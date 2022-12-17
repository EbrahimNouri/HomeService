package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.id.ExpertUserId;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comment_point")
@Builder
@IdClass(ExpertUserId.class)
public class ExpertUser implements Serializable {

    @ManyToOne
    @Id
    private Expert expert;

    @ManyToOne
    @Id
    private User user;

    @OneToOne(/*fetch = FetchType.EAGER*/)
    @JoinColumn(name = "order_id")
    @Id
    private Order order;

    private LocalDate localDate;

    @Column(nullable = false)
    private Double point;

    private String comment;

    @PrePersist
    void setDate(){
        localDate = LocalDate.now();
    }
}
