package ir.maktab.homeservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PersonFindDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private PersonType personType;
    private Double credit;
    private LocalDateTime signupDate;
}
