package ir.maktab.homeservice.entity.id;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.User;
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
    private Expert expert;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
    private User user;

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "order_id")
    private Order order;

}

