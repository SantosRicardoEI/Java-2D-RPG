package core;

import core.states.TitleState;

import java.awt.*;

public class GameStateManager {

    private IGameState currentState;

    public GameStateManager(GamePanel gp) {
        currentState = new TitleState(gp);
    }

    public IGameState getState() {
        return currentState;
    }

    public void setState(IGameState newState) {
        currentState = newState;
    }

    public void draw(Graphics2D g2) {
        currentState.draw(g2);
    }

    public void update() {
        currentState.update();
    }
}