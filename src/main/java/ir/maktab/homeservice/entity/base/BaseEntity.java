package ir.maktab.homeservice.entity.base;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public BaseEntity(Long id) {
        this.id = id;
    }

    public BaseEntity() {
    }
}
