package com.pacman.environment;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.HeadlessException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.pacman.characters.*;

/**
 * GeneralEnvironment class extends the parent class JFrame
 * to inherit the parents class' attributes
 * 
 */
public class GeneralEnvinronment extends JFrame {

    /** Maze */
    private Maze MazeVirtual;
    private JLabel[][] MazeGraphic;
    private PanelState estado;

    /**AudioClip start audio;*/
    private final AudioClip start = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sounds/Start.wav"));

    /** Characters */
    private Pacman pacman;
    private Ghost f1, f2, f3, f4, f5;

    /** Lives and score panel */
    private JLabel[] vidas;
    private JLabel score;
    private JLabel[] frutas;
    private JPanel panel;
    private JPanel panel2;
    private JPanel panel3;

    /**
     * Generates the graphic maze
     * @param numMaze
     * @param numLives
     * @param numScore
     * @throws HeadlessException
     */
    public GeneralEnvinronment(int numMaze, int numLives, int numScore) throws HeadlessException {
        try {
            // configure screen and instantiate graphic panels
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(450, 450);
            this.setLayout(null);
            this.setBounds(0, 0, 416, 470);
            this.setTitle("Pacman");
            setVisible(true);

            vidas = new JLabel[7];
            score = new JLabel(Integer.toString(numScore));
            frutas = new JLabel[3];
            panel = new JPanel();
            panel2 = new JPanel();
            panel3 = new JPanel();

            this.setLocationRelativeTo(null);

            //  Generate the graphic maze from the virtual Maze
            MazeVirtual = new Maze(numMaze);
            MazeGraphic = new JLabel[MazeVirtual.ReturnAmountofColumnsMaze()][MazeVirtual.ReturnAmountofRowsMaze()];

            // Instantiate Pacman and the Ghosts
            pacman = new Pacman(numLives, 18, 28, MazeGraphic, MazeVirtual, numMaze, numScore);
            f1 = new Ghost(1, pacman, MazeVirtual, MazeGraphic, 16, 16, 80, numMaze);
            f2 = new Ghost(2, pacman, MazeVirtual, MazeGraphic, 18, 16, 80, numMaze);
            f3 = new Ghost(3, pacman, MazeVirtual, MazeGraphic, 20, 16, 80, numMaze);
            f4 = new Ghost(4, pacman, MazeVirtual, MazeGraphic, 18, 14, 80, numMaze);
            f5 = new Ghost(5, pacman, MazeVirtual, MazeGraphic, 14, 14, 80, numMaze);

            GenerarMazeGraphic(numMaze);
            estado = new PanelState(pacman, f1, f2, f3, f4, f5, vidas, frutas, MazeVirtual, MazeGraphic, this);
            start.play();
            Thread.sleep(4000);

            estado.siren();
            pacman.Initialize();
            ghostStart();
            estado.start();
        } catch (InterruptedException ex) {
            Logger.getLogger(GeneralEnvinronment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void GenerarMazeGraphic(int n) {

        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case 37:
                        pacman.MovLeft();
                        break;
                    case 39:
                        pacman.MovRight();
                        break;
                    case 38:
                        pacman.MovUp();
                        break;
                    case 40:
                        pacman.MovDown();
                        break;
                }

                score.setText(Integer.toString(pacman.getScore()));

            }
        });

        panel.setBounds(0, 400, 150, 80);
        panel2.setBounds(150, 400, 100, 80);
        panel3.setBounds(250, 400, 150, 80);

        panel.setBackground(Color.BLACK);
        panel2.setBackground(Color.BLACK);
        panel3.setBackground(Color.BLACK);
        score.setForeground(Color.WHITE);
        setBackground(Color.BLACK);

        this.getContentPane().add(panel);
        this.getContentPane().add(panel2);
        this.getContentPane().add(panel3);

        panel2.add(score);
         //Load Panel
        for (int i = 0; i < 7; i++) {
            vidas[i] = new JLabel();
            panel.add(vidas[i]);
            vidas[i].setBounds(20 * i, 0, 20, 20);
            vidas[i].validate();
            if (i < 3) {
                frutas[i] = new JLabel();
                panel3.add(frutas[i]);
                frutas[i].setBounds(250 + (20 * i), 0, 20, 20);
                frutas[i].validate();
            }
        }
        //Load Maze
        for (int i = 0; i < MazeVirtual.ReturnAmountofColumnsMaze(); i++) {
            for (int j = 0; j < MazeVirtual.ReturnAmountofRowsMaze(); j++) {
                MazeGraphic[i][j] = new JLabel();
                add(MazeGraphic[i][j]);
                MazeGraphic[i][j].setIcon(new ImageIcon("Graphics/" + n + "/Maze/" + MazeVirtual.ReturnMazeImageCode(i, j) + ".gif"));
                if (MazeVirtual.MazeActual()[i][j] != "1" && MazeVirtual.MazeActual()[i][j] != "2" && MazeVirtual.MazeActual()[i][j] != "3" && MazeVirtual.MazeActual()[i][j] != "4") {
                    MazeGraphic[i][j].setOpaque(true);
                } else {
                    MazeGraphic[i][j].setOpaque(false);
                }

            }
        }

        for (int i = 0; i < MazeVirtual.ReturnAmountofColumnsMaze(); i++) {
            for (int j = 0; j < MazeVirtual.ReturnAmountofRowsMaze(); j++) {
                MazeGraphic[i][j].setBounds(i * MazeVirtual.ReturnLengthImages(), j * MazeVirtual.ReturnHeightImages(), MazeVirtual.ReturnLengthImages(), MazeVirtual.ReturnHeightImages());
                MazeGraphic[i][j].validate();
            }
        }

    }

    private void ghostStart() {
        f1.start();
        f2.start();
        f3.start();
        f4.start();
        f5.start();
    }

}
