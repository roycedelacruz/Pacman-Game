package com.pacman.environment;

import java.applet.AudioClip;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.pacman.characters.Ghost;
import com.pacman.characters.Pacman;
import com.pacman.menu.Menu;

class PanelState extends Thread {
    private final AudioClip gameO = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sounds/GameOver.wav"));
    private final AudioClip newlev = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sounds/NewLev.wav"));
    private final AudioClip siren = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sounds/Siren.wav"));
    private int nfrut;
    private boolean active;
    private final Pacman pacman;
    private final Ghost f1, f2, f3, f4, f5;
    private final JLabel[] lives;
    private final JLabel[] frut;
    private final Maze MazeVirtual;
    private final JLabel[][] MazeGraphic;
    private final JFrame frame;

    public PanelState(Pacman pacman, Ghost f1, Ghost f2, Ghost f3, Ghost f4, Ghost f5, JLabel[] lives, JLabel[] frut, Maze lab, JLabel[][] graphic, JFrame frame) {
        active = true;
        this.pacman = pacman;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
        this.f5 = f5;
        this.lives = lives;
        this.frut = frut;
        this.MazeVirtual = lab;
        this.MazeGraphic = graphic;
        this.frame = frame;
        this.nfrut = 3;
    }

    @Override
    public void run() {

        LoadLives();
        LoadF();
        int v = pacman.getLives();
        int s = pacman.getScore();
        frame.repaint();
        while (active) {
            boolean ban = false;

            if (v != pacman.getLives()) {

                ResetGhost();
                LoadLives();
                frame.repaint();

            }
            if (s != pacman.getScore()) {
                if (pacman.getScore() == 200 || pacman.getScore() == 300 || pacman.getScore() == 500 || pacman.getScore() == 700 || pacman.getScore() == 900) {

                    for (int i = 10; i < 200; i++) {
                        for (int j = 10; j < 200; j++) {
                            if (MazeVirtual.MazeActual()[i][j] == "CA" && MazeVirtual.MazeActual()[i + 1][j] == "CA" && MazeVirtual.MazeActual()[i][j + 1] == "CA" && MazeVirtual.MazeActual()[i + 1][j + 1] == "CA") {
                                AddF(i, j);
                                ban = true;
                                break;
                            }
                        }
                        if (ban == true) {
                            break;
                        }
                    }
                }

            }

            if (pacman.getLives() == 0) {
                //Lose----------------------  
                siren.stop();
                gameO.play();
                StopGhost();
                
                this.active = false;
                JOptionPane.showMessageDialog(null, "You lose");
                frame.dispose();
                frame.remove(frame);
                new Menu();
                
            } else if (Win() == true) {

                StopGhost();
                siren.stop();
                frame.dispose();
                frame.remove(frame);
                this.active = false;

                if (Maze.n == 2) {
                    // Win
                    JOptionPane.showMessageDialog(null, "Congratulations, you win!----");
                    frame.dispose();
                    frame.remove(frame);
                    new Menu();
                    
                } else {
                    //Next Level----------------------  

                    newlev.play();

                    new GeneralEnvinronment(Maze.n + 1, pacman.getLives(), pacman.getScore());

                }
            }

            v = pacman.getLives();
            s = pacman.getScore();

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(PanelState.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void setActivo(boolean active) {
        this.active = active;
    }
/**
 * Resets the ghosts once Pacman loses a life
 */
    public void ResetGhost() {
        f1.Vanish();
        f2.Vanish();
        f3.Vanish();
        f4.Vanish();
        f5.Vanish();
        f1.Initialize();
        f2.Initialize();
        f3.Initialize();
        f4.Initialize();
        f5.Initialize();

    }
/**
 * Removes the ghost once the game is over 
 */
    private void StopGhost() {
        f1.setLife(false);
        f2.setLife(false);
        f3.setLife(false);
        f4.setLife(false);
        f5.setLife(false);
    }
/**
 * Checks if there is no more for Pacman to eat and achieve win
 * @return a boolean, returns false if n is not equal to 1584, returns true otherwise.
 */
    private boolean Win() {
        int n = 0;
        for (int i = 0; i < MazeVirtual.ReturnAmountofColumnsMaze(); i++) {
            for (int j = 0; j < MazeVirtual.ReturnAmountofRowsMaze(); j++) {

                if ((MazeVirtual.MazeFinal()[i][j] == MazeGraphic[i][j].isOpaque()) && MazeVirtual.MazeActual()[i][j] != "1" && MazeVirtual.MazeActual()[i][j] != "2" && MazeVirtual.MazeActual()[i][j] != "3" && MazeVirtual.MazeActual()[i][j] != "4") {
                    n++;
                }
            }

        }
        return n == 1584;

    }
/**
 * Loads the lives graphic to the game
 */
    public void LoadLives() {
        for (JLabel life : lives) {
            life.setIcon(null);
        }
        for (int i = 0; i < pacman.getLives(); i++) {
            lives[i].setIcon(new ImageIcon("Graphics/P.png"));
        }
    }
/**
 * Loads the fruit images to the graphics
 */
    public void LoadF() {
        for (JLabel frut1 : frut) {
            frut1.setIcon(null);
        }

        if (nfrut == 3) {
            frut[0].setIcon(new ImageIcon("Graphics/frut/apple.png"));
            frut[1].setIcon(new ImageIcon("Graphics/frut/cherry.png"));
            frut[2].setIcon(new ImageIcon("Graphics/frut/orange.png"));
        } else if (nfrut == 2) {
            frut[0].setIcon(new ImageIcon("Graphics/frut/apple.png"));
            frut[1].setIcon(new ImageIcon("Graphics/frut/cherry.png"));
        } else if (nfrut == 1) {
            frut[0].setIcon(new ImageIcon("Graphics/frut/apple.png"));
        }

    }
/**
 * loops the siren audio
 */
    public void siren() {
        siren.loop();
    }
/**
 * Adds fruit to the Game
 * @param x the x coordinate of the maze
 * @param y the y coordinate of the maze
 */
    public void AddF(int x, int y) {
        int mu = 1;
        for (int j = y; j < y + 2; j++) {
            for (int i = x; i < x + 2; i++) {

                switch (nfrut) {
                    case 1:
                        MazeGraphic[i][j].setIcon(new ImageIcon("Graphics/" + Maze.n + "/Fruits/apple_0" + mu + ".png"));
                        MazeVirtual.MazeActual()[i][j] = "a" + mu;
                        break;
                    case 2:
                        MazeGraphic[i][j].setIcon(new ImageIcon("Graphics/" + Maze.n + "/Fruits/cherry_0" + mu + ".png"));
                        MazeVirtual.MazeActual()[i][j] = "c" + mu;
                        break;
                    case 3:
                        MazeGraphic[i][j].setIcon(new ImageIcon("Graphics/" + Maze.n + "/Fruits/orange_0" + mu + ".png"));
                        MazeVirtual.MazeActual()[i][j] = "o" + mu;
                        break;
                }
                mu++;
            }

        }
        nfrut--;
        LoadF();
    }



}
