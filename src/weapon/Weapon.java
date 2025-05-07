package weapon;

import entity.Entity;
import entity.player.Player;
import core.GamePanel;

public class Weapon extends Entity {

    public int extraPixelsX;
    public int extraPixelsY;

    public int hitDamage;
    public int staminaCost;
    public int attackSpeed;
    public float range;
    public String scalingAttribute;
    public double playerSpeed;
    public int impactFrame;
    public int recoveryTime;
    public int initSpeed;

    public int soundIndex = 9;
    public int soundSet[];

    public Weapon(GamePanel gp) {
        super(gp);
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

    // Random sound of weapon soundSet
    public void playSound() {
        if (soundSet != null) {
            int index = (int) (Math.random() * soundSet.length);
            gp.soundM.playSE(soundSet[index]);
        }
    }


    public int scaledDamage(Player player) {
        return 0;
    }

    @Override
    public String toString() {
        return "\nWeapon: " + name +
                "\nBase damage: " + hitDamage +
                "\nPlayer Damage: " + gp.player.currentAttackDamage +
                "\nStamina Cost: " + staminaCost +
                "\nAttack Speed: " + attackSpeed +
                "\nRange: " + range +
                "\nScaling Attribute: " + scalingAttribute;
    }

}
