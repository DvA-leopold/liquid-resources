package com.liquidresources.game.view.animation.oilpump;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.view.animation.Animator;
import com.liquidresources.game.view.DrawableBody;

public class OilPumpAnimation implements Animator, DrawableBody {
    public OilPumpAnimation(float defaultAnimationSpeed,
                            float xDefaultPosition, float yDefaultPosition,
                            float width, float height,
                            Animation.PlayMode animationPlayMode) {

        this.defaultAnimationSpeed = defaultAnimationSpeed;
        workSpeed = defaultAnimationSpeed / 2;

        isStoped = false;

        this.width = width;
        this.height = height;

        this.xDefaultPosition = xDefaultPosition;
        this.yDefaultPosition = yDefaultPosition;

        TextureRegion[] pompFrames = new TextureRegion[10];
        TextureAtlas pompImageAtlas = (TextureAtlas) ResourceManager.getInstance().get("drawable/animation/oil-pomp.atlas");

        for (int i=0; i<10; ++i) {
            pompFrames[i] = new TextureRegion(pompImageAtlas.findRegion("oil-pomp" + Integer.toString(i)));
        }

        pompAnimation = new Animation(workSpeed, pompFrames);
        pompAnimation.setPlayMode(animationPlayMode);

        bodyDef = new BodyDef();
        bodyDef.position.set(xDefaultPosition + width * 0.5f, yDefaultPosition + height * 0.5f);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(width * 0.5f, height * 0.5f);

        //TODO change to normal values later
        fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.isSensor = true;
    }

    @Override
    public void draw(final Batch batch, Vector2 position, float delta) {
        if (!isStoped) {
            workSpeed = Math.max(workSpeed -= 0.001, defaultAnimationSpeed);
            pompAnimation.setFrameDuration(workSpeed);
            stateTime += delta;
        }

        if (position == null) {
            batch.draw(pompAnimation.getKeyFrame(stateTime, true), xDefaultPosition, yDefaultPosition, width, height);
        } else {
            batch.draw(pompAnimation.getKeyFrame(stateTime, true), position.x, position.y, width, height);
        }
    }

    @Override
    public BodyDef getBodyDef() {
        return bodyDef;
    }

    @Override
    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    @Override
    public void resetAnimation(boolean isStoped) {
        this.isStoped = isStoped;
        workSpeed = defaultAnimationSpeed;
    }

    @Override
    public float getX() {
        return xDefaultPosition;
    }

    @Override
    public float getY() {
        return yDefaultPosition;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void dispose() { }


    final private float xDefaultPosition, yDefaultPosition;
    final private float width, height;

    final private float defaultAnimationSpeed;
    private float workSpeed;
    private boolean isStoped;
    private float stateTime = 0f;

    private BodyDef bodyDef;
    private FixtureDef fixtureDef;

    private Animation pompAnimation;
}
