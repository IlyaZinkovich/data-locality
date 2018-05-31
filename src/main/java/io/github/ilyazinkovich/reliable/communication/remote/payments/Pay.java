package io.github.ilyazinkovich.reliable.communication.remote.payments;

import java.math.BigDecimal;

public class Pay {

  final Long courierId;
  final BigDecimal amount;

  public Pay(final Long courierId, final BigDecimal amount) {
    this.courierId = courierId;
    this.amount = amount;
  }
}
