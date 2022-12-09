package ir.maktab.homeservice.entity.id;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.TypeService;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ExpertTypeServiceId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "expert_id")
    private Expert expert;

    @ManyToOne
    @JoinColumn(name = "type_service_id")
    private TypeService typeService;


}
