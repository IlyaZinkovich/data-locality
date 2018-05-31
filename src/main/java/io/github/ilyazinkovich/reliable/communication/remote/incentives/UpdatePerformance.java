package io.github.ilyazinkovich.reliable.communication.remote.incentives;

class UpdatePerformance {

  private final CourierPerformance performance;

  UpdatePerformance(final CourierPerformance performance) {
    this.performance = performance;
  }

  public CourierPerformance performance() {
    return performance;
  }
}
