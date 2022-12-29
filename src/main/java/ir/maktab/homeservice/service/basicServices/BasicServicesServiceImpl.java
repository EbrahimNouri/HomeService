package ir.maktab.homeservice.service.basicServices;

import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.exception.CustomExceptionSave;
import ir.maktab.homeservice.repository.basicService.BasicServiceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Log4j2
@AllArgsConstructor
public class BasicServicesServiceImpl implements BasicServicesService {
    private final BasicServiceRepository repository;


    @Transactional
    @Override
    public void addBasicService(BasicService basicService) {

        if (!repository.existsByName(basicService.getName())) {

            repository.save(basicService);

        } else {
            log.error("error add basic service name is invalid");
            throw new CustomExceptionSave("this name is invalid");
        }
    }

    @Override
    public List<BasicService> findAll() {

        return repository.findAll();
    }


    @Override
    public void removeBasicService(BasicService basicService) {

        if (checkByName(basicService.getName())) {

            repository.delete(basicService);

            log.debug("debug remove basic service {} ", basicService);

        } else {
            log.warn("warn add basic service  found name {} ", basicService);
            throw new CustomExceptionNotFind("basicService not found");
        }
    }

    @Override
    public List<BasicService> showAllBasicService() {
        return repository.findAll();
    }

    @Override
    public BasicService findById(Long id) {
        return repository.findBasicServiceById(id)
                .orElseThrow(() -> new CustomExceptionNotFind("basic service not found"));

    }

    @Override
    public boolean checkByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public void save(BasicService basicService){
        repository.save(basicService);
    }

    @Override
    public void delete(BasicService basicService){
        repository.delete(basicService);
    }
}