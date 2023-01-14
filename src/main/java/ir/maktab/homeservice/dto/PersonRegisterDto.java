package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.entity.base.Person;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonRegisterDto {

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    public static Person personDtoExpertMapping(PersonRegisterDto personRegisterDto) {
        return Person.builder()
                .firstname(personRegisterDto.getFirstname())
                .lastname(personRegisterDto.getLastname())
                .email(personRegisterDto.getEmail())
                .password(personRegisterDto.getPassword())
                .username(personRegisterDto.getUsername())
                .build();
    }

    public static Person personDtoUserMapping(PersonRegisterDto personRegisterDto) {
        return Person.builder()
                .firstname(personRegisterDto.getFirstname())
                .lastname(personRegisterDto.getLastname())
                .email(personRegisterDto.getEmail())
                .password(personRegisterDto.getPassword())
                .username(personRegisterDto.getUsername())
                .build();
    }

    public static Person personDtoAdminMapping(PersonRegisterDto personRegisterDto) {
        return Person.builder()
                .firstname(personRegisterDto.getFirstname())
                .lastname(personRegisterDto.getLastname())
                .email(personRegisterDto.getEmail())
                .password(personRegisterDto.getPassword())
                .username(personRegisterDto.getUsername())
                .build();
    }
}
