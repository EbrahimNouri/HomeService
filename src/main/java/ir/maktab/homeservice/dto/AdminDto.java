package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.entity.base.Person;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@Builder
public class AdminDto {
    String firstname;
    String lastname;
    String username;
    String email;
    String password;

    public static AdminDto adminMapper(Person admin){
        return AdminDto.builder()
                .firstname(admin.getFirstname())
                .lastname(admin.getLastname())
                .email(admin.getEmail())
                .password(admin.getPassword())
                .username(admin.getUsername())
                .build();
    }

    public static Person adminDtoMapper(AdminDto adminDto){
        return Person.builder()
                .firstname(adminDto.getFirstname())
                .lastname(adminDto.getLastname())
                .email(adminDto.getEmail())
                .password(adminDto.getPassword())
                .username(adminDto.getUsername())
                .build();
    }
}
