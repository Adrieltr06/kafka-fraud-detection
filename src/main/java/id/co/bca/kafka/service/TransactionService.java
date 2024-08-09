package id.co.bca.kafka.service;

import id.co.bca.kafka.model.Transaction;
import id.co.bca.kafka.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

  private final TransactionRepository transactionRepository;

  public Transaction createTransaction(Transaction transaction) {
    transactionRepository.save(transaction);
    return transaction;
  }
}