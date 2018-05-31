package io.github.ilyazinkovich.reliable.communication.remote.incentives;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/incentives")
public class IncentivesApi {

  private final Map<Long, CourierPerformance> couriersPerformance = new ConcurrentHashMap<>();

  @PostMapping("/bonus")
  public ResponseEntity giveBonus(@RequestBody final GiveBonus command) {
    return new ResponseEntity(ACCEPTED);
  }

  @PostMapping("/performance/{courierId}")
  public ResponseEntity updatePerformance(
      @PathVariable final Long courierId, @RequestBody final UpdatePerformance command) {
    couriersPerformance.put(courierId, command.performance);
    return new ResponseEntity(ACCEPTED);
  }

  @GetMapping("/performance/{courierId}")
  public ResponseEntity<String> getPerformance(@PathVariable final Long courierId) {
    final CourierPerformance courierPerformance = couriersPerformance.get(courierId);
    return new ResponseEntity<>(courierPerformance.name(), OK);
  }
}
