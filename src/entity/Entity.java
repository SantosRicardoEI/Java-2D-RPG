/*
Otimizações para a Classe Entity
	1.	Movimento e Direção
	•	Problema: A cada movimento, o código recalcula várias vezes a direção e as coordenadas da entidade.
	•	Solução: Armazene os valores calculados de dx e dy para movimento e direção de forma mais eficiente, atualizando-os apenas quando necessário.
	•	Objetivo: Evitar recalcular os valores que não mudam a cada atualização de frame.
	2.	Otimização da Função isNearEntityType
	•	Problema: A função percorre todos os alvos em gp.entityList em busca de entidades de um tipo específico, o que pode ser lento quando o número de alvos aumenta.
	•	Solução: Armazene as entidades em listas separadas por tipo (por exemplo, uma lista para jogadores, outra para monstros) para reduzir a quantidade de entidades que precisam ser verificadas.
	•	Objetivo: Reduzir o número de entidades verificadas ao procurar um alvo.
	3.	Otimização da Lógica de Perseguição e Fuga
	•	Problema: A lógica de perseguição e fuga pode resultar em movimento ineficiente se a entidade ficar “presa” entre obstáculos.
	•	Solução: Utilize uma abordagem A ou de busca de caminho* mais eficiente para encontrar o melhor caminho até o alvo ou para se afastar dele.
	•	Objetivo: Tornar o movimento mais inteligente e eficiente, evitando situações de “empate” entre obstáculos.
	4.	Verificação de Bloqueio de Movimento
	•	Problema: O código atual verifica o bloqueio de movimento constantemente durante a perseguição e fuga, o que pode ser redundante em alguns casos.
	•	Solução: Use um algoritmo de caminho para calcular os bloqueios e restrições de forma mais eficiente. Evite recalcular as verificações a cada atualização, se não houver mudança de direção.
	•	Objetivo: Acelerar a detecção de bloqueios e reduzir o trabalho desnecessário.
	5.	Otimização da Verificação de Colisão de Ataques
	•	Problema: A cada ataque, o código verifica todos os alvos potenciais e suas colisões.
	•	Solução: Utilize uma lista filtrada de alvos próximos ao atacante, baseada na área de alcance do ataque. Evite verificar colisões com alvos distantes ou não relevantes.
	•	Objetivo: Reduzir o número de alvos verificados durante um ataque.
	6.	Cache de Posições e Direções
	•	Problema: O código pode recalcular a posição e direção da entidade muitas vezes.
	•	Solução: Armazene as posições e direções em variáveis temporárias para evitar recalcular essas informações em cada frame.
	•	Objetivo: Melhorar a performance ao minimizar cálculos repetidos.
	7.	Reatividade no Estado de “Invencibilidade”
	•	Problema: A verificação de invencibilidade pode ser ineficiente, especialmente quando há muitas entidades no jogo.
	•	Solução: Use uma flag de invencibilidade que só seja ativada/desativada nas situações de colisão ou ataque, evitando verificações redundantes.
	•	Objetivo: Reduzir o número de verificações de estado para entidades invencíveis.
	8.	Otimização no Controle de Animações
	•	Problema: O controle de animações pode ser repetitivo e não otimizado.
	•	Solução: Utilize um controle centralizado de animações para cada direção, cacheando as imagens e evitando verificações desnecessárias.
	•	Objetivo: Evitar cálculos repetidos ao alternar animações e melhorar o desempenho gráfico.
	9.	Uso de switch vs. if-else
	•	Problema: Em algumas partes do código, você utiliza if-else para verificar direções.
	•	Solução: Substitua a estrutura if-else por um switch para melhorar a legibilidade e talvez a performance em algumas situações.
	•	Objetivo: Melhorar a legibilidade e eficiência na verificação de direções.

	Otimizações para o Jogo em Geral
	1.	Evitar Calcular Desnecessariamente
	•	Problema: Em vários métodos, os cálculos de movimento, colisão e interação são feitos repetidamente, mesmo quando a situação não mudou.
	•	Solução: Introduza flags de “mudança de estado” para evitar recalcular posições e interações se não houver mudança relevante (exemplo: movimento da entidade ou mudança de direção).
	•	Objetivo: Reduzir cálculos desnecessários.
	2.	Implementar Lógicas de Atualização em Grupos
	•	Problema: O código atual trata as atualizações de entidades de forma individual.
	•	Solução: Agrupe a atualização de entidades para processar as lógicas de colisão, movimentação e ataque em blocos, usando uma fila de atualizações.
	•	Objetivo: Melhorar a eficiência no processamento das entidades, especialmente quando há muitas delas.
 */
package entity;

import core.GamePanel;
import entity.effect.Effect;
import entity.player.Player;
import entity.utils.EntityType;
import entity.utils.EntityUtils;
import item.Item;
import main.Light;
import weapon.Weapon;

import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    // === SYSTEM ===
    public GamePanel gp;

    // === DEBUG ===
    public Rectangle attackBoxForDebug = null;

    // ID
    public String name = "Default Entity";
    public EntityType entityType = EntityType.MONSTER;
    public String skin = "default_skin";

    // === IMAGES ===
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
            attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image, image2, image3, image4, image5, image6;

    // === TIMER CONTROLS ===
    public Map<String, Integer> timers = new HashMap<>();
    public int animationFrameCounter = 0;
    public int invincibilityFrameCounter;
    public int dyingFrameCounter = 0;
    public int dashCooldownFrameCounter = 0;
    public int healthRegenFrameCounter = 0;
    public int staminaRegenFrameCounter = 0;
    public int itemUsageFrameCounter = 0;
    public int dashFrameCounter = 0;
    public int effectFrameCounter = 0;
    public int staggerTimer = 0;
    public int deathTimeCounter = 0;
    public int hpDisplayFrameCounter = 0;
    public int nameDisplayFrameCounter = 0;

    // === AINMATION HANDLE ===
    public int spriteNum = 1;
    public boolean hasIdleAnimation = false;
    public boolean isSingleAnimation = false;
    public int spriteSpeed = 12;

    // === WORLD POSITION ===
    public double realX, realY;
    public int worldX, worldY;

    // === SCREEN POSITION ===
    public int screenX;
    public int screenY;

    // === HITBOX AND COLLISION ===
    public Rectangle2D.Double solidArea = new Rectangle2D.Double(0, 0, 48, 48);
    public Rectangle2D.Double attackArea = new Rectangle2D.Double(0, 0, 36, 36);
    public double solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    // === DEATH CONTROL ===
    public int deathx, deathY;
    public boolean deathReactionDone = false;
    public boolean isDying = false;

    // === SETTINGS ===
    public boolean isCollidable = false;
    public boolean isPassive = false;
    public boolean isRespawnable = true;
    public boolean hpDisplay = false;
    public int hpDisplayTime = 300;
    public boolean nameDisplay = false;
    public int nameDisplayTime = 300;
    public int damageIFrames = 10;
    public int rollIFrames;
    public int dashIFrames;
    public int getDamageIFrames;
    public int dashDuration; // frames
    public int dashSpeed;
    public int dashCooldown = 30; // frames
    public int itemUsageTime = 30;
    public int maxItemQuantity;
    public int dyingBlinkInterval = 5;
    public int dyingBlinkDuration = 60;
    public int entityStatsRangeVisibility = 4;

    // === MOVEMENT ===
    public int actionLockCounter = 0;
    public String direction = "idle";
    public String dashDirection;

    // === AI ===
    public int chanceToMove = 100;

    // === CORE STATS ===
    public int baseMaxHealth;
    public int currentMaxHealth;
    public int currentHealth;

    public int baseHealthRegen = 0;
    public int currentHealthRegen = 0;

    public int baseMaxStamina;
    public int currentMaxStamina;
    public int currentStamina;

    public int baseStaminaRegen = 0;
    public int currentStaminaRegen = 0;

    public double baseSpeed;
    public double currentSpeed;

    // === COMBAT BASIC STATS ===
    public int baseAttackDamage;
    public int currentAttackDamage;

    public int baseAttackSpeed;
    public int currentAttackSpeed;

    public int baseAttackRange;
    public float currentAttackRange;

    public int baseAttackStaminaCost;
    public int currentAttackStaminaCost;

    // === STATUS CHECK ===
    public boolean isAlive = true;
    public boolean isStaggered = false;
    public boolean isInvincible = false;
    public boolean isDashing = false;
    public boolean isRolling = false;
    public boolean isAttacking = false;
    public boolean isNotMoving = false;

    public int staggerResistance;
    public float backstabMultiplier;
    public float critMultiplier;

    // === DEFENSE  ===
    public float baseDefense;
    public float currentDefense;

    public float basePoise;
    public float currentPoise;

    public float baseIFrames;
    public float currentIFrames;

    // === EFFECTS ===
    public List<Effect> activeEffects = new ArrayList<>();

    // === INVENTORY ===
    public Weapon weaponList[] = new Weapon[50];
    public Weapon weapon;
    public Item itemList[] = new Item[50];
    public Item item;

    // === LIGHT AND SOUND ===
    public Light light;
    public boolean lightInitialized = false;
    public int pickSound;
    public boolean emitLight = false;
    public int lightRadius = 0;

    // === ATRIBUTES ===
    public int level;
    public int strength;
    public int dexterity;
    public int vigor;
    public int endurance;
    public int intelligence;
    public int faith;
    public int souls;

    // === ATRIBUTE'S PASSIVE STATS
    public int strengthBonusChance = 0;
    public int dexteritySaveStaminaChance = 0;
    public double extraSoulsMultiplier = 1.0;
    public double faithDodgeChance = 0.0;

    public Entity(GamePanel gp) {
        this.gp = gp;
        realX = worldX;
        realY = worldY;
    }


    // ========================================== ACTION AND DAMAGE REACTION ===========================================

    public abstract void setAction();

    public abstract void tryStartAttack();

    public abstract void damageReaction();

    public abstract void deathReaction();

    public abstract void update();


    // =========================================== DEFAULT DEATH LOGIC =================================================

    public void die(Entity attacker) {
        disableLight();
        if (isAlive) {
            gp.ui.addLog(name + " has died", Color.RED);
            isDying = true;
            isAlive = false;
            isCollidable = false;
        }

        if (!deathReactionDone) {
            deathReaction();
            if (attacker instanceof Player) {
                giveSouls(attacker);
            }
            deathReactionDone = true;
        }

    }


    public void giveSouls(Entity attacker) {
        if (souls > 0 && attacker.entityType == EntityType.PLAYER) {
            int gainedSouls = (int) (souls * attacker.extraSoulsMultiplier);
            if (extraSoulsMultiplier > 1) {
                gp.ui.addLog("Intelligence bonus Souls: " + gainedSouls, Color.WHITE);
            }
            attacker.souls += gainedSouls;
        }
    }

    // ========================================= ATTACK AND DAMEGE APPLY ===============================================



    public boolean tryAttack(GamePanel gp, List<Entity> possibleTargets, EntityType targetType) {
        if (!isAttacking) return false;

        animationFrameCounter++;

        int totalDuration = weapon.initSpeed + weapon.attackSpeed + weapon.recoveryTime;

        if (animationFrameCounter <= weapon.initSpeed) {
            spriteNum = 1; // Pré-animação
        } else if (animationFrameCounter <= weapon.initSpeed + weapon.attackSpeed) {
            spriteNum = 2; // Frame de impacto

            if (animationFrameCounter == weapon.initSpeed + 1) {
                List<Entity> hitTargets = gp.cChecker.getAllTargetsHitByAttack(this, possibleTargets, targetType);
                applyDamageToTargets(this, hitTargets);

                weapon.playSound();
            }

        } else if (animationFrameCounter < totalDuration) {
            spriteNum = 1; // Recuperação
        } else {
            animationFrameCounter = 0;
            isAttacking = false;
            spriteNum = 1;
            return false;
        }
        return true;
    }

    public void applyDamageToTarget(Entity attacker, Entity target, boolean rawDamage) {
        if (target != null && !target.isInvincible) {
            int damage = rawDamage ? attacker.baseAttackDamage : (
                    attacker.weapon != null ? attacker.weapon.hitDamage : attacker.baseAttackDamage
            );

            target.currentHealth -= damage;
            target.isInvincible = true;
            target.invincibilityFrameCounter = 0;

            gp.soundM.playSE("hit");
            target.damageReaction();

            if (target.currentHealth <= 0) {
                target.die(attacker);
            }
        }
    }

    public void applyDamageToTargets(Entity attacker, List<Entity> targets) {
        for (Entity target : targets) {
            applyDamageToTarget(attacker, target,false);
        }
    }

    // =============================================== STATS RECOVER ===================================================

    public void staminaRecover() {
        if (currentStamina < 0) {
            currentStamina = 0;
        }
        staminaRegenFrameCounter++;
        if (staminaRegenFrameCounter >= currentStaminaRegen) {
            if (currentStamina < currentMaxStamina) {
                currentStamina++;
            }
            staminaRegenFrameCounter = 0;
        }
    }

    public void hpRecover() {
        if (currentHealth < 0) {
            currentHealth = 0;
        }
        healthRegenFrameCounter++;
        if (healthRegenFrameCounter >= currentHealthRegen) {
            if (currentHealth < currentMaxHealth) {
                currentHealth++;
            }
            healthRegenFrameCounter = 0;
        }
    }

    public void updateInvincibility() {
        if (isInvincible) {
            nameDisplay = true;
            hpDisplay = true;
            hpDisplayFrameCounter = 0;
            nameDisplayFrameCounter = 0;
            invincibilityFrameCounter++;

            if (invincibilityFrameCounter > damageIFrames) {
                isInvincible = false;
                invincibilityFrameCounter = 0;
            }
        }
    }

    // =========================================== DEFAULT ENTITY DRAW =================================================

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        int screenX = Math.round(worldX - gp.player.worldX + gp.player.screenX);
        int screenY = Math.round(worldY - gp.player.worldY + gp.player.screenY);

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            if (this instanceof entity.object.OBJ_Bonfire) {
                switch (spriteNum) {
                    case 1 -> image = down1;
                    case 2 -> image = down2;
                    case 3 -> image = up1;
                    case 4 -> image = down2;
                }
            } else {

                switch (direction) {
                    case "idle":
                        if (hasIdleAnimation) {
                            image = (spriteNum == 1) ? down1 : down2;
                        } else {
                            image = down1;
                        }
                        break;
                    case "up":
                        image = (spriteNum == 1) ? up1 : up2;
                        break;
                    case "down":
                        image = (spriteNum == 1) ? down1 : down2;
                        break;
                    case "left":
                        image = (spriteNum == 1) ? left1 : left2;
                        break;
                    case "right":
                        image = (spriteNum == 1) ? right1 : right2;
                        break;
                }
            }

            if (isNearEntityType(gp.tileSize * 3, EntityType.PLAYER) || nameDisplay || hpDisplay) {

                g2.setColor(Color.white);
                g2.setFont(gp.ui.optima.deriveFont(14f));
                FontMetrics fm = g2.getFontMetrics();
                int nameWidth = fm.stringWidth(name);
                int nameX = screenX + (gp.tileSize - nameWidth) / 2;
                int nameY = screenY - (hpDisplay ? 24 : 10);
                g2.drawString(name, nameX, nameY);

                if (hpDisplay) {
                    int barWidth = gp.tileSize;
                    int currentBarWidth = (int) ((double) currentHealth / currentMaxHealth * barWidth);

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(screenX - 1, screenY - 16, barWidth + 2, 12);

                    g2.setColor(new Color(200, 0, 30));
                    g2.fillRect(screenX, screenY - 15, currentBarWidth, 10);

                    hpDisplayFrameCounter++;
                    nameDisplayFrameCounter++;

                    if (hpDisplayFrameCounter > hpDisplayTime) {
                        hpDisplay = false;
                        hpDisplayFrameCounter = 0;
                    }

                    if (nameDisplayFrameCounter > nameDisplayTime) {
                        nameDisplay = false;
                        nameDisplayFrameCounter = 0;
                    }
                }
            }

            if (isInvincible && !isDying) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }

            if (isDying) {
                dyingAnimation(g2, image, screenX, screenY);
            } else {
                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    // ========================================= DRAW DEFAULT DYING ANIMATION ==========================================

    public void dyingAnimation(Graphics2D g2, BufferedImage image, int screenX, int screenY) {
        dyingFrameCounter++;

        if (dyingFrameCounter <= dyingBlinkDuration) {
            if (dyingFrameCounter % (dyingBlinkInterval * 2) < dyingBlinkInterval) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
            } else {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        } else {
            isDying = false;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }


    // ============================================== GET ATTACK BOX ===================================================

    public Rectangle2D.Double getAttackBox() {
        double range = weapon.range;
        double attackW = attackArea.width;
        double attackH = attackArea.height;

        double attackX = 0;
        double attackY = 0;

        switch (direction) {
            case "up":
                attackH *= range;
                attackX = (realX + solidArea.x + solidArea.width / 2 - attackW / 2);
                attackY = (realY + solidArea.y - attackH);
                break;
            case "down":
                attackH *= range;
                attackX = (realX + solidArea.x + solidArea.width / 2 - attackW / 2);
                attackY = (realY + solidArea.y + solidArea.height);
                break;
            case "left":
                attackW = attackArea.height * range;
                attackH = attackArea.width;
                attackX = (realX + solidArea.x - attackW);
                attackY = (realY + solidArea.y + solidArea.height / 2 - attackH / 2);
                break;
            case "right":
                attackW = attackArea.height * range;
                attackH = attackArea.width;
                attackX = (realX + solidArea.x + solidArea.width);
                attackY = (realY + solidArea.y + solidArea.height / 2 - attackH / 2);
                break;
        }

        return new Rectangle2D.Double(attackX, attackY, attackW, attackH);
    }

    // ================================================ SPECIAL EFFECTS ================================================

    // Add entity Light to entityLight List
    public void emitLight(int radius) {

        if (!lightInitialized) {
            System.out.println("[ENTITY] " + name + ": light initialized");
            light = new Light("entityLight", worldX, worldY, radius * gp.tileSize, gp.overlay.staticDist, gp.overlay.staticColors);
            gp.entityLights.add(light);
            lightInitialized = true;
        }

        if (light != null && (light.col != worldX || light.row != worldY)) {
            light.col = worldX;
            light.row = worldY;
        }
    }

    public void disableLight() {
        if (light != null) {
            gp.entityLights.remove(light);
            light = null;
            lightInitialized = false;
            System.out.println(name + ": light disabled");
        }
    }

    // ====================================================== IA =======================================================

    public boolean isNearEntityType(int maxDistancePixels, EntityType targetType) {
        for (Entity target : gp.entityList) {
            if (target != null && target.entityType == targetType) {
                int dx = Math.abs(worldX - target.worldX);
                int dy = Math.abs(worldY - target.worldY);
                if (dx < maxDistancePixels && dy < maxDistancePixels) {
                    return true;
                }
            }
        }
        return false;
    }

    public void attackIfInContact(Entity attacker, EntityType targetType) {
        Entity collidedEntity = gp.cChecker.getEntityCollision(attacker, gp.entityList, true);

        if (collidedEntity != null && collidedEntity.entityType == targetType) {
            // so deixa atacar se a entidade colidida for do tipo certo
            attacker.applyDamageToTarget(attacker, collidedEntity,true);
        }
    }

    public void chaseEntity(int chaseSpeed, EntityType targetType) {
        // procuro pelo primeiro alvo do tipo targetType dentro de uma distância
        for (Entity target : gp.entityList) {
            if (target != null && target.entityType == targetType && isNearEntityType(200, targetType)) {
                int dx = target.worldX - this.worldX;
                int dy = target.worldY - this.worldY;

                // prefered direction
                if (Math.abs(dx) > Math.abs(dy)) {
                    direction = dx > 0 ? "right" : "left";
                } else {
                    direction = dy > 0 ? "down" : "up";
                }

                // se stiver bloqueado escolhe outra direçao
                if (isBlockedInDirection(direction)) {
                    direction = tryAlternateDirection(dx, dy);
                }

                currentSpeed = chaseSpeed;
                break;
            }
        }
    }


    public void fleeFromEntity(int fleeSpeed, EntityType targetType) {
        for (Entity target : gp.entityList) {
            if (target != null && target.entityType == targetType && isNearEntityType(200, targetType)) {
                int dx = this.worldX - target.worldX;
                int dy = this.worldY - target.worldY;

                // para se mover na direçao oposta
                if (Math.abs(dx) > Math.abs(dy)) {
                    direction = dx > 0 ? "left" : "right";
                } else {
                    direction = dy > 0 ? "up" : "down";
                }

                // se bloqueado outra
                if (isBlockedInDirection(direction)) {
                    direction = tryAlternateDirection(-dx, -dy);
                }

                currentSpeed = fleeSpeed;
                break;
            }
        }
    }

    private boolean isBlockedInDirection(String dir) {
        double testX = realX;
        double testY = realY;

        switch (dir) {
            case "up" -> testY -= baseSpeed;
            case "down" -> testY += baseSpeed;
            case "left" -> testX -= baseSpeed;
            case "right" -> testX += baseSpeed;
        }

        Rectangle2D.Double testArea = new Rectangle2D.Double(
                testX + solidArea.x,
                testY + solidArea.y,
                solidArea.width,
                solidArea.height
        );

        return gp.cChecker.isTileBlocked(testArea);
    }

    private String tryAlternateDirection(int dx, int dy) {
        List<String> options = new ArrayList<>();

        if (dx > 0 && !isBlockedInDirection("right")) options.add("right");
        if (dx < 0 && !isBlockedInDirection("left")) options.add("left");
        if (dy > 0 && !isBlockedInDirection("down")) options.add("down");
        if (dy < 0 && !isBlockedInDirection("up")) options.add("up");

        if (options.isEmpty()) return "idle";

        return options.get(new Random().nextInt(options.size()));
    }

    // ============================================== IMAGE SETTING ====================================================

    public void getImage() {

        String type = entityType.name().toLowerCase();
        System.out.println("[ENTITY] Getting " + type + " images...");


        down1 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_down_1");
        down2 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_down_2");
        up1 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_up_1");
        up2 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_up_2");
        left1 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_left_1");
        left2 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_left_2");
        right1 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_right_1");
        right2 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_right_2");

        if (isSingleAnimation) {
            down1 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_down_1");
            down2 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_down_2");
            up1 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_down_1");
            up2 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_down_2");
            left1 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_down_1");
            left2 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_down_2");
            right1 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_down_1");
            right2 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_down_2");
        } else {
            down1 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_down_1");
            down2 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_down_2");
            up1 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_up_1");
            up2 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_up_2");
            left1 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_left_1");
            left2 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_left_2");
            right1 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_right_1");
            right2 = EntityUtils.setup(gp, "/" + type + "/" + skin + "_right_2");
        }


        // Substitui nulls por down1
        if (down2 == null) down2 = down1;
        if (up1 == null) up1 = down1;
        if (up2 == null) up2 = down1;
        if (left1 == null) left1 = down1;
        if (left2 == null) left2 = down1;
        if (right1 == null) right1 = down1;
        if (right2 == null) right2 = down1;
    }

    public Rectangle2D.Double getSolidAreaWorld() {
        return new Rectangle2D.Double(
                realX + solidArea.x,
                realY + solidArea.y,
                solidArea.width,
                solidArea.height
        );
    }


    public boolean move(String direction, boolean isPlayer) {

        collisionOn = false;

        Entity collidedEntity = gp.cChecker.getEntityCollision(this, gp.entityList, true);

        gp.cChecker.checkTile(this);
        gp.cChecker.getEntityCollision(this, gp.entityList, true);


        if (!isPlayer && collisionOn) {
            pickRandomDirection();
        }

        if (!isPlayer) {
            pickRandomDirection();
        }


        handleMoveAnimation();

        if (!collisionOn) {
            double speed = isDashing ? dashSpeed : currentSpeed;
            switch (direction) {
                case "idle" -> {
                }
                case "up" -> {
                    realY -= speed;
                }
                case "down" -> {
                    realY += speed;
                }
                case "left" -> {
                    realX -= speed;
                }
                case "right" -> {
                    realX += speed;
                }
            }
            worldX = (int) realX;
            worldY = (int) realY;

            return true;
        }

        return false;
    }

// ========================================== PICK RANDOM DIRECTION ================================================


    public void pickRandomDirection() {
        actionLockCounter++;
        if (collisionOn && actionLockCounter < 60) {
            actionLockCounter = 60;
        }

        if (!collisionOn) {
            if (actionLockCounter == 120) {
                int j = new Random().nextInt(100);
                isNotMoving = j >= chanceToMove; // Se j for maior, fica parado

                if (!isNotMoving) {
                    int i = new Random().nextInt(100);
                    if (i < 25) {
                        direction = "up";
                    } else if (i < 50) {
                        direction = "down";
                    } else if (i < 75) {
                        direction = "left";
                    } else {
                        direction = "right";
                    }
                } else {
                    direction = "idle";
                }
                actionLockCounter = 0;
            }
        } else {
            // Escolhe uma direção aleatória diferente da atual
            String[] possibleDirections = {"up", "down", "left", "right"};
            List<String> newDirections = new ArrayList<>(Arrays.asList(possibleDirections));
            newDirections.remove(direction);

            direction = newDirections.get(new Random().nextInt(newDirections.size()));

            collisionOn = false;
            actionLockCounter = 0;
        }
    }

    public void handleMoveAnimation() {
        animationFrameCounter++;
        if (animationFrameCounter > spriteSpeed) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            animationFrameCounter = 0;
        }
    }


}

