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
@Getter
@Setter
@ToString
public class TypeService extends BaseEntity {

    @Column(unique = true)
    private String subService;

    @ColumnDefault("0")
    private double basePrice;

    @OneToMany(mappedBy = "typeService")
    @ToString.Exclude
    private List<ExpertTypeService> expertTypeServices;

    @OneToMany(mappedBy = "typeService")
    @ToString.Exclude
    private List<Order> orders;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private BasicService basicService;

    @Column(nullable = false)
    private String description;

    @Builder
    public TypeService(Long id, String subService, double basePrice, List<ExpertTypeService> expertTypeServices
            , List<Order> orders, BasicService basicService, String description) {
        super(id);
        this.subService = subService;
        this.basePrice = basePrice;
        this.expertTypeServices = expertTypeServices;
        this.orders = orders;
        this.basicService = basicService;
        this.description = description;
    }

    public TypeService() {
    }

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
