package io.github.ilyazinkovich.reliable.communication.remote.payments;

import static org.springframework.http.HttpStatus.ACCEPTED;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/payments")
public class PaymentsApi {

  private static final Logger log = LoggerFactory.getLogger(PaymentsApi.class);

  @PostMapping
  public ResponseEntity pay(@RequestBody final Pay command) {
    log.info("Paid {} dollars to courier {}", command.amount, command.courierId);
    return new ResponseEntity(ACCEPTED);
  }
}
