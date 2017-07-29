package com.merseyside.admin.seeworld.SeeWorld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.merseyside.admin.seeworld.Application;
import com.merseyside.admin.seeworld.Constants;
import com.merseyside.admin.seeworld.R;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by ivan_ on 25.07.2017.
 */

public class Cell {
    private int x,y;
    private SeaBeast beast;
    private ArrayList<Cell> nearby_cells;
    private Paint myPaint;
    @Inject
    Context context;

    public Cell(){
        Application.getComponent().inject(this);
        myPaint = new Paint();
    }

    public void setCoords(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void clean(){
        beast = null;
    }

    public SeaBeast getBeast() {
        return beast;
    }

    public boolean isEmpty() {
        return beast == null;
    }

    public void setBeast(SeaBeast beast) {
        if (this.beast != null && this.beast.getBeastName().equals(Constants.PINGUIN) && beast.getBeastName().equals(Constants.SHARK)) this.beast.die();
        this.beast = beast;
    }

    public void setNearbyCells(ArrayList<Cell> nearby_cells) {
        this.nearby_cells = nearby_cells;
    }

    public ArrayList<Cell> getNearbyCells() {
        return nearby_cells;
    }

    public void onDraw(Canvas canvas) {
        myPaint.setColor(context.getResources().getColor(R.color.border_color));
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(2);
        canvas.drawRect(x, y, x+ Constants.CELL_SIZE, y + Constants.CELL_SIZE, myPaint);
        if (beast != null) beast.onDraw(canvas, x, y);
    }

    public String getBeastName() {
        return beast.getBeastName();
    }
}
