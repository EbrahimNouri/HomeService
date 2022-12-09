package ir.maktab.homeservice.service.transaction;

import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.User;

public interface TransactionService {
    void addTransaction(Transaction transaction);

    void chargeAccountBalance(User user, Double amount);

}
