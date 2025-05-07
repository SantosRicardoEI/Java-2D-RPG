package entity.npc;

import core.states.DialogState;
import entity.Entity;
import core.GamePanel;
import entity.utils.EntityType;

public abstract class Npc extends Entity {

    // === DIALOGS ===
    public String dialogues[] = new String[20];
    public int dialogueIndex = 0;


    public Npc(GamePanel gp) {
        super(gp);
        this.entityType = EntityType.NPC;
        isCollidable = true;
    }

    @Override
    public void setAction() {
        if (move(direction, false)) return;

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

    public abstract void interact(Entity entity);


    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialog = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "downs":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
        gp.gsm.setState(new DialogState(gp));
    }

}
