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
@IdClass(ExpertUserId.class)
@Table(name = "expert_type_service")
public class ExpertTypeService implements Serializable {

    @EmbeddedId
    private ExpertTypeServiceId expertTypeServiceId;

}
