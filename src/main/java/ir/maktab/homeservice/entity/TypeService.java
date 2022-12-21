package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "type_service")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class TypeService extends BaseEntity {

    @Column(unique = true)
    private String subService;

    @ColumnDefault("0")
    private double basePrice;

    @OneToMany(mappedBy = "typeService")
    private List<ExpertTypeService> expertTypeServices;

    @OneToMany(mappedBy = "typeService")
    private List<Order> orders;

    @ManyToOne(/*fetch = FetchType.EAGER*/)
    @JoinColumn(nullable = false)
    private BasicService basicService;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TypeService that = (TypeService) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
