package com.liquidresources.game.model.resource.manager;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.liquidresources.game.model.resource.manager.loaders.FreeTypeFontSkinLoader;

import java.io.FileNotFoundException;
import java.util.*;

public class ResourceManager {
    private ResourceManager() {
        currentStorageSize = 0;
        mimeFileTypes = new Hashtable<>();
        assetManager = new AssetManager();
        fontMap = new HashMap<>();

        mimeFileTypes.put("png", Texture.class);
        mimeFileTypes.put("jpeg", Texture.class);
        mimeFileTypes.put("jpg", Texture.class);
        mimeFileTypes.put("bmp", Texture.class);

        mimeFileTypes.put("pack", TextureAtlas.class);
        mimeFileTypes.put("atlas", TextureAtlas.class);

        mimeFileTypes.put("mp3", Music.class);
        mimeFileTypes.put("ogg", Audio.class);

        mimeFileTypes.put("fnt", BitmapFont.class);
        mimeFileTypes.put("ttf", BitmapFont.class);

        mimeFileTypes.put("json", Skin.class);

        mimeFileTypes.put("p", ParticleEffect.class);
    }

    public static ResourceManager instance() {
        return SingletonHolder.instance;
    }

    /**
     * load all files in section folder and sub folder of this section
     * @param section path to the folder
     * @param sync if this is true then we will wait till all files are load
     */
    public void loadSection(String section, boolean sync) {
        FileHandle sectionRoot = Gdx.files.internal(section);
        try {
            FileHandle[] allFiles = getFiles(sectionRoot);
            for (FileHandle file : allFiles) {
                String fileName = file.file().getName();
                String extension = getExtension(fileName);
                if (mimeFileTypes.containsKey(extension)) {
                    assetManager.load(file.path(), mimeFileTypes.get(extension));
                    currentStorageSize += file.length();
                }
            }
            if (sync) {
                assetManager.finishLoading();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * unload chosen files in a folder and files in a sub-folders
     * @param section path to the folder
     */
    public void unloadSection(String section) {
        FileHandle sectionRoot = Gdx.files.internal(section);
        try {
            FileHandle[] allFiles = getFiles(sectionRoot);
            for (FileHandle allFile : allFiles) {
                assetManager.unload(allFile.path());
                currentStorageSize -= allFile.length();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * load separate file from a directory
     * @param fileName name of the file fo downloading
     * @param sync if this parameter is <code>true</code> than stop and wait loading finished/
     * @throws FileNotFoundException if no such extension in <code>mimeFileType</code> exist
     */
    public void loadFile(String fileName, boolean sync) throws FileNotFoundException {
        String fileExtension = getExtension(fileName);
        if (mimeFileTypes.containsKey(fileExtension)) {
            assetManager.load(fileName, mimeFileTypes.get(fileExtension));
            currentStorageSize += new FileHandle(fileName).length();
        } else {
            throw new FileNotFoundException(" no such extension for this type of file " + fileName);
        }
        if (sync) {
            assetManager.finishLoading();
        }
    }

    public void unloadFile(String fileName) {
        assetManager.unload(fileName);
    }

    public Object get(String fileName) {
        if (assetManager.isLoaded(fileName)) {
            return assetManager.get(fileName);
        } else {
            System.err.println(fileName + " was not loaded ");
            return null;
        }
    }

    public void generateFonts(String ttfFilePath) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(ttfFilePath));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = Gdx.graphics.getHeight() * 24 / Gdx.graphics.getWidth();
        fontParameter.color = Color.BLACK;
        BitmapFont mainFonts = fontGenerator.generateFont(fontParameter);

        fontParameter.color = Color.WHITE;
        BitmapFont addFonts = fontGenerator.generateFont(fontParameter);

        fontMap.put("mainFonts", mainFonts);
        fontMap.put("addFonts", addFonts);

        fontGenerator.dispose();
        assetManager.setLoader(Skin.class, new FreeTypeFontSkinLoader(fontMap));
    }

    public BitmapFont getFont(String fontName) {
        return fontMap.get(fontName);
    }

    public float updateAndGetProgress() {
        assetManager.update();
        return assetManager.getProgress();
    }

    public long getCurrentStorageSize() {
        return currentStorageSize / 1024 / 1024;
    }

    public void dispose() {
        instance().assetManager.dispose();
    }

    private FileHandle[] getFiles(FileHandle sectionForLoading) throws FileNotFoundException {
        if (!sectionForLoading.isDirectory()) {
            throw new FileNotFoundException(" this is not a directory ");
        }
        Queue<FileHandle> fileHandles = new LinkedList<>();
        LinkedList<FileHandle> filesList = new LinkedList<>();

        fileHandles.add(sectionForLoading);
        while (!fileHandles.isEmpty()) {
            FileHandle[] filesInFolder = fileHandles.poll().list();
            for (FileHandle aFilesInFolder : filesInFolder) {
                if (aFilesInFolder.isDirectory()) {
                    fileHandles.add(aFilesInFolder);
                } else {
                    filesList.add(aFilesInFolder);
                }
            }
        }
        FileHandle[] filesListArray = new FileHandle[filesList.size()];
        filesList.toArray(filesListArray);
        return filesListArray;
    }

    private String getExtension(String filePath) throws NoSuchElementException {
        int index = filePath.lastIndexOf(".");
        if (index == -1) {
            throw new NoSuchElementException("this file had no extension");
        }
        return filePath.substring(index + 1);
    }

    private static class SingletonHolder {
        private static final ResourceManager instance = new ResourceManager();
    }


    final private AssetManager assetManager;
    final private Map<String, BitmapFont> fontMap; // TODO get fonts general from asset manager

    final private Hashtable<String, Class> mimeFileTypes;
    private long currentStorageSize;
}
