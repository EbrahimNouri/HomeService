package ir.maktab.homeservice.controller.transaction;


import ir.maktab.homeservice.dto.TransactionDto;
import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.TransactionType;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.transaction.TransactionService;
import ir.maktab.homeservice.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("api/v1/transaction")
public class TransactionControllerImpl implements TransactionController {

    private ExpertService expertService;
    private UserService userService;

    private TransactionService transactionService;

    @PostMapping("/addTransaction")
    @Override
    public void addTransaction(@RequestBody TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.TRANSFER)
                .expert(expertService.findById(transactionDto.getExpertId())
                        .orElseThrow(() -> new CustomExceptionNotFind("expert Not found")))
                .user(userService.findById(transactionDto.getUserid())
                        .orElseThrow(() -> new CustomExceptionNotFind("user Not found")))
                .build();

        transactionService.addTransaction(transaction);
    }

    @Override
    public void chargeAccountBalance(User user, Double amount) {

    }

    @Override
    public Optional<Transaction> findById(Transaction transaction) {
        return Optional.empty();
    }
}
