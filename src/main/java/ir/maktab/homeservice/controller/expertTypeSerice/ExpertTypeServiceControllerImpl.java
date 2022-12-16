package ir.maktab.homeservice.controller.expertTypeSerice;

import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertTypeService;
import ir.maktab.homeservice.entity.id.ExpertTypeServiceId;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("api/v1/expertService")
class ExpertTypeServiceControllerImpl implements ExpertTypeServiceController{


    @Override
    public void removeExpertFromBasicService(Expert expert) {

    }

    @PostMapping
    @Override
    public void addExpertToTypeService(ExpertTypeService expertTypeService) {

    }

    @Override
    public void removeExpertFromTypeService(ExpertTypeService expertTypeService) {

    }

    @Override
    public Optional<ExpertTypeService> findById(ExpertTypeServiceId expertTypeServiceId) {
        return Optional.empty();
    }
}