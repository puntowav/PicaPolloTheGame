package iticbcn.elhueso.picapollo.utils;

import com.badlogic.gdx.graphics.Color;

/**
 *
 * Classe de suport per a representar blocs de pixels.
 * Utilitzada per a representar els rectangles de diferents colors
 * que ccntenen els bitmaps dels nivells del joc.
 *
 * El rectangle es representa en els pixels del Bitmap
 *
 */

public class PPGRectangle {

    // Posició en el mapa
    private float x;
    private float y;

    // Tamany del rectangle
    private float width;
    private float height;

    // Color
    private Color color;

    public PPGRectangle(float x, float y, float width, float height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
