package io.github.ilyazinkovich.reliable.communication.remote.incentives;

import java.math.BigDecimal;

class Pay {

  private final Long courierId;
  private final BigDecimal amount;

  Pay(final Long courierId, final BigDecimal amount) {
    this.courierId = courierId;
    this.amount = amount;
  }
}
