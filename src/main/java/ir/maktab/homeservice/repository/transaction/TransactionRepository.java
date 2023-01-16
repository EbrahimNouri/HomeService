package ir.maktab.homeservice.repository.transaction;


import ir.maktab.homeservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByExpertIdAndUserId(Long expertId, Long UserID);

    List<Transaction> findTransactionByExpertId(Long expertId);

    List<Transaction> findTransactionByUserId(Long expertId);

}
