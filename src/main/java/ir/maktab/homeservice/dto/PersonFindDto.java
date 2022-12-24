package ir.maktab.homeservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PersonFindDto {
    private String firstname;
    private String lastname;
    private String email;
    private PersonType personType;
    private Double credit;
    private LocalDateTime signupDate;
}
