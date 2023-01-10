package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.User;
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

    public static Expert personDtoExpertMapping(PersonRegisterDto personRegisterDto) {
        return Expert.builder()
                .firstname(personRegisterDto.getFirstname())
                .lastname(personRegisterDto.getLastname())
                .email(personRegisterDto.getEmail())
                .password(personRegisterDto.getPassword())
                .username(personRegisterDto.getUsername())
                .build();
    }

    public static User personDtoUserMapping(PersonRegisterDto personRegisterDto) {
        return User.builder()
                .firstname(personRegisterDto.getFirstname())
                .lastname(personRegisterDto.getLastname())
                .email(personRegisterDto.getEmail())
                .password(personRegisterDto.getPassword())
                .username(personRegisterDto.getUsername())
                .build();
    }
}
