package ir.maktab.homeservice.service.typeService;


import ir.maktab.homeservice.entity.TypeService;

import java.util.List;

public interface TypeServiceService {
    void addSubService(TypeService typeService);

    void paymentPriceChange(TypeService typeService, double price);

    List<TypeService> showTypeServices(Long basicServiceId);
}
