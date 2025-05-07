package item;

import entity.Entity;
import core.GamePanel;
import entity.utils.EntityType;

public class Item extends Entity {

    public boolean usable = false;
    public int quantity = 5;
    public boolean alwaysOn = false;
    public boolean enable = false;

    public Item(GamePanel gp) {
        super(gp);
        maxItemQuantity = 5;

    }

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

    public void use(GamePanel gp) {

    }

    @Override
    public String toString() {
        return name;
    }

    public void enable(GamePanel gp) {}

    public void disable(GamePanel gp) {}
}
