package com.liquidresources.game.view.animation.oilpump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.view.animation.Animator;

public class OilPumpAnimation implements Animator {
    public OilPumpAnimation(float defaultAnimationSpeed,
                            float xDefaultPosition, float yDefaultPosition,
                            float width, float height) {

        this.defaultAnimationSpeed = defaultAnimationSpeed;
        workSpeed = defaultAnimationSpeed / 2;

        isStoped = false;

        this.width = width;
        this.height = height;

        this.xDefaultPosition = xDefaultPosition;
        this.yDefaultPosition = yDefaultPosition;

        pompFrames = new TextureRegion[10];
        pompImageAtlas = (TextureAtlas) ResourceManager.getInstance().get("animation/oil-pomp.atlas");
    }

    @Override
    public void create(Animation.PlayMode animationPlayMode) {
        for (int i=0; i<10; ++i) {
            pompFrames[i] = new TextureRegion(pompImageAtlas.findRegion("oil-pomp" + Integer.toString(i)));
        }

        pompAnimation = new Animation(workSpeed, pompFrames);
        pompAnimation.setPlayMode(animationPlayMode);
    }

    @Override
    public void draw(final Batch batch) {
        if (!isStoped) {
            workSpeed = Math.max(workSpeed -= 0.001, defaultAnimationSpeed);
            pompAnimation.setFrameDuration(workSpeed);
            stateTime += Gdx.graphics.getDeltaTime();
        }

        batch.draw(pompAnimation.getKeyFrame(stateTime, true), xDefaultPosition, yDefaultPosition, width, height);
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


    final private float xDefaultPosition, yDefaultPosition;
    final private float width, height;

    final private float defaultAnimationSpeed;
    private float workSpeed;
    private boolean isStoped;
    private float stateTime = 0f;

    private TextureRegion[] pompFrames;
    private TextureAtlas pompImageAtlas;
    private Animation pompAnimation;
}
