package com.liquidresources.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
    public GameScreen(final SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        smokeEffect = new ParticleEffect();
        smokeEffect.load(Gdx.files.internal("particles/smoke.p"), Gdx.files.internal("particles"));
        smokeEffect.setPosition(200, 200);
        smokeEffect.findEmitter("smoke").setContinuous(true);
        smokeEffect.findEmitter("smoke").setAttached(true);
        smokeEffect.start();

    }

    @Override
    public void render(float delta) {

        batch.begin();
        smokeEffect.draw(batch, delta);
        batch.end();

        if (Gdx.input.isTouched()) {
            smokeEffect.findEmitter("smoke").setContinuous(false);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        smokeEffect.dispose();
    }

    private ParticleEffect smokeEffect;
    private final SpriteBatch batch;
}
