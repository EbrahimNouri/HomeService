package ir.maktab.homeservice.controller.transaction;


import java.util.Optional;

public interface TransactionController {


    Optional<Transaction> findById(Transaction transaction);

}
