package ir.maktab.homeservice.entity.id;


import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.base.Person;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ExpertUserId implements Serializable {
//    @ManyToOne
//    @JoinColumn(name = "expert_id")
    private Person expert;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
    private Person user;

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "order_id")
    private Order order;

}

