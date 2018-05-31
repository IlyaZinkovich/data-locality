package io.github.ilyazinkovich.reliable.communication.remote.incentives;

class GiveBonus {

  private final Long courierId;
  private final BonusType bonusType;

  GiveBonus(final Long courierId, final BonusType bonusType) {
    this.courierId = courierId;
    this.bonusType = bonusType;
  }
}
