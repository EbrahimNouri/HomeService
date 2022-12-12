package ir.maktab.homeservice.repository.transaction;


import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.id.TransactionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, TransactionId> {

    Optional<Transaction> findByExpertIdAndUserId(Long expertId, Long UserID);

}
