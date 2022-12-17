package ir.maktab.homeservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter

public class PersonRegisterDto {

    private String firstname;

    private String lastname;

    private String email;

    private String password;
}
