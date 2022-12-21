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
import java.util.Optional;


@Service
@Log4j2
@AllArgsConstructor
public class BasicServicesServiceImpl implements BasicServicesService {

    private BasicServiceRepository repository;


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
    public Optional<BasicService> findById(Long id) {
        return repository.findBasicServiceById(id);
    }

    @Override
    public boolean checkByName(String name){
        return repository.existsByName(name);
    }
}