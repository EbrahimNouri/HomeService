package ir.maktab.homeservice.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;


@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@Component
public class Expert extends Person{

//    private SpecialistType specialistType;
}
