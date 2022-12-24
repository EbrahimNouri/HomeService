package ir.maktab.homeservice.entity.id;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.TypeService;
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

/*    @ManyToOne
    @JoinColumn(name = "expert_id")*/
    private Expert expert;

/*    @ManyToOne
    @JoinColumn(name = "type_service_id")*/
    private TypeService typeService;


}
