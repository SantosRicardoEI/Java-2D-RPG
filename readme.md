# ⚔️ Java 2D RPG — Dark Souls D-Make

A 2D action RPG project written in pure Java, heavily inspired by *Dark Souls*. Built with Java Swing and `Graphics2D`, it features real-time combat, dynamic lighting, an inventory system, RPG attributes, and a functional day/night cycle.

---

## 🔫 Weapons

The game offers 5 different melee weapons, each with distinct:

- **Attack damage**
- **Attack speed**
- **Range**
- **Height** (affects player movement speed)
- **Animations**

Each weapon scales its damage with a specific player attribute (e.g., Strength or Dexterity).

---

## 🎒 Items

There are 3 usable items:

- **Health Potion** – restores health
- **Stamina Potion** – restores stamina
- **Torch** – emits light when held, useful in dark areas

---

## 👾 Enemies

- Wander randomly across the map
- Deal damage to the player on contact
- AI systems for **chasing** and **fleeing** are implemented (but currently inactive)
- Killing enemies grants **souls** (points used for leveling up)

---

## 🧑 NPCs

- Dialog system with multi-line conversations
- NPCs face the player while interacting

---

## 🕒 Time System

- Fully working **hour:minute:second** and **day-of-week** cycle
- Global lighting changes dynamically based on time:
    - Smooth transitions intended, but currently suffer from a known bug causing abrupt changes
- **Dynamic lighting system**:
    - Any entity (objects, enemies, player, NPCs) can emit light

---

## 🔥 Bonfires & Progression

- Resting at a bonfire:
    - Resets enemies and level state
    - Sets the bonfire as the new **spawn point**
    - Allows spending souls to upgrade player attributes

- On death:
    - All unspent souls are dropped at the death location
    - They can be retrieved if the player reaches the spot again

---

## 🧬 Attributes & Passive Effects

Players can level up six core attributes:

- **Vigor** – increases maximum health
- **Endurance** – increases maximum stamina
- **Strength** – increases the chance to deal +50% bonus damage on hit
- **Dexterity** – increases the chance of attacking without stamina cost
- **Intelligence** – increases the multiplier of souls gained from enemies
- **Faith** – increases the chance to completely negate incoming damage

All passive bonuses scale using the square root of the attribute value, providing noticeable benefits at higher levels.

---

## 🎮 Controls

| Key            | Action                                 |
|----------------|----------------------------------------|
| `W / A / S / D`| Move player                            |
| `Q`            | Switch item                            |
| `E`            | Use selected item                      |
| `Enter`        | Attack / Interact / Talk               |
| `Shift`        | Dash (currently disabled due to bugs)  |
| `1 ~ 5`        | Change weapon                          |
| `O`            | Toggle debug mode                      |

---

## ⚠️ Known Issues

- Dynamic lighting may cause performance drops (extreme cases)
- Abrupt transition between day and night phases
- Dash ability is disabled due to collision-related bugs

---

## 📜 License

This is a personal and educational project.  
No commercial use intended.  
Inspired by *Dark Souls*, property of FromSoftware.