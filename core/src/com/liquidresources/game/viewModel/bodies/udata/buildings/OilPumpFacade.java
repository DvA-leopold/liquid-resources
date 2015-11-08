package com.liquidresources.game.viewModel.bodies.udata.buildings;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;

public class OilPumpFacade extends Building {
    public OilPumpFacade(float defaultAnimationSpeed,
                         final Vector2 defaultPosition,
                         final Vector2 buildingSize,
                         Animation.PlayMode animationPlayMode,
                         RelationTypes relationType) {
        super(defaultPosition, null, buildingSize, relationType);
        this.buildingSize = buildingSize;

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
    }

    @Override
    protected void initBodyDefAndFixture(Vector2 startPosition, Vector2 endPosition, Vector2 buildingSize) {
        bodyDef = new BodyDef();
        bodyDef.position.set(
                startPosition.x + buildingSize.x * 0.5f,
                startPosition.y + buildingSize.y * 0.5f
        );
        bodyDef.type = BodyDef.BodyType.StaticBody;

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(buildingSize.x * 0.5f, buildingSize.y * 0.5f);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.isSensor = false;
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
                bodyDef.position.x - buildingSize.x * 0.5f, bodyDef.position.y - buildingSize.y * 0.5f,
                buildingSize.x, buildingSize.y
        );
    }

    @Override
    public Vector2 getSize() {
        return buildingSize;
    }

    @Override
    public void update(final Body body, float delta) {

    }

    @Override
    public void beginCollisionContact(final Body bodyA) {

    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.OIL_POMP;
    }

    public void resetAnimation(boolean isStoped) {
        this.isStoped = isStoped;
        workSpeed = defaultAnimationSpeed;
    }


    final private float defaultAnimationSpeed;
    final private Vector2 buildingSize;

    private float workSpeed;
    private boolean isStoped;
    private float stateTime = 0f;

    private Animation pompAnimation;
}
