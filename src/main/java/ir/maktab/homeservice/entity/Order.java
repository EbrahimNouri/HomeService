package ir.maktab.homeservice.entity;


import ir.maktab.homeservice.entity.base.BaseEntity;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.entity.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_table")
@Builder
public class Order extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private TypeService typeService;

    @ManyToOne
    private User user;

    @OneToOne(mappedBy = "order", fetch = FetchType.EAGER)
    private ExpertUser expertUser;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<Offer> offers;

    private Double suggestedPrice;

    private String description;

    private LocalDate startOfWork;

    private String address;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private Long delayEndWorkHours;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return getId() != null && Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
