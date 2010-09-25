package com.github.fhd.gwtetris.client.gamelogic;

import java.util.*;

class Game {
    boolean running = false;

    void start() {
        running = true;
    }

    boolean isRunning() {
        return running;
    }

    List<Action> move() {
        List<Action> actions = new ArrayList<Action>();
        actions.add(new Action());
        return actions;
    }
}
