package com.liquidresources.game.model.bodies.bariers;

import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.bodies.buildings.Capital;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.model.bodies.UpdatableBodyImpl;
import com.uwsoft.editor.renderer.components.physics.PhysicsBodyComponent;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

import static com.liquidresources.game.view.screens.game.widgets.GameScreenWidgetsGroup.setIonShieldChecked;


final public class IonShield extends UpdatableBodyImpl {
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
                ComponentRetriever.get(super.entity, PhysicsBodyComponent.class).body.setActive(false);
                switchShield();
            }
        }
    }

    @Override
    public void act(float delta) {
        if (isActive) {
            if (!((Capital) entityInitializer.getBaseSceneElement("capital")).changeOil(-3)) {
                isActive = false;
                ComponentRetriever.get(super.entity, PhysicsBodyComponent.class).body.setActive(false);
                setIonShieldChecked();
            }
        }
    }

    @Override
    public void dispose() { }

    public void switchShield() throws NullPointerException {
        ComponentRetriever.get(super.entity, PhysicsBodyComponent.class).body.setActive(!isActive);
        isActive = !isActive;
    }


    private boolean isActive;
}
