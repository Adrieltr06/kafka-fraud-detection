package id.co.bca.kafka.repository;

import id.co.bca.kafka.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Transaction entities.
 * Extends JpaRepository to provide CRUD operations.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}