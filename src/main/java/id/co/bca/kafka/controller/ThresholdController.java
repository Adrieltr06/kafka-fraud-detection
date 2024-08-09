package id.co.bca.kafka.controller;

import id.co.bca.kafka.model.Threshold;
import id.co.bca.kafka.service.ThresholdService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing threshold-related operations.
 */
@RestController
@RequestMapping("/api/thresholds")
@RequiredArgsConstructor
public class ThresholdController {

  private final ThresholdService thresholdService;

  /**
   * Creates a new threshold.
   *
   * @param threshold the threshold to be created
   * @return the created threshold
   */
  @PostMapping
  public Threshold createThreshold(@RequestBody Threshold threshold) {
    return thresholdService.saveThreshold(threshold);
  }

  /**
   * Updates an existing threshold.
   *
   * @param threshold the threshold to be updated
   * @return the updated threshold
   */
  @PutMapping
  public Threshold updateThreshold(@RequestBody Threshold threshold) {
    return thresholdService.saveThreshold(threshold);
  }

}