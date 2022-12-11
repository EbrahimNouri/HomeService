package ir.maktab.homeservice.service.basicServices;

import ir.maktab.homeservice.entity.BasicService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface BasicServicesService {



    void addBasicService(BasicService basicService);

    List<BasicService> findAll();

    void removeBasicService(BasicService basicService);

    List<BasicService> showAllBasicService();


    Optional<BasicService> findById(Long id);
}
