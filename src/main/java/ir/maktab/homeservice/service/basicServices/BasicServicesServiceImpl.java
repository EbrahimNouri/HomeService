package ir.maktab.homeservice.service.basicServices;

import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.repository.basicService.BasicServiceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Log4j2
@AllArgsConstructor
@Transactional
public class BasicServicesServiceImpl implements BasicServicesService {

    private final BasicServiceRepository repository;

    @Override
    public void addBasicService(BasicService basicService) {

        try {
            if (!repository.findByName(basicService.getName())) {

                repository.save(basicService);

            } else
                log.error("error add basic service name is null");
        } catch (Exception e) {
            log.debug("error add basic service {} ", basicService, e);
        }
    }


    @Override
    public void removeBasicService(BasicService basicService) {
        try {

            if (repository.findByName(basicService.getName())) {

                repository.delete(basicService);

                log.debug("debug remove basic service {} ", basicService);

            } else
                log.warn("warn add basic service  found name {} ", basicService);

        } catch (Exception e) {
            log.error("error add basic service {} ", basicService);

        }
    }

    @Override
    public List<BasicService> showAllBasicService() {
        List<BasicService> all = null;
        try {
            all = repository.findAll();

        } catch (Exception e) {
            log.error("error show all basic service");
        }
        return all;
    }
}