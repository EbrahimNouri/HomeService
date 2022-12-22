package ir.maktab.homeservice.service.expertTypeSerice;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertTypeService;

import java.util.List;
import java.util.Optional;

public interface ExpertTypeServiceService {

    void removeExpertFromBasicService(Expert expert);

    void addExpertToTypeService(ExpertTypeService expertTypeService);

    void removeExpertFromTypeService(ExpertTypeService expertTypeService);


    List<ExpertTypeService> findExpertTypeServiceByExpertId(Long expertId);

    public Optional<ExpertTypeService> findById(Long expertId , Long expertTypeServiceId) ;

    }
