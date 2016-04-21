package com.liquidresources.game.model.tasks;

import com.badlogic.gdx.math.Vector2;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;

public class SpawnBodyTask implements Task {
    public SpawnBodyTask(BodyTypes bodyType,
                         RelationTypes bodyRelation,
                         Vector2 position,
                         Vector2 size,
                         int health) {
        this.bodyType = bodyType;
        this.bodyRelation = bodyRelation;
        this.position = position;
        this.size = size;
        this.health = health;
    }

    @Override
    public void runTask() {

    }

    @Override
    public BodyTypes getBodyType() {
        return bodyType;
    }


    final private BodyTypes bodyType;
    final public RelationTypes bodyRelation;
    final public Vector2 position;
    final public Vector2 size;
    final public int health;
}
