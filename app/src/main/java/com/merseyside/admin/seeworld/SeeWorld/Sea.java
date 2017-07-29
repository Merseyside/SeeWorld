package com.merseyside.admin.seeworld.SeeWorld;

import android.graphics.Canvas;

import com.merseyside.admin.seeworld.Constants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

/**
 * Created by ivan_ on 25.07.2017.
 */

public class Sea {

    public interface SeaInterface{
        void gameStarted();
        boolean beastsMoved();
    }

    private SeaInterface seaInterface;
    private int startX, startY;
    private Cell sea[][];
    private LinkedList<SeaBeast> beastsWaitingForTurn;

    public Sea(){
        sea = new Cell[Constants.ROW_COUNT][Constants.COL_COUNT];
        getTableStartCoords();
        createCells();
    }

    public void setSeaInterface(SeaInterface seaInterface){
        this.seaInterface = seaInterface;
    }

    private void setCoordsToCell(int i, int j, Cell cell) {
        cell.setCoords(startX + j * Constants.CELL_SIZE, startY + i * Constants.CELL_SIZE);
    }

    private void getTableStartCoords(){
        int tableWidth = Constants.CELL_SIZE * Constants.COL_COUNT;
        int tableHeight = Constants.CELL_SIZE * Constants.ROW_COUNT;
        if (tableWidth >= Constants.width) {
            Constants.CELL_SIZE = Constants.width / Constants.COL_COUNT;
            startX = 0;
            startY = 0;
        } else {
            startX = (Constants.width - tableWidth)/2;
            startY = ((int)(Constants.height * 0.85) - tableHeight) / 2;
        }
    }

    public void newGame(){
        clearAllCells();
        createPinguins();
        createSharks();
        seaInterface.gameStarted();
        while(true) {
            try {
                Thread.sleep(2000);
                newRound();
                if (seaInterface.beastsMoved()) return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void newRound() {
        getBeastList();
        ListIterator<SeaBeast> iterator = beastsWaitingForTurn.listIterator();
        SeaBeast beast;
        while (iterator.hasNext()) {
            beast = iterator.next();
            if (beast != null) beast.makeTurn();
        }
    }

    private void createPinguins() {
        int count = (Constants.COL_COUNT * Constants.ROW_COUNT)/2;
        createBeasts(count, Constants.PINGUIN);
    }

    private void createSharks(){
        int count = (int)((Constants.COL_COUNT * Constants.ROW_COUNT)*0.05);
        createBeasts(count, Constants.SHARK);
    }

    private void createBeasts(int count, String beast) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            while (true) {
                int rand = random.nextInt(Constants.ROW_COUNT * Constants.COL_COUNT);
                int row = rand / Constants.COL_COUNT;
                int col = rand % Constants.COL_COUNT;
                Cell cell = sea[row][col];
                if (cell.isEmpty()) {
                    SeaBeast seaBeast;
                    switch (beast){
                        case (Constants.PINGUIN) :
                            seaBeast = new Pinguin(cell);
                            break;
                        default:
                            seaBeast = new Shark(cell);
                            break;
                    }
                    setListenerToBeast(seaBeast);
                    cell.setBeast(seaBeast);
                    break;
                }
            }
        }
    }

    private void setListenerToBeast(SeaBeast beast) {
        beast.setBeastListener(new SeaBeast.BeastListener() {
            @Override
            public void stay() {

            }

            @Override
            public void move() {

            }

            @Override
            public void die() {
                int index = beastsWaitingForTurn.lastIndexOf(beast);
                if (index != -1) beastsWaitingForTurn.set(index, null);
            }

            @Override
            public void child(SeaBeast beast) {
                setListenerToBeast(beast);
            }
        });

    }

    private void clearAllCells(){
        for (int i = 0; i < Constants.ROW_COUNT; i++){
            for (int j = 0; j < Constants.COL_COUNT; j++) {
                sea[i][j].clean();
            }
        }
    }

    public void onDraw(Canvas canvas){
        for (int i = 0; i < Constants.ROW_COUNT; i++) {
            for (int j = 0; j < Constants.COL_COUNT; j++) {
                sea[i][j].onDraw(canvas);
            }
        }
    }

    private void createCells(){
        for (int i = 0; i < Constants.ROW_COUNT; i++){
            for (int j = 0; j < Constants.COL_COUNT; j++) {
                Cell cell = new Cell();
                setCoordsToCell(i, j, cell);
                sea[i][j] = cell;
            }
        }

        for (int i = 0; i < Constants.ROW_COUNT; i++){
            for (int j = 0; j < Constants.COL_COUNT; j++) {
                getNearbyCells(sea[i][j], i, j);
            }
        }
    }

    private void getNearbyCells(Cell cell, int row, int col) {
        ArrayList<Cell> nearby_cells = new ArrayList<>();
        for (int i = row - 1; i <= row + 1; i++){
            if (i >= 0 && i < Constants.ROW_COUNT) {
                for (int j = col - 1; j <= col + 1; j++) {
                    if (j >= 0 && j < Constants.COL_COUNT && !(i == row && j == col)) {
                        nearby_cells.add(sea[i][j]);
                    }
                }
            }
        }
        cell.setNearbyCells(nearby_cells);
    }

    private void getBeastList(){
        beastsWaitingForTurn = new LinkedList<>();
        for (int i = 0; i < Constants.ROW_COUNT; i++) {
            for (int j = 0; j < Constants.COL_COUNT; j++) {
                if (!sea[i][j].isEmpty()) beastsWaitingForTurn.add(sea[i][j].getBeast());
            }
        }

    }
}
