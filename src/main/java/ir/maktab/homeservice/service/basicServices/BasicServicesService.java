package ir.maktab.homeservice.service.basicServices;

import ir.maktab.homeservice.entity.BasicService;

import java.util.List;

public interface BasicServicesService {



    void addBasicService(BasicService basicService);

    void removeBasicService(BasicService basicService);

    List<BasicService> showAllBasicService();
}
