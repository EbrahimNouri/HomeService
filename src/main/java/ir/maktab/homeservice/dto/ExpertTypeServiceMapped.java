package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.entity.base.Person;
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

    public static ExpertTypeServiceMapped getExpertTypeServiceMapped(Person expert, TypeService typeService) {
        return new ExpertTypeServiceMapped
                (expert.getFirstname()
                        , expert.getLastname()
                        , expert.getEmail()
                        , expert.getAverageScore()
                        , expert.getCredit()
                        , expert.getAverageScore()
                        , typeService.getSubService()
                        , typeService.getBasePrice()
                        , typeService.getBasicService().getName()
                        , typeService.getDescription()
                );
    }

}
