package entity.object;

import entity.Entity;
import core.GamePanel;
import entity.utils.EntityType;
import entity.utils.EntityUtils;

public abstract class Object extends Entity {

    public Object(GamePanel gp) {
        super(gp);
        this.entityType = EntityType.OBJECT;
        hasIdleAnimation = true;
        hasIdleAnimation = false;
    }

    public abstract void interact(Entity entity);

    @Override
    public void setAction() {

    }

    @Override
    public void tryStartAttack() {

    }

    @Override
    public void damageReaction() {

    }

    @Override
    public void deathReaction() {

    }

    @Override
    public void update() {

    }

    @Override
    public void getImage() {
        String type = entityType.name().toLowerCase();

        down1 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_down_1");
        if (hasIdleAnimation) {
            down2 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_down_2");
        }
    }
}