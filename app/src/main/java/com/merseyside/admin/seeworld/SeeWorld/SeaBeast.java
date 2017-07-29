package com.merseyside.admin.seeworld.SeeWorld;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;

import com.merseyside.admin.seeworld.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by ivan_ on 25.07.2017.
 */

public abstract class SeaBeast {
    int moves_made;
    Bitmap bmp;
    Cell current_cell;
    private Matrix matrix;
    BeastListener beastListener;

    interface BeastListener{
        void stay();
        void move();
        void die();
        void child(SeaBeast beast);
    }

    public void setBeastListener(BeastListener beastListener)  {
        this.beastListener = beastListener;
    }

    SeaBeast(Cell cell){
        matrix = new Matrix();
        current_cell = cell;
    }

    public void onDraw(Canvas canvas, int x, int y) {
        int sc_x, sc_y;

        sc_x = Constants.CELL_SIZE;
        sc_y = Constants.CELL_SIZE;

        matrix.setTranslate(x, y);
        canvas.drawBitmap(Bitmap.createScaledBitmap(bmp, sc_x, sc_y, true), matrix, null);
    }

    public Cell makeTurn(){
        Random random = new Random();
        ArrayList<Cell> nearby_cells = current_cell.getNearbyCells();
        Cell cell = nearby_cells.get(random.nextInt(nearby_cells.size()));
        if (cell.isEmpty()) {
            cell.setBeast(this);
            current_cell.clean();
            current_cell = cell;
            beastListener.move();
        } else {
            beastListener.stay();
        }
        moves_made++;
        return current_cell;
    }

    protected ArrayList<Cell> getNearbyEmptyCells() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return current_cell.getNearbyCells().stream().filter(Cell::isEmpty).collect(Collectors.toCollection(ArrayList::new));
        } else {
            ArrayList<Cell> nearby_cells = new ArrayList<>();
            for (Cell cell : current_cell.getNearbyCells()) {
                if (cell.isEmpty()) nearby_cells.add(cell);
            }
            return nearby_cells;
        }
    }

    protected abstract String getBeastName();

    protected abstract void createChildren();

    protected Cell getCellForChild(ArrayList<Cell> nearby_cells) {
        if (nearby_cells.size() > 0) {
            Random random = new Random();
            int rand = random.nextInt(nearby_cells.size());
            return nearby_cells.get(rand);
        }
        return null;
    }

    protected void die() {
        beastListener.die();
    }


}
