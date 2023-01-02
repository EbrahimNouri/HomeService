package ir.maktab.homeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class adminPassDto {
    private Long id;
    private String username;
    private String password;
}