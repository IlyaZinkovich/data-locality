package io.github.ilyazinkovich.reliable.communication.remote.payments;

import static org.springframework.http.HttpStatus.ACCEPTED;

import java.math.BigDecimal;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PaymentsApi {

  private static final Logger log = LoggerFactory.getLogger(PaymentsApi.class);

  private final RestTemplate restTemplate = new RestTemplate();

  @PostMapping(path = "/payments")
  public ResponseEntity pay(@RequestBody final Pay command) {
    final PerformanceIncentive performanceIncentive =
        restTemplate.getForObject("http://localhost:8080/incentives/performance/{courierId}",
            PerformanceIncentive.class, command.courierId);
    final Double incentive = Optional.ofNullable(performanceIncentive).map(i -> i.incentive)
        .orElse(0D);
    final BigDecimal amountWithIncentive =
        BigDecimal.valueOf(1.0D + incentive).multiply(command.amount);
    log.info("Paid {} dollars to courier {}", amountWithIncentive, command.courierId);
    return new ResponseEntity(ACCEPTED);
  }
}
