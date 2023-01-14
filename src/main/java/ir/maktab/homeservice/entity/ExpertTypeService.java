package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.base.Person;
import ir.maktab.homeservice.entity.id.ExpertTypeServiceId;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@IdClass(ExpertTypeServiceId.class)
@ToString
@Table(name = "expert_type_service")
public class ExpertTypeService implements Serializable {

    @Id
    @ManyToOne(/*fetch = FetchType.EAGER*/)
    @JoinColumn(name = "expert_id")
    private Person expert;

    @Id
    @ManyToOne(/*fetch = FetchType.EAGER*/)
    @JoinColumn(name = "type_service_id")
    private TypeService typeService;


}
