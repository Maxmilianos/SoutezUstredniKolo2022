package xyz.holomek.domino;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Domino {

    public static Domino instance;

    private JFrame frame;

    private File file;

    private int sizeX, sizeY;

    public ImagePane imagePane;

    public Domino(File file) {
        instance = this;
        this.file = file;
        this.sizeX = 500;
        this.sizeY = 800;
        init();
    }

    public void init() {
        frame = new JFrame();
        frame.setTitle("Piskvorky");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // center vertikalne aj horizontalne
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(screenSize.width / 2 - sizeX / 2, screenSize.height / 2 - sizeY / 2, sizeX, sizeY + 25);

        //frame.setLayout(new BorderLayout());
        frame.setLayout(null);


        { // funkce z https://stackoverflow.com/questions/17410168/how-to-show-pictures-in-jframe-using-java2d?answertab=trending#tab-top
            imagePane = new ImagePane();
            try {
                imagePane.setImg(ImageIO.read(file));
            } catch (IOException e) {
                System.out.println("nastala chyba - " + e.getMessage());
                frame.dispose();
                return;
            }
            System.out.println("Width: " + imagePane.getImg().getWidth() + " height:" + imagePane.getImg().getHeight());
            imagePane.setBounds(sizeX / 2 - imagePane.getImg().getWidth() / 2, 10, imagePane.getImg().getWidth(), imagePane.getImg().getHeight());
            imagePane.load();
            frame.add(imagePane);
        }
        {
            JButton button = new JButton("Nápověda");
            button.setFont(new Font("Dialog", Font.BOLD, 18));
            button.setBounds(40, 630, 200, 35);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                }
            });
            frame.add(button);
        }
        {
            JButton button = new JButton("Dokončit");
            button.setFont(new Font("Dialog", Font.BOLD, 18));
            button.setBounds(280, 630, 200, 35);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    finish();
                }
            });
            frame.add(button);
        }



        frame.setVisible(true);
    }

    public void finish() {
        System.out.println("Snaha o vyreseni..");
        imagePane.draw = false;
        ArrayList<Cell> cells = imagePane.cells;
        while (cells.size() != 0) {
            
        }
    }

}
