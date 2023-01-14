package ir.maktab.homeservice.service.transaction;

import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.base.Person;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    void addTransaction(Transaction transaction);

    void chargeAccountBalance(Person user, Double amount);

    @Transactional
    void onlinePayment(Person expert, Double amount);

    Optional<Transaction> findById(Transaction transaction);

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByExpertId(Long expertId);
}
