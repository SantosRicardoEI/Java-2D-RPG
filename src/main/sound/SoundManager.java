package main.sound;

public class SoundManager {

    private Sound music;
    private Sound se;

    public SoundManager() {
        music = new Sound();
        se = new Sound();
    }

    public void playMusic(String name) {
        System.out.println("[MUSIC] Setting and playing: " + name);
        music.setFile(name);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        System.out.println("[MUSIC] Stopped");
        music.stop();
    }



    public void playSE(String name) {
        System.out.println("[SE] Playing: " + name);
        se.setFile(name);
        se.play();
    }

    public void playSE(int i) {
        System.out.println("[SE] Playing index: " + i);
        se.setFile(i);
        se.play();
    }

    public void stopSE() {
        System.out.println("[SE] Stopped");
        se.stop();
    }
}