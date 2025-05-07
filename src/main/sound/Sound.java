package main.sound;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Sound {

    private Clip clip;
    private final URL[] soundURL = new URL[30];
    private final Map<String, Integer> soundMap = new HashMap<>();

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/firelink.wav");
        soundURL[1] = getClass().getResource("/sound/coin.wav");
        soundURL[2] = getClass().getResource("/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/sound/katana.wav");
        soundURL[5] = getClass().getResource("/sound/hitmonster.wav");
        soundURL[6] = getClass().getResource("/sound/receivedamage.wav");
        soundURL[7] = getClass().getResource("/sound/estus.wav");
        soundURL[8] = getClass().getResource("/sound/dash.wav");
        soundURL[9] = getClass().getResource("/sound/katana-001.wav");
        soundURL[10] = getClass().getResource("/sound/katana-002.wav");
        soundURL[11] = getClass().getResource("/sound/katana-003.wav");
        soundURL[12] = getClass().getResource("/sound/katana-004.wav");
        soundURL[13] = getClass().getResource("/sound/katana-005.wav");
        soundURL[14] = getClass().getResource("/sound/katana-006.wav");
        soundURL[15] = getClass().getResource("/sound/katana-007.wav");
        soundURL[16] = getClass().getResource("/sound/menutheme.wav");
        soundURL[17] = getClass().getResource("/sound/startbutton.wav");
        soundURL[18] = getClass().getResource("/sound/death.wav");
        soundURL[19] = getClass().getResource("/sound/darkambient.wav");
        soundURL[20] = getClass().getResource("/sound/changeweapon.wav");
        soundURL[21] = getClass().getResource("/sound/getsoulsback.wav");
        soundURL[22] = getClass().getResource("/sound/ghost.wav");


        // 🔁 Mapa de nomes para índices
        soundMap.put("firelink", 0);
        soundMap.put("coin", 1);
        soundMap.put("powerup", 2);
        soundMap.put("unlock", 3);
        soundMap.put("swing", 4);
        soundMap.put("hit", 5);
        soundMap.put("damage", 6);
        soundMap.put("estus", 7);
        soundMap.put("dash", 8);
        soundMap.put("menutheme", 16);
        soundMap.put("startbutton", 17);
        soundMap.put("death", 18);
        soundMap.put("dark", 19);
        soundMap.put("changeweapon", 20);
        soundMap.put("getmysouls", 21);
        soundMap.put("ghost", 22);
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFile(String name) {
        Integer index = soundMap.get(name);
        if (index != null) {
            setFile(index);
        } else {
            System.err.println("Sound file not found: " + name);
        }
    }

    public void play() {
        if (clip != null) {
            clip.start();
        }

    }

    public void loop() {
        if (clip != null) clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
}