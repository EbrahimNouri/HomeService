package ir.maktab.homeservice.service.expertTypeSerice;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertTypeService;
import ir.maktab.homeservice.entity.id.ExpertTypeServiceId;

import java.util.Optional;

public interface ExpertTypeServiceService {

    void removeExpertFromBasicService(Expert expert);

    void addExpertToTypeService(ExpertTypeService expertTypeService);

    void removeExpertFromTypeService(ExpertTypeService expertTypeService);

    Optional<ExpertTypeService> findById(ExpertTypeServiceId expertTypeServiceId);
}
