package com.pacman.characters;

import java.applet.AudioClip;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.pacman.environment.Maze;

/**
 * creates public class Ghost which
 * extends parent class Thread
 * @author Royce de la Cruz
 *
 */
public class Ghost extends Thread {

    private final AudioClip Stop = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sounds/Stop.wav"));
    private final AudioClip gameO = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sounds/GameOver.wav"));
    private final AudioClip perse = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sounds/Persecucion.wav"));
    private final AudioClip eat = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sounds/eatghost.wav"));
    private int n, nu;
    private int x, y;
    private int dx, dy;
    private int tiempo, dtiempo;
    private Pacman pacman;
    private Maze lab;
    private JLabel[][] environment;
    private int mov;
    private boolean active;
    private boolean life;
    private boolean ban;

    public Ghost(int n, Pacman pac, Maze lab, JLabel[][] environment, int x, int y, int timer, int nu) {
        this.n = n;
        this.dx = x;
        this.dy = y;
        this.pacman = pac;
        this.lab = lab;
        this.environment = environment;
        this.dtiempo = timer;
        this.nu = nu;
        this.life = true;
        this.mov = 0;

    }

    /**
     * This method overrides run. This is called when the moving
     * happens.
     *
     */
    @Override
    public void run() {

        Initialize();
        try {
            MoverseAl();
        } catch (InterruptedException ex) {
            Logger.getLogger(Ghost.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Initializes Ghosts
     */
    public void Initialize() {
        active = false;
        int l = 1;
        tiempo = dtiempo;

        x = dx;
        y = dy;
        for (int i = y; i < y + 2; i++) {
            for (int j = x; j < x + 2; j++) {

                Position(j, i, l, true);
                l++;
            }
        }
    }
    
    /**
     * Handles how the ghosts move using random movements
     * @throws InterruptedException
     */
    private void MoverseAl() throws InterruptedException {
        int direction;
        Random rnd = new Random();
        ban = true;
        while (life) {
            Active();
            if (Locate() == false) {
                direction = (int) (rnd.nextDouble() * 4 + 1);
                switch (direction) {
                    case 1:
                        MovRight();
                        break;
                    case 2:
                        MovLeft();
                        break;
                    case 3:
                        MovUp();
                        break;
                    case 4:
                        MovDown();
                        break;
                }
            }

            Check();
            Thread.sleep(tiempo);
        }

    }

    /**
     * Checks if ghosts are Active
     * @return false if ghosts are not active, true otherwise
     */
    public void Active() {
        if (pacman.isActive() == false && ban == true) {
            active = true;
            ban = false;
            perse.loop();
        }
        if (active == true) {
            mov++;
            if (tiempo > 40) {
                tiempo--;
            }
            if (mov == 450) {
                active = false;
                mov = 0;
                ban = true;
                pacman.setActive(true);
                this.tiempo = this.dtiempo;
            }
        }
    }

    /**
     * Moves ghosts to the right
     * Erases the Picture of the maze to replace it with the image of ghosts
     */
    public void MovRight() {
        int l = 1;
        boolean path[];
        path = valid(x + 2, y, true);
        if (path[0] == true) {

            for (int i = y; i < y + 2; i++) {

                Erase(x, i);

                for (int j = x + 1; j < x + 3; j++) {

                    Position(j, i, l, true);

                    l++;
                }
            }
            x++;
        } else if (path[1] == true) {
            MuRight();
        }
    }

    /**
     * Moves ghosts to the left
     * Erases the Picture of the maze to replace it with the image of ghosts
     */
    public void MovLeft() {
        int l = 1;
        boolean path[];
        path = valid(x - 1, y, true);
        if (path[0] == true) {
            for (int i = y; i < y + 2; i++) {
                Erase(x + 1, i);

                for (int j = x - 1; j < x + 1; j++) {

                    Position(j, i, l, true);

                    l++;
                }
            }

            x--;
        } else if (path[1] == true) {
            MuLeft();
        }

    }

    /**
     * Moves ghosts above or up
     * Erases the Picture of the maze to replace it with the image of ghosts
     */
    public void MovUp() {
        int l = 1;
        boolean path[];
        path = valid(x, y - 1, false);
        if (path[0] == true) {
            for (int j = x; j < x + 2; j++) {
                Erase(j, y + 1);

                for (int i = y - 1; i < y + 1; i++) {

                    Position(j, i, l, false);

                    l++;
                }
            }

            y--;

        } else if (path[1] == true) {
            MuUp();
        }

    }

    /**
     * Moves ghosts below or down
     * Erases the Picture of the maze to replace it with the image of ghosts
     */
    public void MovDown() {
        int l = 1;
        boolean path[];
        path = valid(x, y + 2, false);
        if (path[0] == true) {
            for (int j = x; j < x + 2; j++) {
                Erase(j, y);

                for (int i = y + 1; i < y + 3; i++) {

                    Position(j, i, l, false);

                    l++;
                }
            }

            y++;
        } else if (path[1] == true) {
            MuDown();
        }
    }

    private boolean[] valid(int x, int y, boolean b) {
        boolean[] flag = {false, false};
        if (b == true) {
            if (x == lab.ReturnAmountofColumnsMaze() || x == -1) {
                flag[1] = true;
            } else if (lab.MazeActual()[x][y] == "CA" && lab.MazeActual()[x][y + 1] == "CA" || lab.MazeActual()[x][y] == "1" || lab.MazeActual()[x][y] == "2" || lab.MazeActual()[x][y] == "3" || lab.MazeActual()[x][y] == "4" || lab.MazeActual()[x][y] == "a1" || lab.MazeActual()[x][y] == "a2" || lab.MazeActual()[x][y] == "a3" || lab.MazeActual()[x][y] == "a4" || lab.MazeActual()[x][y] == "c1" || lab.MazeActual()[x][y] == "c2" || lab.MazeActual()[x][y] == "c3" || lab.MazeActual()[x][y] == "c4" || lab.MazeActual()[x][y] == "o1" || lab.MazeActual()[x][y] == "o2" || lab.MazeActual()[x][y] == "o3" || lab.MazeActual()[x][y] == "o4") {
                flag[0] = true;
            }

        } else {
            if (y == lab.ReturnAmountofRowsMaze() || y == -1) {
                flag[1] = true;
            } else if (lab.MazeActual()[x][y] == "CA" && lab.MazeActual()[x + 1][y] == "CA" || lab.MazeActual()[x][y] == "1" || lab.MazeActual()[x][y] == "2" || lab.MazeActual()[x][y] == "3" || lab.MazeActual()[x][y] == "4" || lab.MazeActual()[x][y] == "a1" || lab.MazeActual()[x][y] == "a2" || lab.MazeActual()[x][y] == "a3" || lab.MazeActual()[x][y] == "a4" || lab.MazeActual()[x][y] == "c1" || lab.MazeActual()[x][y] == "c2" || lab.MazeActual()[x][y] == "c3" || lab.MazeActual()[x][y] == "c4" || lab.MazeActual()[x][y] == "o1" || lab.MazeActual()[x][y] == "o2" || lab.MazeActual()[x][y] == "o3" || lab.MazeActual()[x][y] == "o4") {
                flag[0] = true;
            }

        }
        return flag;
    }


    public void MuRight() {
        int l = 1;

        for (int i = y; i < y + 2; i++) {
            for (int j = x; j < x + 2; j++) {

                Erase(j, i);
            }

            for (int j = 0; j < 2; j++) {

                Position(j, i, l, true);

                l++;

            }
        }

        x = 0;
    }

    public void MuLeft() {
        int l = 1;
        for (int i = y; i < y + 2; i++) {
            for (int j = 0; j < 2; j++) {
                Erase(j, i);
            }

            for (int j = environment[0].length - 2; j < environment[0].length; j++) {

                Position(j, i, l, true);

                l++;

            }
        }
        x = environment[0].length - 2;
    }

    public void MuUp() {
        int l = 1;
        for (int j = x; j < x + 2; j++) {
            for (int i = 0; i < 2; i++) {
                Erase(j, i);
            }

            for (int i = environment.length - 2; i < environment.length; i++) {

                Position(j, i, l, false);

                l++;
            }
        }
        y = environment.length - 2;

    }

    public void MuDown() {
        int l = 1;
        for (int j = x; j < x + 2; j++) {
            for (int i = environment.length - 2; i < environment.length; i++) {
                Erase(j, i);
            }

            for (int i = 0; i < 2; i++) {

                Position(j, i, l, false);

                l++;
            }
        }
        y = 0;

    }

    /**
     * Erases the image of the ghost once it moves to another
     * location and replace it with the image of the maze
     * to generate information
     * @param i for the row
     * @param j for the column
     */
    public void Erase(int i, int j) {
        if (lab.MazeActual()[i][j] == "1") {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/" + "1" + ".gif"));
        } else if (lab.MazeActual()[i][j] == "2") {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/" + "2" + ".gif"));
        } else if (lab.MazeActual()[i][j] == "3") {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/" + "3" + ".gif"));
        } else if (lab.MazeActual()[i][j] == "4") {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/" + "4" + ".gif"));
        } else if (lab.MazeActual()[i][j] == "a1") {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/Frutas/" + "apple_01" + ".png"));
        } else if (lab.MazeActual()[i][j] == "a2") {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/Frutas/" + "apple_02" + ".png"));
        } else if (lab.MazeActual()[i][j] == "a3") {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/Frutas/" + "apple_03" + ".png"));
        } else if (lab.MazeActual()[i][j] == "a4") {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/Frutas/" + "apple_04" + ".png"));
        } else if (lab.MazeActual()[i][j] == "c1") {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/Frutas/" + "cherry_01" + ".png"));
        } else if (lab.MazeActual()[i][j] == "c2") {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/Frutas/" + "cherry_02" + ".png"));
        } else if (lab.MazeActual()[i][j] == "c3") {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/Frutas/" + "cherry_03" + ".png"));
        } else if (lab.MazeActual()[i][j] == "c4") {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/Frutas/" + "cherry_04" + ".png"));
        } else if (lab.MazeActual()[i][j] == "o1") {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/Frutas/" + "orange_01" + ".png"));
        } else if (lab.MazeActual()[i][j] == "o2") {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/Frutas/" + "orange_02" + ".png"));
        } else if (lab.MazeActual()[i][j] == "o3") {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/Frutas/" + "orange_03" + ".png"));
        } else if (lab.MazeActual()[i][j] == "o4") {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/Frutas/" + "orange_04" + ".png"));
        } else if (environment[i][j].isOpaque() == true) {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/" + "CA" + ".gif"));
        } else if (environment[i][j].isOpaque() == false) {
            environment[i][j].setIcon(new ImageIcon("Graphics/" + nu + "/Maze/" + "CB" + ".gif"));
        }

    }

    /**
     * Tracking Pacman
     * @return
     * @throws InterruptedException
     */
    private boolean Locate() throws InterruptedException {
        int run;

        boolean[] mov = {false, false, false, false, false};
        if (pacman.getY() == y) {
            if (pacman.getX() <= x) {
                for (int i = pacman.getX(); i <= x; i++) {

                    if (lab.MazeActual()[i][y] != "CA") {
                        break;
                    } else if (i == x) {
                        run = x - pacman.getX();
                        for (int j = 0; j < run; j++) {
                            Active();
                            if (this.active == true) {
                                if (x + 2 == lab.ReturnAmountofColumnsMaze()) {
                                    MuRight();
                                } else if (lab.MazeActual()[x + 2][y] == "CA") {
                                    MovRight();

                                } else if (lab.MazeActual()[x][y - 1] == "CA") {
                                    MovUp();
                                } else if (lab.MazeActual()[x][y + 2] == "CA") {
                                    MovDown();
                                }
                                return false;
                            } else {
                                MovLeft();
                            }

                            Check();
                            Thread.sleep(140);
                        }

                        return true;
                    }

                }
            } else {
                for (int i = x; i <= pacman.getX(); i++) {
                    if (lab.MazeActual()[i][y] != "CA") {
                        break;
                    } else if (i == pacman.getX()) {
                        run = pacman.getX() - x;
                        for (int j = 0; j < run; j++) {
                            Active();
                            if (this.active == true) {
                                if (x == 0) {
                                    MuLeft();
                                } else if (lab.MazeActual()[x - 1][y] == "CA") {
                                    MovLeft();
                                } else if (lab.MazeActual()[x][y - 1] == "CA") {
                                    MovUp();
                                } else if (lab.MazeActual()[x][y + 2] == "CA") {
                                    MovDown();
                                }
                                return false;
                            } else {
                                MovRight();
                            }

                            Check();
                            Thread.sleep(140);
                        }

                        return true;
                    }

                }
            }
        } else if (pacman.getX() == x) {
            if (pacman.getY() <= y) {
                for (int i = pacman.getY(); i <= y; i++) {

                    if (lab.MazeActual()[x][i] != "CA") {
                        break;
                    } else if (i == y) {
                        run = y - pacman.getY();
                        for (int j = 0; j < run; j++) {
                            Active();
                            if (this.active == true) {
                                if (y + 2 == lab.ReturnAmountofRowsMaze()) {
                                    MuDown();
                                } else if (lab.MazeActual()[x][y + 2] == "CA") {
                                    MovDown();
                                } else if (lab.MazeActual()[x + 2][y] == "CA") {
                                    MovRight();
                                } else if (lab.MazeActual()[x - 1][y] == "CA") {
                                    MovLeft();
                                }
                                return false;
                            } else {
                                MovUp();
                            }

                            Check();
                            Thread.sleep(140);
                        }

                        return true;
                    }

                }
            } else {
                for (int i = y; i <= pacman.getY(); i++) {

                    if (lab.MazeActual()[x][i] != "CA") {
                        break;
                    } else if (i == pacman.getY()) {
                        run = pacman.getY() - y;
                        for (int j = 0; j < run; j++) {
                            Active();
                            if (this.active == true) {
                                if (y == 0) {
                                    MuUp();
                                } else if (lab.MazeActual()[x][y - 1] == "CA") {
                                    MovUp();
                                } else if (x != lab.ReturnAmountofRowsMaze() && lab.MazeActual()[x + 2][y] == "CA") {
                                    MovRight();
                                } else if (x != 0 && lab.MazeActual()[x - 1][y] == "CA") {
                                    MovLeft();
                                }
                                return false;
                            } else {
                                MovDown();
                            }
                            Check();
                            Thread.sleep(140);

                        }

                        return true;
                    }

                }
            }

        }

        return false;
    }

    /**draws the image of the ghosts through the pictures
	 * in the graphics file
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param l the length
	 * @param h the height
	 */
    private void Position(int x, int y, int l, boolean h) {
        if (h == true) {
            if (this.active == false) {
                environment[x][y].setIcon(new ImageIcon("Graphics/" + nu + "/Ghosts/ghosth" + n + "_0" + l + ".png"));
            } else {
                environment[x][y].setIcon(new ImageIcon("Graphics/" + nu + "/Ghosts/ghosthe_0" + l + ".png"));
            }
        } else {
            if (this.active == false) {
                environment[x][y].setIcon(new ImageIcon("Graphics/" + nu + "/Ghosts/ghostv" + n + "_0" + l + ".png"));
            } else {
                environment[x][y].setIcon(new ImageIcon("Graphics/" + nu + "/Ghosts/ghosthev_0" + l + ".png"));
            }
        }

    }

    public void Check() {
        if (active == false) {
            ban = true;
            perse.stop();
        }
        if (((pacman.getX() + 1 == x || pacman.getX() + 1 == x + 1 || pacman.getX() == x || pacman.getX() == x + 1)) && ((pacman.getY() + 1 == y || pacman.getY() + 1 == y + 1 || pacman.getY() == y || pacman.getY() == y + 1))) {
            if (active == false) {

                for (int i = pacman.getX(); i < pacman.getX() + 2; i++) {
                    for (int j = pacman.getY(); j < pacman.getY() + 2; j++) {
                        pacman.Erase(i, j);

                    }
                }
                if (pacman.Lose() == true) {
                    Stop.play();
                    pacman.Initialize();
                }

            } else {
                eat.play();
                pacman.setActive(true);
                pacman.setScore(20);
                for (int i = x; i < x + 2; i++) {
                    for (int j = y; j < y + 2; j++) {
                        Erase(i, j);
                    }

                }
                this.mov = 0;
                this.active = false;
                Initialize();
            }

        }
    }

    /**
     * Sets the life of the ghosts
     * @param life the life of a ghost after eaten by Pacman
     */
    public void setLife(boolean life) {
        perse.stop();
        this.life = life;
    }

    /**
     * Erases the ghosts after being eaten by Pacman
     */
    public void Vanish() {

        for (int i = x; i < x + 2; i++) {
            for (int j = y; j < y + 2; j++) {
                Erase(i, j);
            }
        }
    }

}
