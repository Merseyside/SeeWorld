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

public class Pinguin extends SeaBeast {

    @Inject
    Settings settings;
    public Pinguin(Cell cell) {
        super(cell);
        Application.getComponent().inject(this);
        bmp = settings.getPinguin_bmp();
    }

    public Cell makeTurn(){
        super.makeTurn();
        if (moves_made != 0 && moves_made % Constants.PINGUIN_TURNS == 0) {
            createChildren();
        }
        return current_cell;
    }

    @Override
    protected String getBeastName() {
        return Constants.PINGUIN;
    }

    protected void createChildren() {
        Cell cell = getCellForChild(getNearbyEmptyCells());
        if (cell != null) {
            Pinguin pinguin = new Pinguin(cell);
            cell.setBeast(pinguin);
            beastListener.child(pinguin);
        }
    }
}
