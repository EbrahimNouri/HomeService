package ir.maktab.homeservice.service.expertTypeSerice;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertTypeService;
import ir.maktab.homeservice.repository.expertTypeService.ExpertTypeServiceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
@Transactional
public class ExpertTypeServiceServiceImpl implements ExpertTypeServiceService {
    ExpertTypeServiceRepository repository;

    @Override
    public void removeExpertFromBasicService(Expert expert) {

        repository.removeByExpert(expert);

        log.debug("debug remove expert from basic service {} ", expert);

    }

    @Override
    public void addExpertToTypeService(ExpertTypeService expertTypeService) {
        List<ExpertTypeService> expertTypeServices = findExpertTypeServiceByExpertId(expertTypeService.getExpert().getId());


        if (repository.findByExpertIdAndTypeServiceId
                (expertTypeService.getExpert().getId(), expertTypeService.getTypeService().getId()).isEmpty()
                && expertTypeServices.stream().anyMatch((order) -> order.getTypeService()
                        .getBasicService().equals(expertTypeService.getTypeService().getBasicService()))) {

            repository.save(expertTypeService);
            log.debug("debug add expert to basic service {} ", expertTypeService);


        } else {
            log.error("error add expert to basic service expert type service is null or {} "
                    , expertTypeService.getTypeService());
        }

    }

    @Override
    public void removeExpertFromTypeService(ExpertTypeService expertTypeService) {

        Optional<ExpertTypeService> temp = repository
                .findByExpertIdAndTypeServiceId(expertTypeService.getExpert().getId()
                        , expertTypeService.getTypeService().getId());

        temp.ifPresent(typeService -> repository.delete(expertTypeService));

        log.debug("debug remove expert type service {} ", expertTypeService);
    }

    @Override
    public Optional<ExpertTypeService> findById(Long expertId, Long expertTypeServiceId) {
        return repository.findByExpertIdAndTypeServiceId(expertId, expertTypeServiceId);
    }

    @Override
    public List<ExpertTypeService> findExpertTypeServiceByExpertId(Long expertId) {
        return repository.findExpertTypeServiceByExpertId(expertId);
    }
}
