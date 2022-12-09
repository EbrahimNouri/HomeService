package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "basic_service")
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
public class BasicService extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "basicService")
    @ToString.Exclude
    private List<TypeService> typeServices;

}
