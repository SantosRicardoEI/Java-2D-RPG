package entity.player;

import core.states.DialogState;
import core.states.LevelUpState;
import entity.Entity;
import entity.npc.Npc;
import entity.object.OBJ_Bonfire;
import entity.object.Object;
import entity.utils.EntityType;
import entity.utils.EntityUtils;
import item.ITM_HealthPotion;
import item.ITM_StaminaPotion;
import item.ITM_Torch;
import item.Item;
import core.GamePanel;
import core.input.KeyHandler;
import entity.object.OBJ_LostSouls;
import weapon.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    KeyHandler keyH;

    public boolean talkingToNpc = false;

    // === PLAYER CLASS ===
    public PlayerClass playerClass = entity.player.PlayerClass.BANDIT;

    // === LEVEL UP COST
    public int levelUpCost;

    // === DEATH SCREEN ===
    public boolean youDiedScreen = false;

    // === LAST CHECKPOINT ===
    public int lastCheckpointX, lastCheckpointY;

    public Player(GamePanel gp, KeyHandler keyH, PlayerClass playerClass) {

        super(gp);
        this.keyH = keyH;
        this.entityType = EntityType.PLAYER;

        this.name = "Player";
        this.skin = "knight";

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        solidArea.x = 10;
        solidArea.y = 18;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;

        isCollidable = true;

        attackArea.width = 36;
        attackArea.height = 36;

        spriteSpeed = 12;
        direction = "down";
        lightRadius = 5;

        setup(playerClass);
    }

    @Override
    public void damageReaction() {
        gp.overlay.triggerDamageOverlay();
    }

    @Override
    public void deathReaction() {

    }

    // Set player Equipment and spawn
    public void setup(PlayerClass playerClass) {

        System.out.println("\n[PLAYER] Setting up player:");

        // Set player images
        getImage();
        getPlayerAttackImage();

        // Sets default player values
        setDefaultValues(playerClass);

        // Set Player Equipment
        loadItems();
        item = itemList[0];
        loadWeapons();
        weapon = weaponList[0];

        // Update stats the depends on wepons / actual values
        updateDerivedStats();

        // Set Player Spawn and move to spawn
        setSpawn(gp.currentMap.playerSpawnX, gp.currentMap.playerSpawnY);

        reset();

        System.out.println("[PLAYER] Player Setup complete!\n");
    }

    public void reset() {
        System.out.println("[PLAYER] Reseting player...");
        currentHealth = currentMaxHealth;
        currentStamina = currentMaxStamina;
        currentSpeed = weapon.playerSpeed;
        System.out.println("[PLAYER] Moving player to last spawn...");
        goTo(lastCheckpointX, lastCheckpointY);
        refillItems();
        if (!gp.entityList.contains(gp.player)) {
            gp.entityList.add(gp.player);
        }
    }

    public void getPlayerAttackImage() {

        System.out.println("[PLAYER] Getting playerAttack images...");

        // Temporary, delete when add mov to the skins
        String skin = "boy";

        attackUp1 = EntityUtils.setup(gp, "/player/" + skin + "_attack_up_1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = EntityUtils.setup(gp, "/player/" + skin + "_attack_up_2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = EntityUtils.setup(gp, "/player/" + skin + "_attack_down_1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = EntityUtils.setup(gp, "/player/" + skin + "_attack_down_2", gp.tileSize, gp.tileSize * 2);
        attackLeft1 = EntityUtils.setup(gp, "/player/" + skin + "_attack_left_1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = EntityUtils.setup(gp, "/player/" + skin + "_attack_left_2", gp.tileSize * 2, gp.tileSize);
        attackRight1 = EntityUtils.setup(gp, "/player/" + skin + "_attack_right_1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = EntityUtils.setup(gp, "/player/" + skin + "_attack_right_2", gp.tileSize * 2, gp.tileSize);
    }


    public void setDefaultValues(PlayerClass playerClass) {
        System.out.println("[PLAYER] Setting player default values...");

        switch (playerClass) {
            case PlayerClass.BANDIT:
                level = 10;
                vigor = 10;
                endurance = 10;
                strength = 10;
                dexterity = 16;
                intelligence = 10;
                faith = 10;
                break;
            case PlayerClass.KNIGHT:
                level = 10;
                vigor = 13;
                endurance = 10;
                strength = 10;
                dexterity = 12;
                intelligence = 10;
                faith = 10;
                break;
            case PlayerClass.BLADEMAN:
                level = 10;
                vigor = 12;
                endurance = 10;
                strength = 10;
                dexterity = 14;
                intelligence = 10;
                faith = 10;
                break;
            case PlayerClass.WARRIOR:
                level = 10;
                vigor = 13;
                endurance = 13;
                strength = 10;
                dexterity = 10;
                intelligence = 10;
                faith = 10;
                break;
            case PlayerClass.SWORDMAN:
                level = 10;
                vigor = 15;
                endurance = 12;
                strength = 10;
                dexterity = 9;
                intelligence = 10;
                faith = 10;
                break;
        }

        System.out.println("[PLAYER] Starting as " + playerClass);

        souls = 0;
        levelUpCost = 100;

        // Settings
        damageIFrames = 30;
        rollIFrames = 20;
        currentHealthRegen = 150;
        currentStaminaRegen = 4;
        maxItemQuantity = 5;
        baseSpeed = 4;
        dashDuration = 10;
        dashSpeed = 16;
    }

    public void loadWeapons() {
        System.out.println("[PLAYER] Loading player weapons...");
        addWeapon(new WPN_LongSword(gp));
        addWeapon(new WPN_Axe(gp));
        addWeapon(new WPN_Dagger(gp));
        addWeapon(new WPN_Katana(gp));
        addWeapon(new WPN_GreatSword(gp));
    }

    public void loadItems() {
        System.out.println("[PLAYER] Loading player items...");
        addItem(new ITM_HealthPotion(gp, 5));
        addItem(new ITM_StaminaPotion(gp, 5));
        addItem(new ITM_Torch(gp, 1));
    }

    public void addWeapon(Weapon weapon) {
        System.out.println("[PLAYER] Adding weapon: " + weapon.name);
        for (int i = 0; i < weaponList.length; i++) {
            if (weaponList[i] == null) {
                weaponList[i] = weapon;
                return;
            }
        }
    }

    public void addItem(Item item) {
        System.out.println("[PLAYER] Adding item: " + item.name);
        for (int i = 0; i < itemList.length; i++) {
            if (itemList[i] == null) {
                itemList[i] = item;
                return;
            }
        }
    }

    public void setSpawn(int x, int y) {
        System.out.println("[PLAYER] Setting player spawn...");
        worldY = y;
        worldX = x;
        realX = x;
        realY = y;
        lastCheckpointX = x;
        lastCheckpointY = y;
    }

    public void goTo(int x, int y) {
        worldY = y;
        worldX = x;
        realX = x;
        realY = y;
    }


    public void refillItems() {
        System.out.println("[PLAYER] Refilling player items...");
        for (Item item : itemList) {
            if (item != null) {
                item.quantity = item.maxItemQuantity;
            }
        }
    }

    public void updateDerivedStats() {
        System.out.println("[PLAYER] Updating player stats");
        currentMaxHealth = (int) (Math.sqrt(vigor) * 25);
        currentMaxStamina = (int) (Math.sqrt(endurance) * 25);
        currentSpeed = weapon.playerSpeed;
        calculatePassiveChances();
    }


    public void levelUp(String attribute) {
        if (souls < levelUpCost) return;

        level++;
        souls -= levelUpCost;
        levelUpCost = (int) (50 * Math.pow(level, 1.5));

        switch (attribute.toLowerCase()) {
            case "vigor" -> vigor++;
            case "endurance" -> endurance++;
            case "strength" -> strength++;
            case "dexterity" -> dexterity++;
            case "intelligence" -> intelligence++;
            case "faith" -> faith++;
            default -> {
                System.err.println("[PLAYER] Atributo inválido: " + attribute);
                return;
            }
        }
        updateDerivedStats();
    }

    // Atualiza os efeitos passivos com base nos atributos do jogador
    public void calculatePassiveChances() {

        // Strength: aumenta a chance (%) de causar +50% de dano ao atacar
        // Ex: 200 STR - 70% de chance (√200 * 5)
        strengthBonusChance = (int) (Math.sqrt(strength) * 5);

        // Dexterity: chance (%) de não consumir stamina ao atacar
        // Ex: 200 DEX - 70% de chance
        dexteritySaveStaminaChance = (int) (Math.sqrt(dexterity) * 5);

        // Intelligence: multiplicador de souls ganhos de inimigos
        // Ex: 200 INT - 2.13x souls (1.0 + √200 * 0.08)
        extraSoulsMultiplier = 1.0 + (Math.sqrt(intelligence) * 0.08);

        // Faith: chance (%) de ignorar completamente o dano de um ataque inimigo
        // Ex: 200 FAITH - 42% de chance
        faithDodgeChance = Math.sqrt(faith) * 3;
    }

    public void update() {


        if (emitLight) {
            emitLight(lightRadius);
        }

        // if player is dead do Death Sequence
        if (checkPlayerDeath()) return;

        checkForInteractions();
        if (talkingToNpc) {
            return;
        }

        // Update Timers & Cooldowns
        itemUsageFrameCounter++;

        // Atualiza iFrames
        updateInvincibility();

        // Stats recover
        hpRecover();
        staminaRecover();

        // Verify start attack triggers, and turns attacking = true if verifies
        tryStartAttack();

        if (tryAttack(gp, gp.entityList, EntityType.MONSTER)) return;

        //tryDash();
        if (!isDashing) {
            tryMove();
        }
        tryUseItem();
        tryChangeItem();
        tryChangeWeapon();


    }

    // TODO
    // O DASH AINDA ESTÁ MEGA BUGADO POR CAUSA DAS HITBOXES
    /*
    public void tryDash() {
        if (dashCooldownFrameCounter > 0) {
            dashCooldownFrameCounter--;
        }

        if (!isDashing && keyH.shiftPressed && currentStamina >= 10 && dashCooldownFrameCounter == 0) {
            System.out.println("[PLAYER] Dash started.");
            isDashing = true;
            dashFrameCounter = 0;
            isInvincible = true;
            keyH.shiftPressed = false;
            currentStamina -= 10;
            dashCooldownFrameCounter = dashCooldown;
        }

        if (isDashing) {
            dashFrameCounter++;

            double totalDashDistance = dashSpeed;
            int steps = (int) Math.ceil(totalDashDistance / 2.0); // 2 pixels por passo
            double stepDistance = totalDashDistance / steps;

            for (int i = 0; i < steps; i++) {
                collisionOn = false;
                Entity collidedEntity = gp.cChecker.getEntityCollision(this, gp.entityList, true);
                gp.cChecker.checkTile(this);

                if (collisionOn) {
                    if (collidedEntity != null && collidedEntity.isCollidable) {
                        // Verifica se o destino final do dash está fora da hitbox da entidade
                        double futureX = realX;
                        double futureY = realY;

                        switch (direction) {
                            case "up" -> futureY -= (stepDistance * (steps - i));
                            case "down" -> futureY += (stepDistance * (steps - i));
                            case "left" -> futureX -= (stepDistance * (steps - i));
                            case "right" -> futureX += (stepDistance * (steps - i));
                        }

                        Rectangle futureSolidArea = new Rectangle(
                                (int) (futureX + solidArea.x),
                                (int) (futureY + solidArea.y),
                                solidArea.width,
                                solidArea.height
                        );

                        Rectangle entitySolidArea = collidedEntity.getSolidAreaWorld();

                        if (futureSolidArea.intersects(entitySolidArea)) {
                            // Se o final ainda estiver DENTRO da entidade → Cancela o dash
                            System.out.println("[PLAYER] Dash stopped: final position inside entity.");
                            isDashing = false;
                            isInvincible = false;
                            dashFrameCounter = 0;
                            return;
                        } else {
                            // Se o final está fora → Permite atravessar!
                            System.out.println("[PLAYER] Passed through entity during dash!");
                            collisionOn = false;
                        }
                    } else {
                        // Se colidiu com algo que não é entidade (ex: parede), cancela
                        System.out.println("[PLAYER] Dash canceled due to wall collision.");
                        isDashing = false;
                        isInvincible = false;
                        dashFrameCounter = 0;
                        return;
                    }
                }

                // Continua o dash
                switch (direction) {
                    case "up" -> realY -= stepDistance;
                    case "down" -> realY += stepDistance;
                    case "left" -> realX -= stepDistance;
                    case "right" -> realX += stepDistance;
                }

                worldX = (int) realX;
                worldY = (int) realY;
            }

            if (dashFrameCounter > dashDuration) {
                System.out.println("[PLAYER] Dash ended.");
                isDashing = false;
                isInvincible = false;
                dashFrameCounter = 0;
            }
        }
    }

     */

    public void checkForInteractions() {

        Entity collidedEntity = gp.cChecker.getEntityCollision(this, gp.entityList, true);

        if (this instanceof entity.player.Player) {
            if (collidedEntity != null) {
                if (collidedEntity instanceof entity.object.Object obj) {
                    obj.interact(this);
                } else if (collidedEntity instanceof entity.npc.Npc npc) {
                    if (gp.keyH.enterPressed) {
                        gp.keyH.enterPressed = false;
                        npc.interact(this);
                        talkingToNpc = true;
                    }
                }
            }
            // 3. Colisão com eventos do mapa
            gp.eHandler.checkEvent();
        }
    }


    // Not used (controls entity behavior)
    @Override
    public void setAction() {

    }


    @Override
    public void tryStartAttack() {
        if (!isAttacking && keyH.enterPressed && currentStamina > 0) {
            System.out.println("[PLAYER] Player started an attack.");
            keyH.enterPressed = false;
            animationFrameCounter = 0;
            isAttacking = true;

            // DEX passive (chance de não consumir stamina)
            if (Math.random() * 100 >= dexteritySaveStaminaChance) {
                currentStamina -= weapon.staminaCost;
            } else {
                System.out.println("[PLAYER] Attack had no stamina cost.");
                gp.ui.addLog("[PLAYER] Attack without stamina cost (Dexterity)!", Color.white);
            }
        }
    }

    private void triggerDeathSequence() {
        System.out.println("[PLAYER] Death sequence initialized.");
        isInvincible = true;
        currentSpeed = 0;
        youDiedScreen();
    }

    private void finishDeathSequence() {
        System.out.println("[PLAYER] Death sequence finished.");
        youDiedScreen = false;
        deathTimeCounter = 0;
        deathx = worldX / gp.tileSize;
        deathY = worldY / gp.tileSize;
        gp.resetLevel();
        gp.currentMap.placeEntity(deathx, deathY, new OBJ_LostSouls(gp, souls));
        souls = 0;
        gp.ui.fadingToBlack = false;
    }

    public void youDiedScreen() {
        gp.ui.fadingToBlack = true;
        gp.ui.fadeAlpha = 0;
        gp.soundM.playSE("death");
    }

    public boolean checkPlayerDeath() {

        if (currentHealth <= 0 && !youDiedScreen) {
            System.out.println("[PLAYER] Player is dead");
            youDiedScreen = true;
        }
        if (youDiedScreen) {
            if (deathTimeCounter == 0) {
                triggerDeathSequence();
            }
            deathTimeCounter++;
            if (deathTimeCounter == 500) {
                finishDeathSequence();
            }
            return true;
        }
        return false;
    }


    public void draw(Graphics2D g2) {

        int tempScreenX = (int) Math.round(realX - gp.player.realX + gp.player.screenX);
        int tempScreenY = (int) Math.round(realY - gp.player.realY + gp.player.screenY);

        BufferedImage image = null;


        switch (direction) {
            case "up":
                if (isAttacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                } else {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                break;
            case "down":
                if (isAttacking) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                } else {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }
                break;
            case "left":
                if (isAttacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                } else {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                break;
            case "right":
                if (isAttacking) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                } else {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
                break;
        }

        if (isInvincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        if (gp.debug) {
            Rectangle2D.Double atkBox = getAttackBox();
            int screenAttackX = (int) Math.round(atkBox.x - realX + tempScreenX);
            int screenAttackY = (int) Math.round(atkBox.y - realY + tempScreenY);

            g2.setColor(Color.green);
            g2.setStroke(new BasicStroke(1));
            g2.drawRect(screenAttackX, screenAttackY, (int) Math.round(atkBox.width), (int) Math.round(atkBox.height));
        }

        if (keyH.oPressed) {
            gp.debug = !gp.debug; // inverte o estado
            keyH.oPressed = false;
        }
    }

    public boolean tryChangeWeapon() {
        if (!isAttacking && !isDashing) {

            int index = -1;

            if (gp.keyH.pressed1) index = 0;
            if (gp.keyH.pressed2) index = 1;
            if (gp.keyH.pressed3) index = 2;
            if (gp.keyH.pressed4) index = 3;
            if (gp.keyH.pressed5) index = 4;

            if (index != -1 && weaponList[index] != null) {
                if (currentStamina > 0) {
                    gp.soundM.playSE("changeweapon");
                    weapon = weaponList[index];
                    gp.keyH.pressed1 = gp.keyH.pressed2 = gp.keyH.pressed3 = gp.keyH.pressed4 = gp.keyH.pressed5 = false;
                    currentStamina -= currentMaxStamina / 5;

                    System.out.println("[PLAYER] Changed weapon to " + weapon.name + ".");
                    updateDerivedStats();
                    return true;

                } else {
                    gp.keyH.pressed1 = gp.keyH.pressed2 = gp.keyH.pressed3 = gp.keyH.pressed4 = gp.keyH.pressed5 = false;
                }
            }
        }

        return false;
    }


    private boolean tryChangeItem() {


        if (gp.keyH.qPressed) {
            gp.keyH.qPressed = false;

            if (item.alwaysOn) {
                item.disable(gp);
            }

            for (int i = 0; i < itemList.length; i++) {
                if (itemList[i] != null) {
                    if (itemList[i] == item) {
                        if (i + 1 < itemList.length && itemList[i + 1] != null) {
                            item = itemList[i + 1];
                        } else {
                            item = itemList[0];
                        }
                        System.out.println("[PLAYER] Changed item to " + item.name + ".");
                        if (item.alwaysOn) {
                            item.enable(gp);
                        }
                        return true;
                    }
                }
            }
            item = itemList[0];
        }
        return false;
    }

    private boolean tryMove() {

        if (keyH.wPressed || keyH.sPressed || keyH.aPressed || keyH.dPressed) {

            if (keyH.wPressed) {
                direction = "up";
            } else if (keyH.sPressed) {
                direction = "down";
            } else if (keyH.aPressed) {
                direction = "left";
            } else if (keyH.dPressed) {
                direction = "right";
            }

            // Se nao está em colisao, move-se
            if (move(direction, true)) return true;
        }
        return false;
    }


    public boolean tryUseItem() {

        if (keyH.ePressed && itemUsageFrameCounter >= itemUsageTime && !isAttacking && !isDashing) {
            if (item.quantity > 0) {
                item.use(gp);
                System.out.println("[PLAYER] Used item: " + item.name);
                itemUsageFrameCounter = 0;
            } else {
                System.out.println("[PLAYER] No " + item.name + " to use!");
            }
            keyH.ePressed = false;
            return true;
        }
        return false;
    }
}
