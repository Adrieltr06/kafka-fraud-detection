package id.co.bca.kafka.controller;

import id.co.bca.kafka.model.Transaction;
import id.co.bca.kafka.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing transaction-related operations.
 */
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

  private final TransactionService transactionService;

  /**
   * Creates a new transaction.
   *
   * @param transaction the transaction to be created
   * @return the created transaction
   */
  @PostMapping
  public Transaction createTransaction(@RequestBody Transaction transaction) {
    return transactionService.createTransaction(transaction);
  }

}