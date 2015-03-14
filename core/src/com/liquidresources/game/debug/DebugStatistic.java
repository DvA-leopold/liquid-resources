package com.liquidresources.game.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DebugStatistic {
    public DebugStatistic() {
        System.out.println(Gdx.files.internal("").path());
        if (isWindows()) {
            debugBatch = new SpriteBatch(5000, DebugShader.createDefaultShader());
        } else {
            debugBatch = new SpriteBatch();
        }
    }

    public void render(final SpriteBatch batch) {
        debugBatch.begin();
        fontStandart.draw(debugBatch,
                "D_C:" + batch.renderCalls +
                        " fps:" + Gdx.graphics.getFramesPerSecond(), 20, 20);
        debugBatch.end();
    }

    public boolean isWindows() {
        final String os = System.getProperty("os.name").toLowerCase();
        //windows
        return os.contains("win");
    }

    public void dispose() {
        debugBatch.dispose();
    }

    private BitmapFont fontStandart = new BitmapFont(Gdx.files.internal("fonts/whiteFont.fnt"));
    private SpriteBatch debugBatch;

}
