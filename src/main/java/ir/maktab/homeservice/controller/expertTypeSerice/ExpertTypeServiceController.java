package ir.maktab.homeservice.controller.expertTypeSerice;


import ir.maktab.homeservice.entity.ExpertTypeService;

public interface ExpertTypeServiceController {

    void removeExpertFromBasicService(Long expertTypeServiceId);

    void addExpertToTypeService(ExpertTypeService expertTypeService);

    void removeExpertFromTypeService(ExpertTypeService expertTypeService);
}
