package ir.maktab.homeservice.entity.id;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.TypeService;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
@AllArgsConstructor
public class ExpertTypeServiceId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "expert_id")
    private Expert expert;

    @ManyToOne
    @JoinColumn(name = "type_service_id")
    private TypeService typeService;


}
