package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

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
}
