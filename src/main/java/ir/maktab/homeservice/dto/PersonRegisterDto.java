package ir.maktab.homeservice.dto;

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
}
