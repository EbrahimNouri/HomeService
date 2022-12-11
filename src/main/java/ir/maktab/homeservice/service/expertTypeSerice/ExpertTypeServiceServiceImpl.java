package ir.maktab.homeservice.service.expertTypeSerice;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertTypeService;
import ir.maktab.homeservice.entity.id.ExpertTypeServiceId;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.repository.expertTypeService.ExpertTypeServiceRepository;
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
    private final ExpertRepository expertRepository;
    ExpertTypeServiceRepository repository;

    @Override
    public void removeExpertFromBasicService(Expert expert) {
        try {

            repository.removeByExpert(expert);

            log.debug("debug remove expert from basic service {} ", expert);
        } catch (Exception e) {

            log.error("error remove expert from basic service {} ", expert);
        }
    }

    @Override
    public void addExpertToTypeService(ExpertTypeService expertTypeService) {
        try {

            if (!repository.existsById(new ExpertTypeServiceId(expertTypeService.getExpert()
                    , expertTypeService.getTypeService()))) {
                repository.save(expertTypeService);

                log.debug("debug add expert to basic service {} ", expertTypeService);
            } else {
                log.error("error add expert to basic service expert type service is null or {} "
                        , expertTypeService.getTypeService());
            }
        } catch (Exception e) {

            log.error("error add expert to basic service {} ", expertTypeService, e);
        }

    }

    @Override
    public void removeExpertFromTypeService(ExpertTypeService expertTypeService) {

        try {
            Optional<ExpertTypeService> temp = repository
                    .findByExpertIdAndTypeServiceId(expertTypeService.getExpert().getId()
                            , expertTypeService.getTypeService().getId());

            temp.ifPresent(typeService -> repository.delete(expertTypeService));

            log.debug("debug remove expert type service {} ", expertTypeService);

        } catch (Exception e) {

            log.error("error remove expert type service {} ", expertTypeService, e);
        }
    }

    @Override
    public Optional<ExpertTypeService> findById(ExpertTypeServiceId expertTypeServiceId) {
        Optional<ExpertTypeService> expertTypeService = Optional.empty();
        try {
            expertTypeService = repository.findById(expertTypeServiceId);
        }catch (Exception e){
            // TODO: 12/11/2022 AD
        }
        return expertTypeService;
    }
}
