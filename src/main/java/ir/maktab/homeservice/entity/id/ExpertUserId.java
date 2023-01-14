package ir.maktab.homeservice.entity.id;


import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.base.Person;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ExpertUserId implements Serializable {
    @ManyToOne
    private Person expert;

    @ManyToOne
    private Person user;

    @OneToOne(fetch = FetchType.EAGER)
    private Order order;

}

