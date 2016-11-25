package com.liquidresources.game.model.resource.manager.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.uwsoft.editor.renderer.data.ProjectInfoVO;
import com.uwsoft.editor.renderer.data.ResolutionEntryVO;
import com.uwsoft.editor.renderer.data.SceneVO;
import com.uwsoft.editor.renderer.resources.IResourceRetriever;
import com.uwsoft.editor.renderer.utils.MySkin;

/**
 * Created by leopold on 7/27/16.
 */
public class Overlap2dScenesLoader implements IResourceRetriever {

    @Override
    public TextureRegion getTextureRegion(String name) {
        System.err.println("getTextureRegion not implemented yet");
        return null;
    }

    @Override
    public ParticleEffect getParticleEffect(String name) { // TODO refactor
        ParticleEffect particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal(name), new TextureAtlas(Gdx.files.internal("orig/pack.atlas")));
        return particleEffect;
    }

    @Override
    public TextureAtlas getSkeletonAtlas(String name) {
        System.err.println("getSkeletonAtlas not implemented yet");
        return null;
    }

    @Override
    public FileHandle getSkeletonJSON(String name) {
        System.err.println("getSkeletonJSON not implemented yet");
        return null;
    }

    @Override
    public FileHandle getSCMLFile(String name) {
        System.err.println("getSCMLFile not implemented yet");
        return null;
    }

    @Override
    public TextureAtlas getSpriteAnimation(String name) {
        return null;
    }

    @Override
    public BitmapFont getBitmapFont(String name, int size) {
        return null;
    }

    @Override
    public MySkin getSkin() {
        System.err.println("getSkin not implemented yet");
        return null;
    }

    @Override
    public SceneVO getSceneVO(String sceneName) {
        System.err.println("getSceneVO not implemented yet");
        return null;
    }

    @Override
    public ProjectInfoVO getProjectVO() {
        System.err.println("getProjectVO not implemented yet");
        return null;
    }

    @Override
    public ResolutionEntryVO getLoadedResolution() {
        System.err.println("getLoadedResolution not implemented yet");
        return null;
    }

    @Override
    public ShaderProgram getShaderProgram(String shaderName) {
        System.err.println("getShaderProgram not implemented yet");
        return null;
    }
}
