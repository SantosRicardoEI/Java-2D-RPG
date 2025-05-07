package entity.effect;

import entity.Entity;

public class SlowEffect extends Effect {

    private final float slowAmount;

    public SlowEffect(int duration, float slowAmount) {
        super(EffectType.SLOW, duration);
        this.slowAmount = slowAmount;
    }

    @Override
    public void tick(Entity target) {
        target.currentSpeed = (int) (target.baseSpeed * (1f - slowAmount));
    }
}