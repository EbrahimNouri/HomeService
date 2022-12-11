package ir.maktab.homeservice.controller.transaction;

import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.User;

public interface TransactionController {
    void addTransaction(Transaction transaction);

    void chargeAccountBalance(User user, Double amount);

}
