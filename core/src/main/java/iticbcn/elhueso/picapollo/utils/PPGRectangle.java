package iticbcn.elhueso.picapollo.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;


/**
 * Extén rectangle per aprofitar la llògica de col·lisions
 * Classe de suport per a representar blocs de pixels.
 * Utilitzada per a representar els rectangles de diferents colors
 * que ccntenen els bitmaps dels nivells del joc.
 *
 * El rectangle es representa en els pixels del Bitmap
 *
 */

public class PPGRectangle extends Rectangle {
    // Color
    private Color color;

    public PPGRectangle(float x, float y, float width, float height, Color color) {
        super(x, y, width, height);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
