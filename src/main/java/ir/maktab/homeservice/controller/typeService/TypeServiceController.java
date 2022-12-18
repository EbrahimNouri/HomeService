package ir.maktab.homeservice.controller.typeService;


import ir.maktab.homeservice.dto.PaymentPriceChangeDto;
import ir.maktab.homeservice.dto.TypeServiceDto;
import ir.maktab.homeservice.entity.TypeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TypeServiceController {

    @PostMapping("/addSubService")
    public void addSubService(@RequestBody TypeServiceDto typeServiceDto);

    @PutMapping("/paymentPriceChange")
    public void paymentPriceChange(@RequestBody PaymentPriceChangeDto paymentPriceChangeDto);

    List<TypeService> showTypeServices(Long basicServiceId);
}
