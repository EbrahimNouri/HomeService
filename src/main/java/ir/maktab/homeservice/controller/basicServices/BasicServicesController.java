package ir.maktab.homeservice.controller.basicServices;

import ir.maktab.homeservice.entity.BasicService;

import java.util.List;

public interface BasicServicesController {



    void addBasicService(BasicService basicService);

    void removeBasicService(BasicService basicService);

    List<BasicService> showAllBasicService();
}
