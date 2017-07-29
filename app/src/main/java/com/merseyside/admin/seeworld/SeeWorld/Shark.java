package com.merseyside.admin.seeworld.SeeWorld;

import com.merseyside.admin.seeworld.Application;
import com.merseyside.admin.seeworld.Constants;
import com.merseyside.admin.seeworld.Settings;

import java.util.ArrayList;
import java.util.Random;


import javax.inject.Inject;

/**
 * Created by ivan_ on 25.07.2017.
 */

public class Shark extends SeaBeast {

    @Inject
    Settings settings;
    private int moves_without_eating;
    public Shark(Cell cell) {
        super(cell);
        Application.getComponent().inject(this);
        bmp = settings.getShark_bmp();
    }

    @Override
    protected String getBeastName() {
        return Constants.SHARK;
    }

    @Override
    protected void createChildren() {
        Cell cell = getCellForChild(getNearbyEmptyCells());
        if (cell != null) {
            Shark shark = new Shark(cell);
            cell.setBeast(shark);
            beastListener.child(shark);
        }
    }

    protected ArrayList<Cell> getNearbyCellsWithPinguins() {
        ArrayList<Cell> pinguin_cells = new ArrayList<>();
        for (Cell cell : current_cell.getNearbyCells()) {
            if (!cell.isEmpty() && cell.getBeastName().equals(Constants.PINGUIN)) pinguin_cells.add(cell);
        }
        //current_cell.getNearbyCells().stream().filter(cell -> !cell.isEmpty() && cell.getBeastName().equals(Constants.PINGUIN)).forEach(pinguin_cells::add);
        return pinguin_cells;
    }

    @Override
    public Cell makeTurn() {
        ArrayList<Cell> pinguin_cells = getNearbyCellsWithPinguins();
        if (pinguin_cells.size() == 0) {
            super.makeTurn();
            moves_without_eating++;
            if (moves_without_eating == Constants.SHARK_TURNS_BEFORE_DIE) {
                current_cell.clean();
                die();
                return null;
            }
        } else {
            eatPinguin(pinguin_cells);
        }
        if (moves_made % Constants.SHARK_TURNS == 0) {
            createChildren();
        }
        return current_cell;
    }

    private void eatPinguin(ArrayList<Cell> pinguin_cells) {
        Random random = new Random();
        int rand = random.nextInt(pinguin_cells.size());
        Cell cell = pinguin_cells.get(rand);
        current_cell.clean();
        current_cell = cell;
        current_cell.setBeast(this);
        moves_without_eating = 0;
        moves_made++;
    }
}
