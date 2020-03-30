package com.liquidresources.game.model.bodies.bariers;

import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.bodies.buildings.Capital;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.model.bodies.UpdatableBody;

final public class IonShield extends UpdatableBody {
    public IonShield(final RelationTypes relationType) {
        super(relationType, 100);
        isActive = false;
    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.ION_SHIELD;
    }

    @Override
    public void collisionContact(Body collidedWithBody) {
        if (isActive) {
            if (!((Capital) entityInitializer.getBaseSceneElement("capital")).changeOil(-100)) {
                isActive = !isActive;
            }
        }
    }

    @Override
    public void act(float delta) {
        if (physicsBodyComponent != null && isActive != physicsBodyComponent.body.isActive()) {
            physicsBodyComponent.body.setActive(isActive);
        }

        if (isActive) {
            if (!((Capital) entityInitializer.getBaseSceneElement("capital")).changeOil(-3)) {
                isActive = false;
                physicsBodyComponent.body.setActive(false);
//                setIonShieldChecked();
            }
        }
    }

    @Override
    public void dispose() { }

    public void switchShield() throws NullPointerException {
        physicsBodyComponent.body.setActive(!isActive);
        isActive = !isActive;
    }


    private boolean isActive;
}
