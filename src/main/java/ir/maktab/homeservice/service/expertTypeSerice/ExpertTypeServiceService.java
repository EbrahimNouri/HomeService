package ir.maktab.homeservice.service.expertTypeSerice;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertTypeService;

import java.util.List;

public interface ExpertTypeServiceService {

    int removeExpertFromBasicService(Expert expert);

    void addExpertToTypeService(ExpertTypeService expertTypeService);

    void removeExpertFromTypeService(ExpertTypeService expertTypeService);


    List<ExpertTypeService> findExpertTypeServiceByExpertId(Long expertId);

    ExpertTypeService findById(Long expertId , Long typeServiceId) ;
}
