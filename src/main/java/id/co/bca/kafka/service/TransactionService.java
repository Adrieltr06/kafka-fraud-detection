package id.co.bca.kafka.service;

import id.co.bca.kafka.model.Transaction;
import id.co.bca.kafka.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for handling transaction-related operations.
 */
@Service
@RequiredArgsConstructor
public class TransactionService {

  /**
   * Repository for managing Transaction entities.
   */
  private final TransactionRepository transactionRepository;

  /**
   * Creates a new transaction and saves it to the repository.
   *
   * @param transaction the transaction to create and save
   * @return the saved transaction
   */
  public Transaction createTransaction(Transaction transaction) {
    transactionRepository.save(transaction);
    return transaction;
  }

}