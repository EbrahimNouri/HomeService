package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.entity.Admin;
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
        String name;
        String username;
        String email;

        public static AdminDto adminDtoMapper(Admin admin) {
                return AdminDto.builder()
                        .username(admin.getUsername())
                        .name(admin.getName())
                        .email(admin.getEmail())
                        .build();
        }
}
