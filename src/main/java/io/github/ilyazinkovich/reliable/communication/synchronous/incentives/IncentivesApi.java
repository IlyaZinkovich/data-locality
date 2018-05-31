package io.github.ilyazinkovich.reliable.communication.synchronous.incentives;

import static io.github.ilyazinkovich.reliable.communication.synchronous.incentives.CourierPerformance.HIGH;
import static io.github.ilyazinkovich.reliable.communication.synchronous.incentives.CourierPerformance.NORMAL;
import static io.github.ilyazinkovich.reliable.communication.synchronous.incentives.CourierPerformance.OUTSTANDING;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

import com.google.common.collect.ImmutableMap;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class IncentivesApi {

  private static final Logger log = LoggerFactory.getLogger(IncentivesApi.class);

  private final Map<Long, CourierPerformance> couriersPerformance = new ConcurrentHashMap<>();
  private final Map<BonusType, BigDecimal> paymentAmountPerBonusType =
      ImmutableMap.<BonusType, BigDecimal>builder()
          .put(BonusType.SMALL, new BigDecimal(5))
          .put(BonusType.AVERAGE, new BigDecimal(10))
          .put(BonusType.BIG, new BigDecimal(20))
          .build();
  private final Map<CourierPerformance, Double> performanceIncentives =
      ImmutableMap.<CourierPerformance, Double>builder()
          .put(NORMAL, 0D)
          .put(HIGH, 0.10D)
          .put(OUTSTANDING, 0.20D)
          .build();
  private final RestTemplate restTemplate = new RestTemplate();

  @PostMapping(path = "/incentives/bonuses/{courierId}")
  public ResponseEntity giveBonus(
      @PathVariable final Long courierId, @RequestBody final GiveBonus command) {
    final BigDecimal paymentAmount = paymentAmountPerBonusType.get(command.bonusType);
    final Pay pay = new Pay(courierId, paymentAmount);
    restTemplate.postForEntity("http://localhost:8080/payments", pay, String.class);
    log.info("Gave {} bonus to courier {}", command.bonusType, courierId);
    return new ResponseEntity(ACCEPTED);
  }

  @PostMapping(path = "/incentives/performance/{courierId}")
  public ResponseEntity updatePerformance(
      @PathVariable final Long courierId, @RequestBody final UpdatePerformance command) {
    couriersPerformance.put(courierId, command.performance);
    return new ResponseEntity(ACCEPTED);
  }

  @GetMapping(path = "/incentives/performance/{courierId}")
  public ResponseEntity<PerformanceIncentive> getPerformanceIncentive(
      @PathVariable final Long courierId) {
    final CourierPerformance courierPerformance = couriersPerformance.get(courierId);
    final Double incentive = performanceIncentives.get(courierPerformance);
    return new ResponseEntity<>(new PerformanceIncentive(incentive), OK);
  }
}
