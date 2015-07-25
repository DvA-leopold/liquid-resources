package com.liquidresources.game.version;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class VersionHandler {
    static {
        fontStandard = new BitmapFont(Gdx.files.internal("fonts/whiteFont.fnt"));
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

    public static void render(final SpriteBatch batch) {
        batch.begin();
        fontStandard.draw(batch, version, 10, Gdx.graphics.getHeight() * 0.05f);
        batch.end();
    }


    private static final String version;
    public static BitmapFont fontStandard;
}
