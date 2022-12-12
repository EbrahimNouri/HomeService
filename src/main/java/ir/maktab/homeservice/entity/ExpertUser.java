package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.id.ExpertUserId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    @Id
    private Order order;

    @CreationTimestamp
    private LocalDate localDate;

    @Max(5)
    private Double point;

    @Column(columnDefinition = "text")
    private String comment;


}
