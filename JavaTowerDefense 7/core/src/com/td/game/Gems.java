package com.td.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by evkingen on 27.04.2018.
 */
public class Gems {
    private int count;
    private StringBuilder sbHUD;
    public Gems(int count) {
        this.count = count;
        this.sbHUD = new StringBuilder(20);
        this.sbHUD.append("Gems: ");
    }
    public int getCount() {
        return count;
    }


    public void spend() {
        this.count -= 10;
    }
    public void refund() {
        this.count += 10;
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        this.sbHUD.setLength(6);
        this.sbHUD.append(this.count);
        font.draw(batch, sbHUD, 160, 80, 1, 1, false);
    }

    public void update(Float dt) {

    }

}
