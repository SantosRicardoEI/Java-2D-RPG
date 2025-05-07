package entity.npc;

import core.GamePanel;
import entity.Entity;

import java.util.Random;

public class NPC_OldMan extends Npc {

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        this.skin = "oldman";
        direction = "down";
        baseSpeed = 1;
        currentSpeed = baseSpeed;
        name = "Old Jose";
        hasIdleAnimation = false;

        getImage();
        setDialog();
    }



    @Override
    public void interact(Entity entity) {
       speak();
    }


    public void setDialog() {

        dialogues[0] = "Ah... you again, brave soul.";
        dialogues[1] = "Watch your step out there.";
        dialogues[2] = "Those slimes — don't let their\nshape fool you.";
        dialogues[3] = "They're filled with venom...\nnasty stuff.";
        dialogues[4] = "Keep your distance, and you'll\nlive to see another fire.";

    }

    // Becaus maybe i need to edit speak() later
    public void speak() {
        super.speak();
    }

}
