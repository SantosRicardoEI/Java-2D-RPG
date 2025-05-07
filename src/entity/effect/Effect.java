package entity.effect;

import entity.Entity;

public abstract class Effect {

    public final EffectType type;
    public final int duration;
    public int tickCounter = 0;

    public Effect(EffectType type, int duration) {
        this.type = type;
        this.duration = duration;
    }

    public abstract void tick(Entity target); // o que o efeito faz a cada frame
}