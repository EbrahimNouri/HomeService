package ir.maktab.homeservice.service.expertTypeSerice;


import ir.maktab.homeservice.entity.ExpertTypeService;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.repository.expertTypeSerice.ExpertTypeServiceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
@Transactional
public class ExpertTypeServiceServiceImpl implements ExpertTypeServiceService {
    ExpertTypeServiceRepository repository;

    @Override
    public void removeExpertFromBasicService(Long expertTypeServiceId) {
        try {

            repository.removeTypeServiceByExpertId(expertTypeServiceId);

            log.debug("debug remove expert from basic service {} ", expertTypeServiceId);
        } catch (Exception e) {

            log.error("error remove expert from basic service {} ", expertTypeServiceId);
        }
    }

    @Override
    public void addExpertToTypeService(ExpertTypeService expertTypeService) {

        var temp = repository.findByExpertAndTypeService
                (expertTypeService.getExpertTypeServiceId().getExpert().getId()
                        , expertTypeService.getExpertTypeServiceId().getTypeService().getId());
        try {
            if (temp.isPresent()
                    && expertTypeService.getExpertTypeServiceId().getExpert()
                    .getExpertStatus().equals(ExpertStatus.CONFIRMED)) {

                repository.save(expertTypeService);

                log.debug("debug add expert to basic service {} ", expertTypeService);
            } else

                log.error("error add expert to basic service expert type service is null or {} "
                        , expertTypeService.getExpertTypeServiceId().getTypeService());
        } catch (Exception e) {

            log.error("error add expert to basic service {} ", expertTypeService, e);
        }
    }

    @Override
    public void removeExpertFromTypeService(ExpertTypeService expertTypeService) {

        try {
            Optional<ExpertTypeService> temp = repository
                    .findByExpertAndTypeService(expertTypeService.getExpertTypeServiceId().getExpert().getId()
                            , expertTypeService.getExpertTypeServiceId().getTypeService().getId());

            temp.ifPresent(typeService -> repository
                    .removeTypeServiceByExpertId(typeService.getExpertTypeServiceId().getExpert().getId()));

            log.debug("debug remove expert type service {} ", expertTypeService);

        } catch (Exception e) {

            log.error("error remove expert type service {} ", expertTypeService, e);
        }
    }
}
