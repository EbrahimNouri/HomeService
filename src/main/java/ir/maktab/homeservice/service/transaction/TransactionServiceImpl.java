package ir.maktab.homeservice.service.transaction;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.id.TransactionId;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.repository.transaction.TransactionRepository;
import ir.maktab.homeservice.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
        User user = transaction1.getTransactionId().getUser();
        Expert expert = transaction1.getTransactionId().getExpert();
        double amount = transaction1.getTransfer();
        if (transaction1.getTransfer() >= transaction1.getTransactionId().getUser().getCredit()) {

            try {
                user.setCredit(user.getCredit() - amount);
                expert.setCredit(expert.getCredit() + amount);
                userRepository.save(user);
                expertRepository.save(expert);
                transaction1.getTransactionId().setUser(user);
                transaction1.getTransactionId().setExpert(expert);
                repository.save(transaction1);

                log.debug("error add transaction {} ", transaction1);
            } catch (Exception e) {

                log.error("error add transaction {} ", transaction1, e);

            }
        } else {

            log.warn("warn Transfers are more than inventory {} ", transaction1);

        }
    }

    @Transactional
    @Override
    public void chargeAccountBalance(User user, Double amount) {
        try {

            user.setCredit(user.getCredit() + amount);
            userRepository.save(user);
            Transaction transaction =
                    new Transaction((new TransactionId(null, user, null)), amount);
            repository.save(transaction);

            log.debug("debug add transaction to wallet user {} {} ", user, amount);
        } catch (Exception e) {

            log.error("error add transaction to wallet user {} {} ", user, amount, e);
        }
    }
}
