package com.liquidresources.game.view.particles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.liquidresources.game.model.resource.manager.ResourceManager;

import java.util.ArrayList;

public class SmokeParticles {
    public SmokeParticles(Vector2 smokePosition, boolean isContinuous) {
        listOfParticleEffects = new ArrayList<>(numberOfParticleEffects);
        for (int i = 0; i < numberOfParticleEffects; ++i) {
            listOfParticleEffects.add(new ParticleEffect((ParticleEffect) ResourceManager.getInstance().get("particles/smoke.p")));
            listOfParticleEffects.get(i).findEmitter("smoke"/* + Integer.toString(i)*/).setContinuous(isContinuous);

            listOfParticleEffects.get(i).scaleEffect(0.05f);
            listOfParticleEffects.get(i).setPosition(smokePosition.x, smokePosition.y);
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
            p.findEmitter("smoke").allowCompletion(); // TODO сделать плавное завершение анимации
        }
    }

    public void resetEffect() {
        for (ParticleEffect p : listOfParticleEffects) {
            p.reset();
        }
    }


    private boolean isAnimationStarted = false;

    final private short numberOfParticleEffects = 1;
    private ArrayList<ParticleEffect> listOfParticleEffects;
}
