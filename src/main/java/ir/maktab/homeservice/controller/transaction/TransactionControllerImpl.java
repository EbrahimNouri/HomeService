package ir.maktab.homeservice.controller.transaction;


import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("api/v1/transaction")
public class TransactionControllerImpl implements TransactionController {

    @Override
    public void addTransaction(Transaction transaction) {

    }

    @Override
    public void chargeAccountBalance(User user, Double amount) {

    }

    @Override
    public Optional<Transaction> findById(Transaction transaction) {
        return Optional.empty();
    }
}
