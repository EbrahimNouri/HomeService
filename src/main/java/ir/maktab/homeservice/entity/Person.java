package ir.maktab.homeservice.entity;

import jakarta.persistence.*;
import lombok.*;


import java.sql.Timestamp;
import java.time.LocalDateTime;


@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Timestamp registerTime;



    @PrePersist
    void setCurrentTime(){
        registerTime=Timestamp.valueOf(LocalDateTime.now());
    }

}
