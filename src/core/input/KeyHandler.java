package core.input;

import core.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    // Teclas Alfabéticas
    public boolean aPressed, bPressed, cPressed, dPressed, ePressed, fPressed, gPressed,
            hPressed, iPressed, jPressed, kPressed, lPressed, mPressed, nPressed,
            oPressed, pPressed, qPressed, rPressed, sPressed, tPressed, uPressed,
            vPressed, wPressed, xPressed, yPressed, zPressed;

    // Teclas Numéricas
    public boolean pressed0, pressed1, pressed2, pressed3, pressed4,
            pressed5, pressed6, pressed7, pressed8, pressed9;

    // Teclas Especiais
    public boolean spacePressed, enterPressed, escPressed, shiftPressed,
            ctrlPressed, altPressed, backspacePressed, tabPressed, capsLockPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Não usado
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_A -> aPressed = true;
            case KeyEvent.VK_B -> bPressed = true;
            case KeyEvent.VK_C -> cPressed = true;
            case KeyEvent.VK_D -> dPressed = true;
            case KeyEvent.VK_E -> ePressed = true;
            case KeyEvent.VK_F -> fPressed = true;
            case KeyEvent.VK_G -> gPressed = true;
            case KeyEvent.VK_H -> hPressed = true;
            case KeyEvent.VK_I -> iPressed = true;
            case KeyEvent.VK_J -> jPressed = true;
            case KeyEvent.VK_K -> kPressed = true;
            case KeyEvent.VK_L -> lPressed = true;
            case KeyEvent.VK_M -> mPressed = true;
            case KeyEvent.VK_N -> nPressed = true;
            case KeyEvent.VK_O -> oPressed = true;
            case KeyEvent.VK_P -> pPressed = true;
            case KeyEvent.VK_Q -> qPressed = true;
            case KeyEvent.VK_R -> rPressed = true;
            case KeyEvent.VK_S -> sPressed = true;
            case KeyEvent.VK_T -> tPressed = true;
            case KeyEvent.VK_U -> uPressed = true;
            case KeyEvent.VK_V -> vPressed = true;
            case KeyEvent.VK_W -> wPressed = true;
            case KeyEvent.VK_X -> xPressed = true;
            case KeyEvent.VK_Y -> yPressed = true;
            case KeyEvent.VK_Z -> zPressed = true;

            case KeyEvent.VK_0 -> pressed0 = true;
            case KeyEvent.VK_1 -> pressed1 = true;
            case KeyEvent.VK_2 -> pressed2 = true;
            case KeyEvent.VK_3 -> pressed3 = true;
            case KeyEvent.VK_4 -> pressed4 = true;
            case KeyEvent.VK_5 -> pressed5 = true;
            case KeyEvent.VK_6 -> pressed6 = true;
            case KeyEvent.VK_7 -> pressed7 = true;
            case KeyEvent.VK_8 -> pressed8 = true;
            case KeyEvent.VK_9 -> pressed9 = true;

            case KeyEvent.VK_SPACE -> spacePressed = true;
            case KeyEvent.VK_ENTER -> enterPressed = true;
            case KeyEvent.VK_ESCAPE -> escPressed = true;
            case KeyEvent.VK_SHIFT -> shiftPressed = true;
            case KeyEvent.VK_CONTROL -> ctrlPressed = true;
            case KeyEvent.VK_ALT -> altPressed = true;
            case KeyEvent.VK_BACK_SPACE -> backspacePressed = true;
            case KeyEvent.VK_TAB -> tabPressed = true;
            case KeyEvent.VK_CAPS_LOCK -> capsLockPressed = true;
        }

        // Direções alternativas (setas)
        if (code == KeyEvent.VK_UP) upPressed = true;
        if (code == KeyEvent.VK_DOWN) downPressed = true;
        if (code == KeyEvent.VK_LEFT) leftPressed = true;
        if (code == KeyEvent.VK_RIGHT) rightPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_A -> aPressed = false;
            case KeyEvent.VK_B -> bPressed = false;
            case KeyEvent.VK_C -> cPressed = false;
            case KeyEvent.VK_D -> dPressed = false;
            case KeyEvent.VK_E -> ePressed = false;
            case KeyEvent.VK_F -> fPressed = false;
            case KeyEvent.VK_G -> gPressed = false;
            case KeyEvent.VK_H -> hPressed = false;
            case KeyEvent.VK_I -> iPressed = false;
            case KeyEvent.VK_J -> jPressed = false;
            case KeyEvent.VK_K -> kPressed = false;
            case KeyEvent.VK_L -> lPressed = false;
            case KeyEvent.VK_M -> mPressed = false;
            case KeyEvent.VK_N -> nPressed = false;
            case KeyEvent.VK_O -> oPressed = false;
            case KeyEvent.VK_P -> pPressed = false;
            case KeyEvent.VK_Q -> qPressed = false;
            case KeyEvent.VK_R -> rPressed = false;
            case KeyEvent.VK_S -> sPressed = false;
            case KeyEvent.VK_T -> tPressed = false;
            case KeyEvent.VK_U -> uPressed = false;
            case KeyEvent.VK_V -> vPressed = false;
            case KeyEvent.VK_W -> wPressed = false;
            case KeyEvent.VK_X -> xPressed = false;
            case KeyEvent.VK_Y -> yPressed = false;
            case KeyEvent.VK_Z -> zPressed = false;

            case KeyEvent.VK_0 -> pressed0 = false;
            case KeyEvent.VK_1 -> pressed1 = false;
            case KeyEvent.VK_2 -> pressed2 = false;
            case KeyEvent.VK_3 -> pressed3 = false;
            case KeyEvent.VK_4 -> pressed4 = false;
            case KeyEvent.VK_5 -> pressed5 = false;
            case KeyEvent.VK_6 -> pressed6 = false;
            case KeyEvent.VK_7 -> pressed7 = false;
            case KeyEvent.VK_8 -> pressed8 = false;
            case KeyEvent.VK_9 -> pressed9 = false;

            case KeyEvent.VK_SPACE -> spacePressed = false;
            case KeyEvent.VK_ENTER -> enterPressed = false;
            case KeyEvent.VK_ESCAPE -> escPressed = false;
            case KeyEvent.VK_SHIFT -> shiftPressed = false;
            case KeyEvent.VK_CONTROL -> ctrlPressed = false;
            case KeyEvent.VK_ALT -> altPressed = false;
            case KeyEvent.VK_BACK_SPACE -> backspacePressed = false;
            case KeyEvent.VK_TAB -> tabPressed = false;
            case KeyEvent.VK_CAPS_LOCK -> capsLockPressed = false;
        }

        // Direções alternativas (setas)
        if (code == KeyEvent.VK_UP) upPressed = false;
        if (code == KeyEvent.VK_DOWN) downPressed = false;
        if (code == KeyEvent.VK_LEFT) leftPressed = false;
        if (code == KeyEvent.VK_RIGHT) rightPressed = false;
    }

    public void clearAllKeyInputs() {
        upPressed = downPressed = leftPressed = rightPressed = false;

        aPressed = bPressed = cPressed = dPressed = ePressed = fPressed = gPressed = false;
        hPressed = iPressed = jPressed = kPressed = lPressed = mPressed = nPressed = false;
        oPressed = pPressed = qPressed = rPressed = sPressed = tPressed = uPressed = false;
        vPressed = wPressed = xPressed = yPressed = zPressed = false;

        pressed0 = pressed1 = pressed2 = pressed3 = pressed4 =
                pressed5 = pressed6 = pressed7 = pressed8 = pressed9 = false;

        spacePressed = enterPressed = escPressed = shiftPressed = ctrlPressed = altPressed =
                backspacePressed = tabPressed = capsLockPressed = false;
    }
}