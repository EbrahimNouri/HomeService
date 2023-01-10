package ir.maktab.homeservice.service.transaction;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.TransactionType;
import ir.maktab.homeservice.exception.CustomExceptionAmount;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.exception.CustomExceptionSave;
import ir.maktab.homeservice.repository.transaction.TransactionRepository;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class TransactionServiceImpl implements TransactionService {

    private UserService userService;
    private ExpertService expertService;
    private TransactionRepository repository;


    @Transactional
    @Override
    public void addTransaction(Transaction transaction1) {
        User user = transaction1.getUser();
        Expert expert = transaction1.getExpert();
        double amount = transaction1.getTransfer();

        if (transaction1.getTransfer() <= transaction1.getUser().getCredit()) {
            throw new CustomExceptionAmount("amount greater than wallet");

        }
        if (user.getId() == null || expert.getId() == null) {
            throw new CustomExceptionSave("transaction not valid");
        }

        user.setCredit(user.getCredit() - amount);
        expert.setCredit(expert.getCredit() + (amount * 70) / 100);
        userService.save(user);
        expertService.save(expert);
        transaction1.setUser(user);
        transaction1.setExpert(expert);
        transaction1.setTransactionType(TransactionType.TRANSFER);
        repository.save(transaction1);

        log.debug("error add transaction {} ", transaction1);

    }


    @Transactional
    @Override
    public void chargeAccountBalance(User user, Double amount) {
        if (user.getId() == null)
            throw new CustomExceptionNotFind("user not valid");

        user.setCredit(user.getCredit() + amount);
        userService.save(user);
        Transaction transaction = Transaction.builder()
                .user(user)
                .transfer(amount)
                .transactionType(TransactionType.DEPOSIT)
                .build();

        repository.save(transaction);

        log.debug("debug add transaction to wallet user {} {} ", user, amount);

    }

    @Transactional
    @Override
    public void onlinePayment(Expert expert, Double amount) {
        if (expert.getId() == null)
            throw new CustomExceptionNotFind("user not valid");

        expert.setCredit(expert.getCredit() + (amount * 70) / 100);
        expertService.save(expert);
        Transaction transaction = Transaction.builder()
                .expert(expert)
                .transfer(amount)
                .transactionType(TransactionType.ONLINE_PAYMENT)
                .build();
        repository.save(transaction);

        log.debug("debug add transaction to wallet user {} {} ", expert, amount);

    }


    @Override
    public Optional<Transaction> findById(Transaction transaction) {
        Optional<Transaction> transaction1 = Optional.empty();

        return repository.findByExpertIdAndUserId(
                transaction.getExpert().getId(),
                transaction.getUser().getId());

    }

    @Override
    public List<Transaction> findByUserId(Long userId){
        return repository.findTransactionByUserId(userId);
    }
    @Override
    public List<Transaction> findByExpertId(Long expertId){
        return repository.findTransactionByUserId(expertId);
    }
}
