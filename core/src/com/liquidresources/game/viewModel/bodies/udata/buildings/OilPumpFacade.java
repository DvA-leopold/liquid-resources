package com.liquidresources.game.viewModel.bodies.udata.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.viewModel.bodies.udata.SteerableBodyImpl;

import static com.liquidresources.game.model.common.utils.UConverter.m2p;

public class OilPumpFacade extends SteerableBodyImpl {
    static {
        oilPumpSize = m2p(Gdx.graphics.getWidth() * 0.08f, Gdx.graphics.getHeight() * 0.08f);
    }

    public OilPumpFacade(float defaultAnimationSpeed,
                         final Vector2 defaultPosition,
                         Animation.PlayMode animationPlayMode,
                         RelationTypes relationType) {
        super(relationType, 100);
        this.defaultAnimationSpeed = defaultAnimationSpeed;
        workSpeed = defaultAnimationSpeed / 2;

        isStoped = false;

        TextureRegion[] pompFrames = new TextureRegion[10];
        TextureAtlas pompImageAtlas = (TextureAtlas) ResourceManager.getInstance().get("drawable/animation/oil-pomp.atlas");

        for (int i=0; i<10; ++i) {
            pompFrames[i] = new TextureRegion(pompImageAtlas.findRegion("oil-pomp" + Integer.toString(i)));
        }

        pompAnimation = new Animation(workSpeed, pompFrames);
        pompAnimation.setPlayMode(animationPlayMode);

        bodyDef = new BodyDef();
        bodyDef.position.set(defaultPosition.x + oilPumpSize.x * 0.5f, defaultPosition.y + oilPumpSize.y * 0.5f);

        if (bodyShape == null) {
            bodyShape = new PolygonShape();
            bodyShape.setAsBox(oilPumpSize.x * 0.5f, oilPumpSize.y * 0.5f);
        }

        if (fixtureDef == null) {
            fixtureDef = new FixtureDef();
            fixtureDef.shape = bodyShape;
            fixtureDef.isSensor = false;
        }
    }

    @Override
    public void draw(final Batch batch, Vector2 position, float delta) {
        if (!isStoped) {
            workSpeed = Math.max(workSpeed -= 0.001, defaultAnimationSpeed);
            pompAnimation.setFrameDuration(workSpeed);
            stateTime += delta;
        }

        batch.draw(
                pompAnimation.getKeyFrame(stateTime, true),
                bodyDef.position.x - oilPumpSize.x * 0.5f, bodyDef.position.y - oilPumpSize.y * 0.5f,
                oilPumpSize.x, oilPumpSize.y
        );
    }

    @Override
    public Vector2 getSize() {
        return oilPumpSize;
    }

    @Override
    public void update(final Body body, float delta) {

    }

    @Override
    public void beginCollisionContact(final Body bodyA, BodyFactoryWrapper bodyFactoryWrapper) {

    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.OIL_POMP;
    }

    @Override
    public BodyDef getBodyDef() {
        return bodyDef;
    }

    @Override
    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    public void resetAnimation(boolean isStoped) {
        this.isStoped = isStoped;
        workSpeed = defaultAnimationSpeed;
    }

    public static void dispose() {
        if (bodyShape != null) {
            bodyShape.dispose();
            bodyShape = null;
        }
    }


    final private float defaultAnimationSpeed;
    static final private Vector2 oilPumpSize;

    private float workSpeed;
    private boolean isStoped;
    private float stateTime = 0f;

    private Animation pompAnimation;

    private BodyDef bodyDef;
    static private FixtureDef fixtureDef;
    static private PolygonShape bodyShape;
}
