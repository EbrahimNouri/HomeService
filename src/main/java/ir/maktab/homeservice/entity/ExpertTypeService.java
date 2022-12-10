package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.id.ExpertTypeServiceId;
import ir.maktab.homeservice.entity.id.ExpertUserId;
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
@Table(name = "expert_type_service")
public class ExpertTypeService implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "expert_id")
    private Expert expert;

    @Id
    @ManyToOne
    @JoinColumn(name = "type_service_id")
    private TypeService typeService;
}
