package com.liquidresources.game.model.bodies.bariers;

import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.bodies.buildings.Capital;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.model.bodies.UpdatableBody;

import static com.liquidresources.game.view.screens.game.widgets.GameScreenWidgetsGroup.setIonShieldChecked;


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
            if (!((Capital) entityInitializerSystem.getBaseSceneElement("capital")).changeOil(-100)) {
                isActive = !isActive;
            }
        }
    }

    public void act(float delta) {
        if (body != null && isActive != body.isActive()) {
            body.setActive(isActive);
        }

        if (isActive) {
            if (!((Capital) entityInitializerSystem.getBaseSceneElement("capital")).changeOil(-3)) {
                isActive = false;
                body.setActive(false);
                setIonShieldChecked();
            }
        }
    }

    public void switchShield() throws NullPointerException {
        body.setActive(!isActive);
        isActive = !isActive;
    }


    private boolean isActive;
}
