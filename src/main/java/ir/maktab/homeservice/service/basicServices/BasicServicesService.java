package ir.maktab.homeservice.service.basicServices;

import ir.maktab.homeservice.entity.BasicService;

import java.util.List;
import java.util.Optional;

public interface BasicServicesService {



    void addBasicService(BasicService basicService);

    List<BasicService> findAll();

    void removeBasicService(BasicService basicService);

    List<BasicService> showAllBasicService();


    Optional<BasicService> findById(Long id);

    boolean findByName(String name);
}
