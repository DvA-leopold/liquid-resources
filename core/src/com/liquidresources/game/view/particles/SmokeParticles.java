package com.liquidresources.game.view.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class SmokeParticles {
    public SmokeParticles(Vector2 smokePosition, boolean isContinuous) { //TODO разобраться с эммитерами
        listOfParticleEffects = new ArrayList<>(numberOfParticleEffects);
        for (int i = 0; i < numberOfParticleEffects; ++i) {
            listOfParticleEffects.add(new ParticleEffect());
            listOfParticleEffects.get(i).load(Gdx.files.internal("particles/smoke.p"), Gdx.files.internal("particles"));//TODO сделать лоадер
            listOfParticleEffects.get(i).setPosition(smokePosition.x, smokePosition.y);
            listOfParticleEffects.get(i).findEmitter("smoke"/* + Integer.toString(i)*/).setContinuous(isContinuous);
        }
    }

    public void draw(final Batch batch, float delta) {
        if (isAnimationStarted) {
            for (ParticleEffect p : listOfParticleEffects) {
                p.draw(batch, delta);
            }
        }
    }

    public void startEffect() {
        isAnimationStarted = true;
        for (ParticleEffect p : listOfParticleEffects) {
            p.start();
        }
    }

    public void stopEffect() {
        isAnimationStarted = false;
        for (ParticleEffect p : listOfParticleEffects) {
            //smokeEffect.findEmitter("smoke").allowCompletion();
            p.findEmitter("smoke").allowCompletion(); // TODO сделать плавное завершение эффекта
        }
    }

    public void dispose() {
        for(ParticleEffect effect : listOfParticleEffects) {
            effect.dispose();
        }
    }


    private boolean isAnimationStarted = false;

    final private int numberOfParticleEffects = 1;
    private ArrayList<ParticleEffect> listOfParticleEffects;
}
