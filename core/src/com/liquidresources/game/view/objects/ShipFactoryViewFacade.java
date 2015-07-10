package com.liquidresources.game.view.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.view.particles.SmokeParticles;

public class ShipFactoryViewFacade {
    public ShipFactoryViewFacade(float xDefaultPosition, float yDefaultPosition, float width, float height) {
        Texture factoryTexture = (Texture) ResourceManager.getInstance().get("buildings/shipFactory.png");

        shipFactory = new Sprite(factoryTexture);
        shipFactory.setPosition(xDefaultPosition, yDefaultPosition);
        shipFactory.setSize(width, height);

        smokeParticles = new SmokeParticles(
                new Vector2(xDefaultPosition + 35, yDefaultPosition + shipFactory.getHeight() * 0.8f),
                true
        );
    }

    public void draw(final Batch batch, float delta) {
        smokeParticles.draw(batch, delta);
        shipFactory.draw(batch);
    }

    public void startEffect() {
        smokeParticles.startEffect();
    }

    public void stopEffect() {
        smokeParticles.stopEffect();
    }

    public float getWidth() {
        return shipFactory.getWidth();
    }

    public float getHeight() {
        return shipFactory.getHeight();
    }

    public void dispose() {
        smokeParticles.dispose();
    }


    final private Sprite shipFactory;
    final private SmokeParticles smokeParticles;
}
