package ir.maktab.homeservice.controller.transaction;

import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.User;

import java.util.Optional;

public interface TransactionController {
    void addTransaction(Transaction transaction);

    void chargeAccountBalance(User user, Double amount);

    Optional<Transaction> findById(Transaction transaction);

}
