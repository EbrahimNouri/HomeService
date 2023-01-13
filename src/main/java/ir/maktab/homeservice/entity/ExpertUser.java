package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.base.Person;
import ir.maktab.homeservice.entity.id.ExpertUserId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

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
    private Person expert;

    @ManyToOne
    @Id
    private Person user;

    @OneToOne(/*fetch = FetchType.EAGER*/)
//    @JoinColumn(name = "order_id")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ExpertUser that = (ExpertUser) o;
        return expert != null && Objects.equals(expert, that.expert)
                && user != null && Objects.equals(user, that.user)
                && order != null && Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expert, user, order);
    }
}
