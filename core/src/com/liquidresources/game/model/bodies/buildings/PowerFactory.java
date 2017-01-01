package com.liquidresources.game.model.bodies.buildings;

import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.model.bodies.UpdatableBody;
import com.uwsoft.editor.renderer.components.particle.ParticleComponent;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;
import com.uwsoft.editor.renderer.utils.ItemWrapper;


final public class PowerFactory extends UpdatableBody {
    public PowerFactory(final RelationTypes relationType) {
        super(relationType, 100);
    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.SHIP_FACTORY;
    }

    @Override
    public void collisionContact(Body collidedWithBody) {
        UpdatableBody collidedUpdatableBody = (UpdatableBody) collidedWithBody.getUserData();
        if (collidedUpdatableBody.getRelation() == RelationTypes.ENEMY) {
            switch (collidedUpdatableBody.getBodyType()) {
                case METEOR:
                    takeDamage(25);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void act(float delta) {
        if (!isInitialized) {
            return;
        }

        if (health <= 0 && physicsBodyComponent.body.isActive()) {
            physicsBodyComponent.body.setActive(false);
        }
    }

    @Override
    public void dispose() {
        ParticleComponent particleComponent = ComponentRetriever.get(
                new ItemWrapper(getEntity()).getChild("smoke").getEntity(),
                ParticleComponent.class);
        particleComponent.particleEffect.allowCompletion();
    }
}
