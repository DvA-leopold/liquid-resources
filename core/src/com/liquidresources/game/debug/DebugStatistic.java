package com.liquidresources.game.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DebugStatistic {
    public DebugStatistic(final BitmapFont mainFont) {
        this.mainFont = mainFont;
    }

    public void render(final SpriteBatch batch) {
        batch.begin();
        mainFont.draw(
                batch, "fps:" + Gdx.graphics.getFramesPerSecond(),
                10, Gdx.graphics.getHeight() * 0.08f);
        batch.end();
    }


    final private BitmapFont mainFont;
}
