package xyz.holomek.domino;

import xyz.holomek.utils.UtilMath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
zdroj zakladu: https://stackoverflow.com/questions/17410168/how-to-show-pictures-in-jframe-using-java2d?answertab=trending#tab-top
 */
public class ImagePane extends JPanel {

    private ArrayList<Cell> cells = new ArrayList<Cell>();

    private BufferedImage img;

    public ImagePane() {
        this.addMouseListener(new Listener());
    }

    public void load() {
        if (img == null) return;
        int color = img.getRGB(438, 373);
        int red =   (color & 0x00ff0000) >> 16;
        int green = (color & 0x0000ff00) >> 8;
        int blue =   color & 0x000000ff;
        System.out.println("img: " + red + " g: " + green + " b " + blue);
        // nefungovalo to protoze jsem bral 8x8 misto 7x8 timpadem jsem ztratil 3 hodiny casu :))
        for (int rowX = 1; rowX <= 8; rowX++) {
            for (int rowY = 1; rowY <= 7; rowY++) {
                System.out.println("==================");

                int x = (int) Math.ceil((rowX - 1) * 60.25);
                int y = (int) Math.ceil(1 + (rowY - 1) * (double) ((img.getHeight()-1) / 7.0));

                ArrayList<Integer> checkColor = new ArrayList<Integer>();
                checkColor.add(img.getRGB((int) (x + (15 * 1)), (int) y + (15 * 1)));
                checkColor.add(img.getRGB((int) (x + (15 * 2)), (int) y + (15 * 1)));
                checkColor.add(img.getRGB((int) (x + (15 * 3)), (int) y + (15 * 1)));

                checkColor.add(img.getRGB((int) x + (15 * 1), (int) y + (15 * 3)));
                checkColor.add(img.getRGB((int) x + (15 * 2), (int) y + (15 * 3)));
                checkColor.add(img.getRGB((int) x + (15 * 3), (int) y + (15 * 3)));

                // varianta a kvuli otoceni
                int blackHolesA = checkBlackHole(checkColor);
                System.out.println("Holes A: " + blackHolesA);
                checkColor.clear();

                // varianta b kvuli otoceni
                checkColor.add(img.getRGB((int) (x + (15 * 1)), (int) y + (15 * 1)));
                checkColor.add(img.getRGB((int) (x + (15 * 1)), (int) y + (15 * 2)));
                checkColor.add(img.getRGB((int) (x + (15 * 1)), (int) y + (15 * 3)));

                checkColor.add(img.getRGB((int) (x + (15 * 3)), (int) y + (15 * 1)));
                checkColor.add(img.getRGB((int) (x + (15 * 3)), (int) y + (15 * 2)));
                checkColor.add(img.getRGB((int) (x + (15 * 3)), (int) y + (15 * 3)));

                int blackHolesB = checkBlackHole(checkColor);
                System.out.println("Holes B: " + blackHolesB);
                checkColor.clear();

                int up = 0;

                // kontrola prostredku
                checkColor.add(img.getRGB((int) (x + (15 * 2)), (int) (y + (15 * 2))));
                int blackHoles = checkBlackHole(checkColor);

                // varianta s tim ze by bylo 9 neexistuje
                if (blackHolesA < blackHolesB) blackHoles += blackHolesB;
                else blackHoles += blackHolesA;

                if (blackHoles == 6) {
                    if (blackHolesA == 6) {
                        up = 2;
                    } else {
                        up = 1;
                    }
                }

                System.out.println("Holes COMPLETE: " + blackHoles);

                Cell cell = new Cell(x, y, blackHoles, rowX, rowY, up);
                cells.add(cell);
            }
        }
        //int cellX = 0;
        /*for (double x = 0; x < img.getWidth(); x += 60.25) {
            int cellY = 0;
            for (double y = 1; y < img.getHeight(); y += ((img.getHeight()-1) / 8)) { //52.75
                System.out.println("Y " + y);
                ArrayList<Integer> checkColor = new ArrayList<Integer>();
                System.out.println("==================");
                System.out.println("X " + x);
                System.out.println( "new Cell :" + cellX + " - " + cellY + " x " + y + " " + " " + (x + 15*1) + " , " + (y + 15*1));
                checkColor.add(img.getRGB((int) (x + (15 * 1)), (int) y + (15 * 1)));
                checkColor.add(img.getRGB((int) (x + (15 * 2)), (int) y + (15 * 1)));
                checkColor.add(img.getRGB((int) (x + (15 * 3)), (int) y + (15 * 1)));

                checkColor.add(img.getRGB((int) x + (15 * 1), (int) y + (15 * 3)));
                checkColor.add(img.getRGB((int) x + (15 * 2), (int) y + (15 * 3)));
                checkColor.add(img.getRGB((int) x + (15 * 3), (int) y + (15 * 3)));

                // varianta a kvuli otoceni
                int blackHolesA = checkBlackHole(checkColor);
                System.out.println("Holes A: " + blackHolesA);
                checkColor.clear();

                // varianta b kvuli otoceni
                checkColor.add(img.getRGB((int) (x + (15 * 1)), (int) y + (15 * 1)));
                checkColor.add(img.getRGB((int) (x + (15 * 1)), (int) y + (15 * 2)));
                checkColor.add(img.getRGB((int) (x + (15 * 1)), (int) y + (15 * 3)));

                checkColor.add(img.getRGB((int) (x + (15 * 3)), (int) y + (15 * 1)));
                checkColor.add(img.getRGB((int) (x + (15 * 3)), (int) y + (15 * 2)));
                checkColor.add(img.getRGB((int) (x + (15 * 3)), (int) y + (15 * 3)));

                int blackHolesB = checkBlackHole(checkColor);
                System.out.println("Holes B: " + blackHolesB);
                checkColor.clear();

                // kontrola prostredku
                checkColor.add(img.getRGB((int) (x + (15 * 2)), (int) (y + (15 * 2))));
                int blackHoles = checkBlackHole(checkColor);

                // varianta s tim ze by bylo 9 neexistuje
                if (blackHolesA < blackHolesB) blackHoles += blackHolesB;
                else blackHoles += blackHolesA;

                System.out.println("Holes COMPLETE: " + blackHoles);

                Cell cell = new Cell(x, y, blackHoles, cellX, cellY);
                cells.add(cell);
                cellY += 1;
            }
            cellX += 1;
        }*/
        System.out.println("Total cells:" + cells.size());
    }

    private int checkBlackHole(ArrayList<Integer> checkColor) {
        int holes = 0;
        for (int color : checkColor) {
            //https://stackoverflow.com/questions/22391353/get-color-of-each-pixel-of-an-image-using-bufferedimages
            int red =   (color & 0x00ff0000) >> 16;
            int green = (color & 0x0000ff00) >> 8;
            int blue =   color & 0x000000ff;
            if (red < 50 && green < 50 && blue < 50) {
                holes += 1;
            }
            //System.out.println("Color red, green, blue: " + red + ", " + green + "," + blue);
        }
        return holes;
    }

    /**
     * If the new image is different from the old image, set the new image to the old image and repaint the component.
     *
     * @param value The new image to be displayed.
     */
    public void setImg(BufferedImage value) {
        if (img != value) {
            img = value;
            repaint();
        }
    }

    /*
    Returning img
     */
    public BufferedImage getImg() {
        return img;
    }

    /**
     * If the image is null, return a default size, otherwise return the size of the image.
     *
     * @return The preferred size of the image.
     */
    @Override
    public Dimension getPreferredSize() {
        BufferedImage img = getImg();
        return img == null ? new Dimension(200, 200) : new Dimension(img.getWidth(), img.getHeight());
    }

    /**
     * "Draw the image at the center of the panel."
     *
     * The first line of the function is a call to the superclass's paintComponent function. This is a good practice to
     * follow
     *
     * @param g The Graphics object to draw on.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        BufferedImage img = getImg();
        if (img != null) {
            int x = (getWidth() - img.getWidth()) / 2;
            int y = (getHeight()- img.getHeight()) / 2;

            g2d.drawImage(img, x, y, this);
        }
        g2d.dispose();
    }

    public ArrayList<Cell> getCellByXY(int x, int y) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (Cell c : this.cells) {
            if (UtilMath.isInArea(x, y, (int) c.x - 5, (int) c.y - 5, (int) c.x + 65, (int) c.y + 65))
                cells.add(c);
        }
        return cells;
    }

    public boolean combination(Cell c, Cell c2) {
        for (Cell cell : cells) {
            if (cell.claimed != null) {
                if (cell.count == c.count && cell.claimed.count == c2.count) {
                    return true;
                } else if (cell.count == c2.count && cell.claimed.count == c.count) {
                    return true;
                }
            }
        }
        return false;
    }

    public class Listener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX(), y = e.getY();
            System.out.println("XY : " + x + " - " + y);
            ArrayList<Cell> cells = getCellByXY(x, y);
            if (cells.size() == 0) {
                System.out.println("Nebylo nic nalezeno");
                return;
            }
            Cell cell1 = cells.get(0);
            System.out.println(" . " + cell1.rowX + " - " + cell1.rowY);
            System.out.println("Nalezeno " + cells.size());
            if (cells.size() != 2) {
                System.out.println("Nalezeno vice nebo mene jak dve policek skipuju");
                return;
            }
            Cell cell2 = cells.get(1);
            if (cell2.claimed != null || cell1.claimed != null) {
                System.out.println("Claimnute je neco, jdu to odstranit");
                if (cell1.claimed != null) {
                    cell1.claimed.claimed = null;
                    cell1.claimed = null;
                }
                if (cell2.claimed != null) {
                    cell2.claimed.claimed = null;
                    cell2.claimed = null;
                }
                return;
            }

            if (cell1.up != 0 || cell2.up != 0) {
                int up1 = cell1.up, up2 = cell2.up;
                int smer = cell1.smerCislo(cell2);
                if (up1 != 0)
                    if (smer != up1) {
                        System.out.println("Bacha na smery");
                        return;
                    }
                if (up2 != 0)
                    if (smer != up2) {
                        System.out.println("Bacha na smery");
                        return;
                    }
            }

            if (combination(cell1, cell2)) {
                System.out.println("Tato kombinace je jiz obsazena");
                return;
            }

            System.out.println("Cell1 " + cell1.rowX + " - " + cell1.rowY + " = " + cell1.count);
            System.out.println("Cell2 " + cell2.rowX + " - " + cell2.rowY + " = " + cell2.count);

            cell1.claimed = cell2;
            cell2.claimed = cell1;
            cell1.smer(cell2);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public void drawRect(double x, double y, int i, int i1, Color color) {
        Graphics2D graphics = img.createGraphics();
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke(3));
        graphics.drawRect((int) x, (int) y, i, i1);
        repaint();
    }

}