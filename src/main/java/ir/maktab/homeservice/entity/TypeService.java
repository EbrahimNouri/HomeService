package ir.maktab.homeservice.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class TypeService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BaseServiceType baseServiceType;

    @OneToMany(mappedBy = "typeService")
    private List<Expert> experts;

    @Column(unique = true)
    private String subService;

    private double basePrice;
}
