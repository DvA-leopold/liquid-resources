package com.liquidresources.game.viewModel.bodies.udata.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.viewModel.bodies.udata.UniversalBody;
import com.liquidresources.game.viewModel.bodies.udata.SteerableBodyImpl;
import com.liquidresources.game.viewModel.bodies.udata.bullets.Laser;

import static com.liquidresources.game.model.common.utils.UConverter.m2p;

public class Fighter extends SteerableBodyImpl {
    static {
        shipSize = m2p(Gdx.graphics.getWidth() * 0.04f, Gdx.graphics.getHeight() * 0.04f);
    }

    public Fighter(final Vector2 defaultPosition,
                   int defaultHealth,
                   BodyFactoryWrapper bodyFactoryWrapper,
                   final RelationTypes relationType) {
        super(relationType, defaultHealth);
        this.bodyFactoryWrapper = bodyFactoryWrapper;

        this.shipSprite = new Sprite((Texture) ResourceManager.getInstance().get("drawable/ships/fighter.png"));
        this.shipSprite.setPosition(defaultPosition.x - shipSize.x * 0.5f, defaultPosition.y - shipSize.y * 0.5f);
        this.shipSprite.setSize(shipSize.x, shipSize.y);

        fighterAI = new FighterAI(relationType);

        bodyDef = new BodyDef();
        bodyDef.position.set(defaultPosition.x + shipSize.x, defaultPosition.y + shipSize.y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // TODO использовать joint для задания границ и т.п

        if (bodyShape == null) {
            bodyShape = new PolygonShape();
            bodyShape.setAsBox(shipSize.x * 0.5f, shipSize.y * 0.5f);
        }

        if (fixtureDef == null) {
            fixtureDef = new FixtureDef();
            fixtureDef.shape = bodyShape;
            fixtureDef.isSensor = true;
        }
    }

    @Override
    public void draw(final Batch batch, final Vector2 position, float delta) {
        shipSprite.setPosition(
                position.x - shipSprite.getWidth() * 0.5f,
                position.y - shipSprite.getHeight() * 0.5f
        );
        shipSprite.draw(batch);
    }

    @Override
    public Vector2 getSize() {
        return shipSize;
    }

    @Override
    public void update(final Body myself, float delta) {
        switch(fighterAI.getFighterStatus()) {
            case SEARCH:
                fighterAI.retarget(myself.getPosition(), bodyFactoryWrapper);
                break;
            case ATTACK:
                if (fighterAI.allowAttack(myself.getPosition()) && fighterAI.allowShoot(delta)) {
                    shoot(fighterAI.getTarget());
                }
                // TODO shoot
                break;
            case RELOAD:
                fighterAI.reload(delta);
                // TODO reload
                break;
            case GO_AWAY:
                fighterAI.reload(delta);
                // TODO go away
                break;
            case GET_CLOSE:
                // TODO get close
        }

        boolean anyAcceleration = false;
        if (super.steeringBehavior != null) {
            if (((Arrive<Vector2>)super.steeringBehavior).getTarget() == null) {
                ((Arrive<Vector2>)super.steeringBehavior).setTarget(fighterAI.getTarget());
            }

            super.steeringBehavior.calculateSteering(steeringOutput);
            if (!steeringOutput.linear.isZero()) {
                steeringOutput.linear.y += 9.8;
                super.thisBody.applyForceToCenter(steeringOutput.linear, true);
                anyAcceleration = true;
            }

            if (steeringOutput.angular != 0) {
                super.thisBody.applyTorque(steeringOutput.angular, true);
                anyAcceleration = true;
            }

            if (anyAcceleration) {
                Vector2 velocity = super.thisBody.getLinearVelocity();
                float currentSpeedSquare = velocity.len2();
                float maxLinearSpeed = getMaxLinearSpeed();
                if (currentSpeedSquare > maxLinearSpeed * maxLinearSpeed) {
                    super.thisBody.setLinearVelocity(velocity.scl(maxLinearSpeed / (float)Math.sqrt(currentSpeedSquare)));
                }

                float maxAngVelocity = getMaxAngularSpeed();
                if (super.thisBody.getAngularVelocity() > maxAngVelocity) {
                    super.thisBody.setAngularVelocity(maxAngVelocity);
                }
            }
        }
    }

    @Override
    public void beginCollisionContact(final Body bodyA, BodyFactoryWrapper bodyFactoryWrapper) { }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.FIGHTER_SHIP;
    }

    @Override
    public BodyDef getBodyDef() {
        return bodyDef;
    }

    @Override
    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    private void shoot(UniversalBody target) {
        Vector2 laserSpawnPosition = new Vector2(shipSprite.getX(), shipSprite.getY());
        bodyFactoryWrapper.createBody(new Laser(laserSpawnPosition, target.getPosition(), fighterAI.getRelationType()));
    }

    static public void dispose() {
        if (bodyShape != null) {
            bodyShape.dispose();
            bodyShape = null;
        }
    }


    final private BodyFactoryWrapper bodyFactoryWrapper;

    final private FighterAI fighterAI;

    final private Sprite shipSprite;

    static final private Vector2 shipSize;

    private BodyDef bodyDef;
    static private FixtureDef fixtureDef;
    static private PolygonShape bodyShape;
}
