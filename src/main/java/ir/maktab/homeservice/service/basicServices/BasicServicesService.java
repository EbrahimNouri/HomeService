package ir.maktab.homeservice.service.basicServices;

import ir.maktab.homeservice.entity.BasicService;

import java.util.List;

public interface BasicServicesService {


    void addBasicService(BasicService basicService);

    List<BasicService> findAll();

    void removeBasicService(BasicService basicService);

    List<BasicService> showAllBasicService();


    BasicService findById(Long id);

    boolean checkByName(String name);

    void save(BasicService basicService);

    void delete(BasicService basicService);
}