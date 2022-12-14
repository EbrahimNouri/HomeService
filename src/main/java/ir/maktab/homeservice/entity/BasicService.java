package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "basic_service")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class BasicService extends BaseEntity {

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "basicService", fetch = FetchType.EAGER)
    private List<TypeService> typeServices;

}
