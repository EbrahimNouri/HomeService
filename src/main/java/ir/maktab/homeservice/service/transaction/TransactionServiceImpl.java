package ir.maktab.homeservice.service.transaction;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.TransactionType;
import ir.maktab.homeservice.exception.CustomExceptionSave;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.repository.transaction.TransactionRepository;
import ir.maktab.homeservice.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class TransactionServiceImpl implements TransactionService {

    private UserRepository userRepository;
    private ExpertRepository expertRepository;
    private TransactionRepository repository;

    @Transactional
    @Override
    public void addTransaction(Transaction transaction1) {
        User user = transaction1.getUser();
        Expert expert = transaction1.getExpert();
        double amount = transaction1.getTransfer();
        if (transaction1.getTransfer() <= transaction1.getUser().getCredit()
                && user.getId() != null && expert.getId() != null) {

                user.setCredit(user.getCredit() - amount);
                expert.setCredit(expert.getCredit() + amount);
                userRepository.save(user);
                expertRepository.save(expert);
                transaction1.setUser(user);
                transaction1.setExpert(expert);
                transaction1.setTransactionType(TransactionType.TRANSFER);
                repository.save(transaction1);

                log.debug("error add transaction {} ", transaction1);

        } else {
            log.warn("warn Transfers are more than inventory {} ", transaction1);

            throw new CustomExceptionSave("amount greater than wallet");
        }
    }

    @Transactional
    @Override
    public void chargeAccountBalance(User user, Double amount) {

        user.setCredit(user.getCredit() + amount);
        userRepository.save(user);
        Transaction transaction =
                new Transaction(null, user, null, amount, TransactionType.DEPOSIT);
        repository.save(transaction);

        log.debug("debug add transaction to wallet user {} {} ", user, amount);

    }


    @Override
    public Optional<Transaction> findById(Transaction transaction) {
        Optional<Transaction> transaction1 = Optional.empty();
        try {
            return repository.findByExpertIdAndUserId(
                    transaction1.orElse(null).getExpert().getId(),
                    transaction1.orElse(null).getUser().getId()
            );
        } catch (Exception e) {
            // TODO: 12/12/2022 AD
        }
        return transaction1;
    }
}
