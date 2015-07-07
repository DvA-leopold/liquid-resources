package com.liquidresources.game.view.animation.oilpump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.view.animation.Animator;

public class OilPumpAnimation implements Animator {
    public OilPumpAnimation(float defaultAnimationSpeed) {
        this.defaultAnimationSpeed = defaultAnimationSpeed;
        workSpeed = defaultAnimationSpeed / 2;
        isStoped = false;
        pompFrames = new TextureRegion[10];
        pompImageAtlas = (TextureAtlas) ResourceManager.getInstance().get("animation/oil-pomp.atlas");
    }

    @Override
    public void create() {
        for (int i=0; i<10; ++i) {
            pompFrames[i] = new TextureRegion(pompImageAtlas.findRegion("oil-pomp" + Integer.toString(i)));

        }
        pompAnimation = new Animation(workSpeed, pompFrames);
        pompAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    @Override
    public void draw(final Batch batch, float x, float y, float width, float height) {
        if (!isStoped) {
            workSpeed = Math.max(workSpeed -= 0.001, defaultAnimationSpeed);
            pompAnimation.setFrameDuration(workSpeed);
            stateTime += Gdx.graphics.getDeltaTime();
        } else {

        }
        batch.draw(pompAnimation.getKeyFrame(stateTime, true), x, y, width, height);
    }

    @Override
    public void resetAnimation(boolean isStoped) {
        this.isStoped = isStoped;
        workSpeed = defaultAnimationSpeed;
    }


    private float workSpeed;
    final private float defaultAnimationSpeed;
    private boolean isStoped;
    private float stateTime = 0f;

    private TextureRegion[] pompFrames;
    private TextureAtlas pompImageAtlas;
    private Animation pompAnimation;
}
