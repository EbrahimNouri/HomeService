package ir.maktab.homeservice.entity.id;


import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.entity.base.Person;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
/*
@Embeddable
*/
@AllArgsConstructor
@NoArgsConstructor
public class ExpertTypeServiceId implements Serializable {

    @ManyToOne
    private Person expert;

    @ManyToOne
    private TypeService typeService;


}
