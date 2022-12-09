package ir.maktab.homeservice.entity;


import ir.maktab.homeservice.entity.base.BaseEntity;
import ir.maktab.homeservice.entity.enums.OrderType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "order_table")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Order extends BaseEntity {

    @ManyToOne
    private TypeService typeService;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order")
    private List<ExpertUser> expertUser;

    private Double suggestedPrice;

    private String description;

    private LocalDate startOfWork;

    private String address;

    private OrderType orderType;

}
