package ir.maktab.homeservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@Component
@NoArgsConstructor
public class TypeService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BaseServiceType baseServiceType;

    @OneToMany(mappedBy = "typeService")
    @ToString.Exclude
    private List<Expert> experts;

    @Column(unique = true)
    private String subService;

    private double basePrice;

    @OneToMany(mappedBy = "typeService")
    @ToString.Exclude
    private List<Order> orders;
}
