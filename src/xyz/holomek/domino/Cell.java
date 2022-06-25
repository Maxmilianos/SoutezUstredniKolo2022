package xyz.holomek.domino;

import java.awt.*;

/*
jdou po sobe od 60,25 - y 52,75 minus ten jeden prvni pixel, mezera mezi teckama cca 15px
60.25 - 60 - brzo jsem si vsiml ze je to 7x8 misto 8x8 kdyz jsem si toho vsiml 12.25
 */
public class Cell {

    // startovni
    public double x, y;

    // pocet tecek
    public int count, rowX, rowY;

    // 0 = nic, 1 = nahoru, dolu, 2 = doprava, doleva
    public int up;

    public Cell claimed = null;

    public Cell(double x, double y, int count, int rowX, int rowY, int up) {
        this.x = x;
        this.y = y;
        this.count = count;
        this.rowX = rowX;
        this.rowY = rowY;
        this.up = up;
    }

    /*
    true = pod nim
    false = napravo
     */
    public void changeColor(Cell cell, boolean smer) {
        if (smer) { // pod nim
            Domino.instance.imagePane.drawRect(x + 1, y + 1, 60 - 2, 120 - 2, Color.BLUE);
        } else {
            Domino.instance.imagePane.drawRect(x + 1, y + 1, 120 - 2, 60 - 2, Color.RED);
        }
    }

    public void smer(Cell cell) {
        if (cell.rowX == rowX) {
            // ve stejnem sloupci
            if (cell.rowY > rowY) {
                // je pod nim
                changeColor(cell, true);
            } else {
                // je nad nim
                cell.changeColor(this, true);
            }
        } else if (cell.rowY == rowY){
            // na stejnem radku
            if (cell.rowX > rowX) {
                // napravo
                changeColor(cell, false);
            } else {
                cell.changeColor(this, false);
            }
        } else {
            System.out.println("chyba chyba");
        }
    }
    public int smerCislo(Cell cell) {
        if (cell.rowX == rowX) {
            return 1; // pod nad
        } else if (cell.rowY == rowY){
            return 2; // vedle sebe
        } else {
            System.out.println("chyba chyba");
            return -1;
        }
    }
}
