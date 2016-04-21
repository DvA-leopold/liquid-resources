package com.liquidresources.game.model.tasks;

import com.liquidresources.game.model.types.BodyTypes;

public interface Task {
    void runTask();
    BodyTypes getBodyType();
}
