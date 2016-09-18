package com.liquidresources.game.model.bodies;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.liquidresources.game.model.EntityInitializer;
import com.liquidresources.game.model.types.RelationTypes;
import com.uwsoft.editor.renderer.components.physics.PhysicsBodyComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;


public abstract class UpdatableBodyImpl implements UpdatableBody, IScript {
    public UpdatableBodyImpl(RelationTypes relationType, int health) {
        this.relationType = relationType;
        this.health = health;
    }

    public static void setEntityInitializer(final EntityInitializer entityInitializer) {
        if (UpdatableBodyImpl.entityInitializer == null) {
            UpdatableBodyImpl.entityInitializer = entityInitializer;
        }
    }

    protected void takeDamage(int dmg) {
        health -= dmg;
        if (health < 1) {
            dispose();
        }
    }

    @Override
    public RelationTypes getRelation() {
        return relationType;
    }

    @Override
    public void init(Entity entity) {
        this.entity = entity;
        //need to make one render call to init physic body
        entityInitializer.getEngine().update(Gdx.graphics.getDeltaTime()); // FIXME related to ***1, cant notify here, result is NullPointerException
                                                    // FIXME cos user data does not attached yet, but collision already happened
        PhysicsBodyComponent physicsBodyComponent = ComponentRetriever.get(entity, PhysicsBodyComponent.class);
        if (physicsBodyComponent != null && physicsBodyComponent.body != null) {
            physicsBodyComponent.body.setUserData(this);
            switch (getBodyType()) {
                case ION_SHIELD:
                    physicsBodyComponent.body.setActive(false);
                    break;
                default:
                    break;
            }
        }
    }

    public static void finalDispose() {
        entityInitializer = null;
    }


    private int health;
    protected Entity entity;
    final private RelationTypes relationType;

    static protected EntityInitializer entityInitializer;
}
