package ir.maktab.homeservice.entity;


import ir.maktab.homeservice.entity.base.BaseEntity;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.entity.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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

}
