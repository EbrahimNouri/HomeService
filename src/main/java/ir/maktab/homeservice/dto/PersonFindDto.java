package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.User;
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

    public static PersonFindDto userTopPersonFindDtoMapper(User user) {
        return PersonFindDto.builder()
                .id(user.getId())
                .signupDate(user.getSignupDateTime())
                .personType(PersonType.USER)
                .email(user.getEmail())
                .credit(user.getCredit())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .build();
    }

    public static PersonFindDto expertTopPersonFindDtoMapper(Expert expert) {
        return PersonFindDto.builder()
                .id(expert.getId())
                .signupDate(expert.getSignupDateTime())
                .personType(PersonType.EXPERT)
                .email(expert.getEmail())
                .credit(expert.getCredit())
                .firstname(expert.getFirstname())
                .lastname(expert.getLastname())
                .build();
    }
}
