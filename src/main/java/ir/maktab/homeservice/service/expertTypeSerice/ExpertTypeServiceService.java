package ir.maktab.homeservice.service.expertTypeSerice;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertTypeService;

public interface ExpertTypeServiceService {

    void removeExpertFromBasicService(Expert expert);

/*
    void addExpertToTypeService(ExpertTypeService expertTypeService);
*/

    void removeExpertFromTypeService(ExpertTypeService expertTypeService);
}
