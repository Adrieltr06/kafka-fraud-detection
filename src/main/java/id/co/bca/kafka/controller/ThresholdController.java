package id.co.bca.kafka.controller;

import id.co.bca.kafka.model.Threshold;
import id.co.bca.kafka.service.ThresholdService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/thresholds")
@RequiredArgsConstructor
public class ThresholdController {

  private final ThresholdService thresholdService;

  @PostMapping
  public Threshold createThreshold(@RequestBody Threshold threshold) {
    return thresholdService.createThreshold(threshold);
  }

}