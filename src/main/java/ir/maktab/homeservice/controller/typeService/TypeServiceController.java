package ir.maktab.homeservice.controller.typeService;


import ir.maktab.homeservice.entity.TypeService;

import java.util.List;

public interface TypeServiceController {

    void addSubService(TypeService typeService);

    void paymentPriceChange(TypeService typeService, double price);

    List<TypeService> showTypeServices(Long basicServiceId);
}
