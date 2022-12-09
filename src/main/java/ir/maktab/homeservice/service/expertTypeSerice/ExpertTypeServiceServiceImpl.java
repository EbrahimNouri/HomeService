package ir.maktab.homeservice.service.expertTypeSerice;


import ir.maktab.homeservice.entity.ExpertTypeService;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.repository.expertTypeSerice.ExpertTypeServiceRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ExpertTypeServiceServiceImpl implements ExpertTypeServiceService {
    ExpertTypeServiceRepository repository;

    @Override
    public void removeExpertFromBasicService(Long expertTypeServiceId) {

        repository.removeTypeServiceByExpertId(expertTypeServiceId);

    }

    @Override
    public void addExpertToTypeService(ExpertTypeService expertTypeService) {

        var temp = repository.findByExpertAndTypeService
                (expertTypeService.getExpertTypeServiceId().getExpert().getId()
                , expertTypeService.getExpertTypeServiceId().getTypeService().getId());

        if (temp.isPresent()
                && expertTypeService.getExpertTypeServiceId().getExpert()
                .getExpertStatus().equals(ExpertStatus.CONFIRMED)) {

            repository.save(expertTypeService);

        }
    }

        @Override
        public void removeExpertFromTypeService (ExpertTypeService expertTypeService){


                Optional<ExpertTypeService> temp = repository
                        .findByExpertAndTypeService(expertTypeService.getExpertTypeServiceId().getExpert().getId()
                                , expertTypeService.getExpertTypeServiceId().getTypeService().getId());

            temp.ifPresent(typeService -> repository
                    .removeTypeServiceByExpertId(typeService.getExpertTypeServiceId().getExpert().getId()));
        }
    }
