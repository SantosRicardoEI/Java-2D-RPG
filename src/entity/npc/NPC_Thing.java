package entity.npc;

import core.GamePanel;
import entity.Entity;

import java.util.Random;

public class NPC_Thing extends Npc {

    public NPC_Thing(GamePanel gp) {
        super(gp);

        this.name = "Blue Boy";
        this.skin = "boy";
        direction = "down";
        baseSpeed = 2;
        currentSpeed = baseSpeed;
        hasIdleAnimation = false;


        getImage();
        setDialog();
    }


    public void setAction() {

        actionLockCounter++;


        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // 1 a 100

            if (i <= 20) {
                direction = "idle";
                currentSpeed = 0;
            } else if (i <= 40) {
                direction = "up";
                currentSpeed = baseSpeed;
            } else if (i <= 60) {
                direction = "down";
                currentSpeed = baseSpeed;
            } else if (i <= 80) {
                direction = "left";
                currentSpeed = baseSpeed;
            } else {
                direction = "right";
                currentSpeed = baseSpeed;
            }
            actionLockCounter = 0;
        }
    }

    @Override
    public void interact(Entity entity) {

    }

    public void setDialog() {

        dialogues[0] = "Tread carefully, wanderer… Not all that\nslithers is harmless. The slimes — don’t be\nfooled by their form.";
        dialogues[1] = "Their touch burns, their bile is poison.\nKeep your blade drawn, and your distance…\nif you value your breath.";
    }

    // Becaus maybe i need to edit speak() later
    public void speak() {

        super.speak();
    }

}
