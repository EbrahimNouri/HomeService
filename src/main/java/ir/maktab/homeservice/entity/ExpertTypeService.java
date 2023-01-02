package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.id.ExpertTypeServiceId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

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
    private Expert expert;

    @Id
    @ManyToOne(/*fetch = FetchType.EAGER*/)
    @JoinColumn(name = "type_service_id")
    private TypeService typeService;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ExpertTypeService that = (ExpertTypeService) o;
        return expert != null && Objects.equals(expert, that.expert)
                && typeService != null && Objects.equals(typeService, that.typeService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expert, typeService);
    }
}
