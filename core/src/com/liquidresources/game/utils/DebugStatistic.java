package com.liquidresources.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.model.resource.manager.ResourceManager;


final public class DebugStatistic {
    public DebugStatistic(boolean drawFpsStat, boolean drawVersionStat) {
        this.batch = ((LiquidResources) Gdx.app.getApplicationListener()).getMainBatch();
        this.drawFpsStat = drawFpsStat;
        this.drawVersionStat = drawVersionStat;

        FileHandle versionFile = Gdx.files.internal("version/version.properties");
        if (versionFile.exists()) {
            int equalIndex = versionFile.readString().indexOf("\n");
            String stringWithoutInitData = versionFile.readString().substring(equalIndex);
            version = stringWithoutInitData.
                    replaceAll("major=", "v").
                    replaceAll("minor=", ".").
                    replaceAll("patch=", ".").
                    replaceAll("\n", "");
        } else {
            version = "";
        }
    }

    public void render() {
        if (mainFont == null) {
            if((mainFont = ResourceManager.instance().getFont("addFonts")) == null) {
                return;
            }
        }
        batch.begin();
        if (drawFpsStat) {
            mainFont.draw(
                    batch, "fps:" + Gdx.graphics.getFramesPerSecond(),
                    10, Gdx.graphics.getHeight() * 0.06f);
        }
        if (drawVersionStat) {
            mainFont.draw(batch, version, 10, Gdx.graphics.getHeight() * 0.04f);
        }
        batch.end();
    }


    private boolean drawFpsStat, drawVersionStat;
    final private String version;

    final private Batch batch;
    private BitmapFont mainFont;
}
