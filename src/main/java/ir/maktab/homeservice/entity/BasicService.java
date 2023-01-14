package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "basic_service")
@Getter
@Setter
@ToString
public class BasicService extends BaseEntity {

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "basicService"/*, fetch = FetchType.EAGER*/)
    @ToString.Exclude
    private List<TypeService> typeServices;

    public BasicService(Long id, String name, List<TypeService> typeServices) {
        super(id);
        this.name = name;
        this.typeServices = typeServices;
    }

    public BasicService() {
        super();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BasicService that = (BasicService) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
