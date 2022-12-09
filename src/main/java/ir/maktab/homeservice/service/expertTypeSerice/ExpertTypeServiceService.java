package ir.maktab.homeservice.service.expertTypeSerice;


import ir.maktab.homeservice.entity.ExpertTypeService;

public interface ExpertTypeServiceService {

    void removeExpertFromBasicService(Long expertTypeServiceId);

    void addExpertToTypeService(ExpertTypeService expertTypeService);

    void removeExpertFromTypeService(ExpertTypeService expertTypeService);
}
