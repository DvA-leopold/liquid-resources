package com.liquidresources.game.view.symbols;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.liquidresources.game.model.resource.manager.ResourceManager;

import java.util.ArrayList;

public class SymbolsRenderer {
    public SymbolsRenderer(float xDefaultPosition, float yDefaultPosition, float width, float height) {
        this.xDefaultPosition = xDefaultPosition;
        this.yDefaultPosition = yDefaultPosition;
        this.width = width;
        this.height = height;

        TextureAtlas numbersTextureAtlas = (TextureAtlas) ResourceManager.getInstance().get("symbols/numb.atlas");
        texturesNumbersArray = new ArrayList<>(10);

        for (int i = 0; i < 10; ++i) {
            texturesNumbersArray.add(numbersTextureAtlas.createSprite("num" + Integer.toString(i)));
        }
    }

    public void renderNumber(final Batch batch, int number) {
        //сдвиг цифр в обратную сторону: i = 0; i< numberOfDigits; ++i; and xDefaultPosition - width * i
        int numberOfDigits = (int) Math.ceil(Math.log10(number + 1));
        for (int i = numberOfDigits; i > 0; --i) {
            batch.draw(texturesNumbersArray.get(number % 10), xDefaultPosition + width * i, yDefaultPosition, width, height);
            number /= 10;
        }
    }


    private float xDefaultPosition, yDefaultPosition;
    private float width, height;

    private ArrayList<Sprite> texturesNumbersArray;
}