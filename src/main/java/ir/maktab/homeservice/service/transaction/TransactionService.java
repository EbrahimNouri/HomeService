package ir.maktab.homeservice.service.transaction;

import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    void addTransaction(Transaction transaction);

    void chargeAccountBalance(User user, Double amount, String card);

    void onlinePayment(User user ,Expert expert, Double amount);

    Optional<Transaction> findById(Transaction transaction);

    List<Transaction> findTransactionByUserId(Long userId);

    List<Transaction> findTransactionByExpertId(Long expertId);

}
