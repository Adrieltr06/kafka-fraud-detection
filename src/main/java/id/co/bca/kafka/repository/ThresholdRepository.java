package id.co.bca.kafka.repository;

import id.co.bca.kafka.model.Threshold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThresholdRepository extends JpaRepository<Threshold, String> {
}