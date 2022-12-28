package ir.maktab.homeservice.dto;

import lombok.Data;

@Data
public class PersonChangePasswordDto {
    private Long id;
    private String password;
}
