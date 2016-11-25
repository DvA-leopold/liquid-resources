package com.liquidresources.game.model.resource.manager.loaders;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Map;

public class FreeTypeFontSkinLoader extends SkinLoader {
    public FreeTypeFontSkinLoader(Map<String, BitmapFont> fontsByName) {
        super(new InternalFileHandleResolver());

        if (fontsByName == null) {
            throw new NullPointerException("Font map is null");
        }

        this.fontsByName = fontsByName;
    }

    @Override
    public Skin loadSync(AssetManager manager, String fileName, FileHandle file, SkinParameter parameter) {
        String textureAtlasPath = file.pathWithoutExtension() + ".atlas";
        ObjectMap<String, Object> resources = null;

        if (parameter != null) {
            if (parameter.textureAtlasPath != null) {
                textureAtlasPath = parameter.textureAtlasPath;
            }

            if (parameter.resources != null) {
                resources = parameter.resources;
            }
        }

        TextureAtlas atlas = manager.get(textureAtlasPath, TextureAtlas.class);
        Skin skin = new Skin(atlas);
        for (Map.Entry<String, BitmapFont> kv : this.fontsByName.entrySet()) {
            skin.add(kv.getKey(), kv.getValue());
        }
        if (resources != null) {
            for (ObjectMap.Entry<String, Object> entry : resources.entries()) {
                skin.add(entry.key, entry.value);
            }
        }
        skin.load(file);

        return skin;
    }

    
    private Map<String, BitmapFont> fontsByName;
}
