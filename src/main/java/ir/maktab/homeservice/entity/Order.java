package ir.maktab.homeservice.entity;


import ir.maktab.homeservice.entity.base.BaseEntity;
import ir.maktab.homeservice.entity.enums.OrderType;
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

    @OneToMany(mappedBy = "order")
    private List<ExpertUser> expertUser;

    @OneToMany(mappedBy = "order")
    private List<Offer> offers;

    private Double suggestedPrice;

    private String description;

    private LocalDate startOfWork;

    private String address;

    private OrderType orderType;

}
