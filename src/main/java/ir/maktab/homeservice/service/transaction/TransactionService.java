package ir.maktab.homeservice.service.transaction;

import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    void addTransaction(Transaction transaction);

    void chargeAccountBalance(User user, Double amount);

    void onlinePayment(User user ,Expert expert, Double amount);

    Optional<Transaction> findById(Transaction transaction);

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByExpertId(Long expertId);
}
