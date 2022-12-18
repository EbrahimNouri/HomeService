package ir.maktab.homeservice.controller.transaction;

import ir.maktab.homeservice.dto.TransactionDto;
import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public interface TransactionController {
    @PostMapping("/addTransaction")
    public void addTransaction(@RequestBody TransactionDto transactionDto);
    void chargeAccountBalance(User user, Double amount);

    Optional<Transaction> findById(Transaction transaction);

}
