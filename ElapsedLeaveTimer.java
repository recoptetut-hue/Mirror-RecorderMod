package com.mirror.recorder.handler;

/** A one-shot timer measured with a monotonic clock rather than game ticks or wall-clock time. */
final class ElapsedLeaveTimer {
  private static final long NANOS_PER_MINUTE = 60000000000L;
  private long started, duration;
  private boolean armed;

  void start(long nowNanos, int minutes) {
    started = nowNanos;
    duration = Math.max(1, Math.min(525600, minutes)) * NANOS_PER_MINUTE;
    armed = true;
  }

  boolean expired(long nowNanos) {
    return armed && nowNanos - started >= duration;
  }

  void cancel() {
    armed = false;
  }
}
