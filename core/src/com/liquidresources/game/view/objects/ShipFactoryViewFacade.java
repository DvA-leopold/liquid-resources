package com.liquidresources.game.view.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.view.particles.SmokeParticles;

public class ShipFactoryViewFacade {
    public ShipFactoryViewFacade(int x, int y) {
        Texture factoryTexture = (Texture) ResourceManager.getInstance().get("buildings/factory.png");
        shipFactory = new Sprite(factoryTexture, 0, 0, factoryTexture.getWidth(), factoryTexture.getHeight());
        shipFactory.setPosition(x, y);
        shipFactory.setSize(Gdx.graphics.getWidth() * 0.15f, Gdx.graphics.getHeight() * 0.15f);
        //shipFactory = (Texture) ResourceManager.getInstance().get("buildings/factory.png");
        smokeParticles = new SmokeParticles(new Vector2(x + 35, y + shipFactory.getHeight() * 0.8f), true);
    }

    public void draw(final Batch batch, float delta) {
        //batch.draw(shipFactory, x, y, width, height);
        smokeParticles.draw(batch, delta);
        shipFactory.draw(batch);
    }

    public void startEffect() {
        smokeParticles.startEffect();
    }

    public void stopEffect() {
        smokeParticles.stopEffect();
    }

    public void dispose() {
        smokeParticles.dispose();
    }


    private Sprite shipFactory;
    //private Texture shipFactory;
    private SmokeParticles smokeParticles;
}
