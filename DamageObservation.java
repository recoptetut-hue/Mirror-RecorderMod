package com.mirror.recorder.handler;

/** A synchronized baseline plus vanilla hurt evidence distinguishes hits from absorption expiry. */
public final class DamageObservation {
  private static final float HEALTH_EPSILON = 0.01f;
  private Object player;
  private float health, total;
  private int lastHurtTime;
  private boolean synchronizedHealth, unconsumedHurt;

  public boolean observe(
      Object nextPlayer, float nextHealth, float absorption, boolean validHealth, int hurtTime) {
    boolean comparable =
        nextPlayer != null && player == nextPlayer && synchronizedHealth && validHealth;
    if (!comparable || hurtTime <= 0) unconsumedHurt = false;
    else if (hurtTime > lastHurtTime) unconsumedHurt = true;
    float nextTotal = nextHealth + absorption;
    boolean damaged =
        comparable
            && hurtTime > 0
            && nextTotal < total - HEALTH_EPSILON
            && (nextHealth < health - HEALTH_EPSILON || unconsumedHurt);
    if (damaged) unconsumedHurt = false;
    player = nextPlayer;
    health = nextHealth;
    total = nextTotal;
    lastHurtTime = hurtTime;
    synchronizedHealth = nextPlayer != null && validHealth;
    return damaged;
  }
}
