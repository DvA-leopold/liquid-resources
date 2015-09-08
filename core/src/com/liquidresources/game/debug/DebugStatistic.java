package com.liquidresources.game.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DebugStatistic {
    public DebugStatistic() {
        fontStandard  = new BitmapFont(Gdx.files.internal("fonts/whiteFont.fnt"));
        debugBatch = new SpriteBatch();
    }

    public void render(final SpriteBatch batch) {
        debugBatch.begin();
        fontStandard.draw(debugBatch,
                "DC:" + batch.renderCalls +
                        " fps:" + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() * 0.1f);
        debugBatch.end();
    }

    public void dispose() {
        debugBatch.dispose();
    }


    private BitmapFont fontStandard;
    private SpriteBatch debugBatch;
}
