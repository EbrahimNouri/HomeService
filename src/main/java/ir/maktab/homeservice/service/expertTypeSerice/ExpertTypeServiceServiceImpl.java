package ir.maktab.homeservice.service.expertTypeSerice;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertTypeService;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.repository.expertTypeService.ExpertTypeServiceRepository;
import ir.maktab.homeservice.repository.typeService.TypeServiceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class ExpertTypeServiceServiceImpl implements ExpertTypeServiceService {
    private final TypeServiceRepository typeServiceRepository;
    ExpertTypeServiceRepository repository;

    @Override
    public int removeExpertFromBasicService(Expert expert) {

        log.debug("debug remove expert from basic service {} ", expert);

        return repository.removeByExpert(expert);

    }

    @Override
    public void addExpertToTypeService(ExpertTypeService expertTypeService) {
        List<ExpertTypeService> expertTypeServices = findExpertTypeServiceByExpertId(expertTypeService.getExpert().getId());

        if (repository.findById
                (expertTypeService.getExpert().getId(), expertTypeService.getTypeService().getId()).isEmpty()
                || repository.findBasicService(expertTypeService.getTypeService().getId())
                .orElseThrow(() -> new CustomExceptionNotFind("expert type service not found"))
                .equals(expertTypeServices.get(0).getTypeService().getBasicService())) {

            repository.save(expertTypeService);
        }
    }

    @Override
    public void removeExpertFromTypeService(ExpertTypeService expertTypeService) {
        Optional<ExpertTypeService> temp = repository
                .findById(expertTypeService.getExpert().getId()
                        , expertTypeService.getTypeService().getId());

        temp.ifPresent(typeService -> repository.delete(expertTypeService));

    }

    @Override
    public ExpertTypeService findById(Long expertId, Long typeServiceId) {

        return repository.findById(expertId, typeServiceId)
                .orElseThrow(() -> new CustomExceptionNotFind("expert type service not found"));

    }

    @Override
    public List<ExpertTypeService> findExpertTypeServiceByExpertId(Long expertId) {
        return repository.findExpertTypeServiceByExpertId(expertId);

    }


}
