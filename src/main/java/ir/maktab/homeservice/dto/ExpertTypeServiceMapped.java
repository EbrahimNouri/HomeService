package ir.maktab.homeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpertTypeServiceMapped {

    private String firstname;
    private String lastname;
    private String email;
    private Double averageScore;
/*
    private byte[] avatar;
*/
    private Double credit;
    private Double averageScores;
    private String typeServiceName;
    private double basePrice;
    private String basicService;
    private String description;

}
