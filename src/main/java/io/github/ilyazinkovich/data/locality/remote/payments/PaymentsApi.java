package io.github.ilyazinkovich.data.locality.remote.payments;

import static org.springframework.http.HttpStatus.ACCEPTED;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/payments")
public class PaymentsApi {

  @PostMapping
  public ResponseEntity pay(@RequestBody final Pay command) {
    return new ResponseEntity(ACCEPTED);
  }
}
