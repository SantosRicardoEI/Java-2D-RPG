package entity.utils;


import entity.Entity;
import item.*;
import core.GamePanel;
import entity.monster.*;
import entity.npc.*;
import entity.object.*;

public class EntityFactory {

    public static Entity create(GamePanel gp, String id, EntityType type) {
        Entity entity = switch (type) {
            case PLAYER -> null;
            case NPC -> createNPC(gp, id);
            case MONSTER -> createMonster(gp, id);
            case OBJECT -> createObject(gp, id);
        };

        if (entity != null) {
            entity.entityType = type; // ⚠️ Atribui sempre o tipo
        } else {
            System.out.println("❌ Falha ao criar entidade: " + id + " do tipo " + type);
        }

        return entity;
    }

    private static Npc createNPC(GamePanel gp, String id) {
        return switch (id) {
            case "invincibleOldMan" -> {
                NPC_OldMan npc = new NPC_OldMan(gp);
                npc.currentMaxHealth = Integer.MAX_VALUE;
                npc.currentHealth = npc.currentMaxHealth;
                npc.baseSpeed = 0;
                npc.currentSpeed = npc.baseSpeed;
                yield npc;
            }
            case "oldMan" -> new NPC_OldMan(gp);
            case "blueboy" -> new NPC_Thing(gp);
            default -> {
                System.out.println("❌ NPC desconhecido: " + id);
                yield null;
            }
        };
    }

    public static Monster createMonster(GamePanel gp, String id) {
        return switch (id) {
            case "invincibleSlime" -> {
                MON_GreenSlime slime = new MON_GreenSlime(gp);
                slime.currentMaxHealth = Integer.MAX_VALUE;
                slime.currentHealth = slime.currentMaxHealth;
                slime.baseSpeed = 0;
                slime.currentSpeed = slime.baseSpeed;
                yield slime;
            }
            case "wheelChairSlime" -> {
                MON_GreenSlime slime = new MON_GreenSlime(gp);
                slime.name = "The Everliving Slime";
                slime.currentMaxHealth = 3000;
                slime.currentHealth = slime.currentMaxHealth;
                slime.baseSpeed = 1;
                slime.currentSpeed = slime.baseSpeed;
                yield slime;
            }
            case "greenSlime" -> {
                MON_GreenSlime slime = new MON_GreenSlime(gp);
                yield slime;
            }
            case "slowSlime" -> {
                MON_GreenSlime slime = new MON_GreenSlime(gp);
                slime.baseSpeed = 1;
                slime.currentSpeed = slime.baseSpeed;
                yield slime;
            }
            case "redSlime" -> new MON_RedSlime(gp);
            case "bat" -> new MON_Bat(gp);
            case "scarab" -> new MON_Scarab(gp);
            case "ghost" -> new MON_Ghost(gp);
            case "dummy" -> new MON_Dummy(gp);
            default -> {
                System.out.println("❌ Monstro desconhecido: " + id);
                yield null;
            }
        };
    }

    private static Entity createObject(GamePanel gp, String id) {
        return switch (id) {
            case "healthPotion" -> new ITM_HealthPotion(gp, 5);
            case "staminaPotion" -> new ITM_StaminaPotion(gp, 5);
            case "key" -> new OBJ_Key(gp);
            case "bonfire" -> new OBJ_Bonfire(gp);
            case "lostSouls" -> new OBJ_LostSouls(gp,500);
            default -> {
                System.out.println("❌ Objeto desconhecido: " + id);
                yield null;
            }
        };
    }
}