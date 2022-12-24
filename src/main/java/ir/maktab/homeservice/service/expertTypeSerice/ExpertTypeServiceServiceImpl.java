package ir.maktab.homeservice.service.expertTypeSerice;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertTypeService;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.repository.expertTypeService.ExpertTypeServiceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class ExpertTypeServiceServiceImpl implements ExpertTypeServiceService {
    ExpertTypeServiceRepository repository;

    @Override
    public int removeExpertFromBasicService(Expert expert) {
/*        try {*/
            log.debug("debug remove expert from basic service {} ", expert);

            return repository.removeByExpert(expert);
/*        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void addExpertToTypeService(ExpertTypeService expertTypeService) {
        List<ExpertTypeService> expertTypeServices = findExpertTypeServiceByExpertId(expertTypeService.getExpert().getId());
        try {

            if (repository.findById
                    (expertTypeService.getExpert().getId(), expertTypeService.getTypeService().getId()).isEmpty()
                    || repository.findBasicService(expertTypeService.getTypeService().getId()).get()
                    .equals(expertTypeServices.get(0).getTypeService().getBasicService())){

                repository.save(expertTypeService);
                log.debug("debug add expert to basic service {} ", expertTypeService);


            } else{
                log.error("error add expert to basic service expert type service is null or {} "
                        , expertTypeService.getTypeService());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeExpertFromTypeService(ExpertTypeService expertTypeService) {
/*
        try {
*/
            Optional<ExpertTypeService> temp = repository
                    .findById(expertTypeService.getExpert().getId()
                            , expertTypeService.getTypeService().getId());

            temp.ifPresent(typeService -> repository.delete(expertTypeService));


            log.debug("debug remove expert type service {} ", expertTypeService);
/*        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public ExpertTypeService findById(Long expertId, Long typeServiceId) {
/*
        try {
*/
            return repository.findById(expertId, typeServiceId)
                    .orElseThrow(() -> new CustomExceptionNotFind("expert type service not found"));

/*        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();*/
    }

    @Override
    public List<ExpertTypeService> findExpertTypeServiceByExpertId(Long expertId) {
        try {
            return repository.findExpertTypeServiceByExpertId(expertId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
