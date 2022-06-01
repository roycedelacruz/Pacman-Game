package com.pacman.characters;

import java.applet.AudioClip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.pacman.environment.Maze;

/**
 * creates public class Pacman
 * @author Royce de la Cruz
 *
 */
public class Pacman {
    private final AudioClip   waka = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sounds/WakaWaka.wav"));
    private final AudioClip   point = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sounds/point.wav"));
    private final AudioClip   eatf = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sounds/Eatche.wav"));
    private int lives;
    private int score;
    private int y,x,b;
    private int dx,dy;
    private int nu;
    private JLabel[][] MazeGraphic;
    private Maze lab;
    private String[][] coordinates;
    private boolean active=true;
    
    public Pacman(int lives,int x, int y,JLabel[][] environment, Maze lab,int n,int s) {
        
        this.lives = lives;
        this.score = s;
        this.MazeGraphic=environment;
        this.dy=y;
        this.dx=x;
        this.active=true;
        this.lab=lab;
        this.coordinates=lab.MazeActual();
        this.nu=n;
       
               
    }
    
    /**
     * Moves pacman to the right
     * Erases the Picture of the maze to replace it with the image of Pacman
     */
    public void MovRight()
    {
        int n=1;
        if(x+2==lab.ReturnAmountofColumnsMaze())
        {
            for(int i=y;i<y+2;i++)
                    {
                        for(int j=x;j<x+2;j++)
                        {
                            Erase(j,i); 
                        } 
                        
                        for(int j=0;j<2;j++)
                        {
                             MazeGraphic[j][i].setOpaque(false);
                            if(j==1)
                            {
                               
                               Position(j,i,n,true,true,true);
                            }
                            else
                            {
                             
                               Position(j,i,n,true,true,false);
                            }
                             
                            n++;
                            
                        }
                    }
                   
                    x=0;
        }
        else if(coordinates[x+2][y]=="1")
        {
            point.play();
            this.active=false;
            lab.MazeActual()[x+2][y]="CA";
            lab.MazeActual()[x+3][y]="CA";
            lab.MazeActual()[x+2][y+1]="CA";
            lab.MazeActual()[x+3][y+1]="CA";
            MovRight();
            MovRight();
        }
        else if(coordinates[x+2][y].equalsIgnoreCase("o1")||coordinates[x+2][y].equalsIgnoreCase("a1")||coordinates[x+2][y].equalsIgnoreCase("c1"))
        {
            
            eatf.play();
            score+=100;
            lab.MazeActual()[x+2][y]="CA";
            lab.MazeActual()[x+3][y]="CA";
            lab.MazeActual()[x+2][y+1]="CA";
            lab.MazeActual()[x+3][y+1]="CA";
            MovRight();
            MovRight();
        }
        else if(coordinates[x+2][y]=="CA" && coordinates[x+2][y+1]=="CA" )
        {
            puntaje(x+2,y,x+2,y+1);     
            MuRight();
            x++;
        }
        
        
      b=b^1;      
        
    }
    
    /**
     * Moves pacman to the left
     * Erases the Picture of the maze to replace it with the image of Pacman
     */
    public void MovLeft()
    
    {
        int n=1;
        
        if(x==0)
        {
            for(int i=y;i<y+2;i++)
                    {
                        for(int j=0;j<2;j++)
                        {
                            Erase(j,i);
                        } 
                        
                        for(int j=MazeGraphic[0].length-2;j<MazeGraphic[0].length;j++)
                        {
                             MazeGraphic[j][i].setOpaque(false);
                            if(j==MazeGraphic[0].length-2)
                            {
                               
                               
                               Position(j,i,n,true,false,true);
                            }
                            else
                            {
                               
                                Position(j,i,n,true,false,false);
                            }
                             
                            n++;
                            
                        }
                    }
                    x=MazeGraphic[0].length-2;
        }
        else if(coordinates[x-1][y]=="2")
        {
            point.play();
            this.active=false;
            lab.MazeActual()[x-1][y]="CA";
            lab.MazeActual()[x-2][y]="CA";
            lab.MazeActual()[x-1][y+1]="CA";
            lab.MazeActual()[x-2][y+1]="CA";
            MovLeft();
            MovLeft();
        }
        else if(coordinates[x-1][y].equalsIgnoreCase("o2")||coordinates[x-1][y].equalsIgnoreCase("a2")||coordinates[x-1][y].equalsIgnoreCase("c2"))
        {
            eatf.play();
            score+=100;
            lab.MazeActual()[x-1][y]="CA";
            lab.MazeActual()[x-2][y]="CA";
            lab.MazeActual()[x-1][y+1]="CA";
            lab.MazeActual()[x-2][y+1]="CA";
            MovLeft();
            MovLeft();
        }
        else if(coordinates[x-1][y]=="CA" && coordinates[x-1][y+1]=="CA")
        {
            puntaje(x-1,y,x-1,y+1);
            MuLeft();    
            x--;
        }
        
       b=b^1;   
            
       
    }
    
    /**
     * Moves pacman above or up
     * Erases the Picture of the maze to replace it with the image of Pacman
     */
    public void MovUp()
    {
        int n=1;
        if(y==0)
        {
           for(int j=x;j<x+2;j++)
                    {
                        for(int i=0;i<2;i++)
                        {
                             Erase(j,i);
                        }
                           
                        for(int i=MazeGraphic.length-2;i<MazeGraphic.length;i++)
                        {
                            MazeGraphic[j][i].setOpaque(false); 
                            if(i==MazeGraphic.length-2)
                            {
                            
                               Position(j,i,n,false,true,true);
                            }
                            else
                            {
                               
                               Position(j,i,n,false,true,false);
                            }
                            
                            n++;
                        }
                    }
                    y=MazeGraphic.length-2;
        }
        else if(coordinates[x][y-1]=="3")
        {
            point.play();
            this.active=false;
            lab.MazeActual()[x][y-1]="CA";
            lab.MazeActual()[x][y-2]="CA";
            lab.MazeActual()[x+1][y-1]="CA";
            lab.MazeActual()[x+1][y-2]="CA";
            MovUp();
            MovUp();
        }
        else if(coordinates[x][y-1].equalsIgnoreCase("o3")||coordinates[x][y-1].equalsIgnoreCase("a3")||coordinates[x][y-1].equalsIgnoreCase("c3"))
        {
            eatf.play();
            score+=100;
            lab.MazeActual()[x][y-1]="CA";
            lab.MazeActual()[x][y-2]="CA";
            lab.MazeActual()[x+1][y-1]="CA";
            lab.MazeActual()[x+1][y-2]="CA";
            MovUp();
            MovUp();
        }
        else if(coordinates[x][y-1]=="CA" && coordinates[x+1][y-1]=="CA")
        {
                puntaje(x,y-1,x+1,y-1);
                MuUp();    
                y--;
        }
        
        b=b^1;      
        
    }
    
    /**
     * Moves pacman below or down
     * Erases the Picture of the maze to replace it with the image of Pacman
     */
    public void MovDown()
    {
        int n=1;
         if(y+2==lab.ReturnAmountofRowsMaze())
        {
            for(int j=x;j<x+2;j++)
                    {
                        for(int i=MazeGraphic.length-2;i<MazeGraphic.length;i++)
                        {
                          Erase(j,i);  
                        }
                        
                        for(int i=0;i<2;i++)
                        {
                            MazeGraphic[j][i].setOpaque(false); 
                            if(i==1)
                            {
                                
                               Position(j,i,n,false,false,true);
                            }
                            else
                            {
                               
                               Position(j,i,n,false,false,false);
                            }
                            
                            n++;
                        }
                    }
                    y=0;
        }
        else if(coordinates[x][y+2]=="1")
        {
            point.play();
            this.active=false;
            lab.MazeActual()[x][y+2]="CA";
            lab.MazeActual()[x][y+3]="CA";
            lab.MazeActual()[x+1][y+2]="CA";
            lab.MazeActual()[x+1][y+3]="CA";
            MovDown();
            MovDown();
        }
        else if(coordinates[x][y+2].equalsIgnoreCase("o1")||coordinates[x][y+2].equalsIgnoreCase("a1")||coordinates[x][y+2].equalsIgnoreCase("c1"))
        {
            eatf.play();
            score+=100;
            lab.MazeActual()[x][y+2]="CA";
            lab.MazeActual()[x][y+3]="CA";
            lab.MazeActual()[x+1][y+2]="CA";
            lab.MazeActual()[x+1][y+3]="CA";
            MovDown();
            MovDown();
        }
        else if(coordinates[x][y+2]=="CA" && coordinates[x+1][y+2]=="CA")
        {
            puntaje(x,y+2,x+1,y+2);
            MuDown();   
            y++;
        }
        
        b=b^1;      
        
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
    public boolean Lose()
    {
        if(lives!=0)
        {
            lives--;
            return true;
        }
        else
        {
            return false;
        }
        
    }

    
    private void puntaje(int x,int y,int x2,int y2){
        
        if((MazeGraphic[x][y].isOpaque()==true || MazeGraphic[x2][y2].isOpaque()==true))
        {
           MazeGraphic[x][y].setOpaque(false);
           MazeGraphic[x2][y2].setOpaque(false);
           score++;
           waka.play();
        }
        
        
    }
    /**
     * Initializes pacman
     */
    public void Initialize(){
       int n=1;
       x=dx;
       y=dy;
       b=0;
       for(int i=y;i<y+2;i++)
        {
            for(int j=x;j<x+2;j++)
            {
               MazeGraphic[j][i].setOpaque(false);
               
               if(j==x+1)
                            {
                                Position(j,i,n,true,true,true);
                            }
                            else
                            {
                               Position(j,i,n,true,true,false);
                            }
               b=b^1;
               n++;
            }
            
        } 
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    /**
     * Checks if PacMan is Active
     * @return false if pacMan is not active, true otherwise
     */
    public boolean isActive() {
        return active;
    }
    
    /**draws the position of pacman using the pictures
	 * in the graphics file
	 * @param x the x coordinate of pacman
	 * @param y the y coordinate of pacman
	 */
    public void Position(int x,int y,int n,boolean h,boolean v,boolean ban){
    	
        if(h==true)
        {
            if(v==true)
            {
                if(ban==true)
                {
                    MazeGraphic[x][y].setIcon(new ImageIcon("Graphics/"+nu+"/Pacman/D_" +n +b+ ".gif"));
                }
                else
                {
                    MazeGraphic[x][y].setIcon(new ImageIcon("Graphics/"+nu+"/Pacman/D_" + n + ".gif")); 
                }
                
            }
            else
            {
                if(ban==true)
                {
                    MazeGraphic[x][y].setIcon(new ImageIcon("Graphics/"+nu+"/Pacman/I_" +n +b+ ".gif"));
                }
                else
                {
                    MazeGraphic[x][y].setIcon(new ImageIcon("Graphics/"+nu+"/Pacman/I_" + n + ".gif")); 
                }
            }
        }
        else
        {
            if(v==true)
            {
                if(ban==true)
                {
                   MazeGraphic[x][y].setIcon(new ImageIcon("Graphics/"+nu+"/Pacman/AR_" +n +b+ ".gif"));  
                }
                else
                {
                    MazeGraphic[x][y].setIcon(new ImageIcon("Graphics/"+nu+"/Pacman/AR_" + n + ".gif")); 
                }
            }
            else
            {
                if(ban==true)
                {
                    MazeGraphic[x][y].setIcon(new ImageIcon("Graphics/"+nu+"/Pacman/AB_" +n +b+ ".gif"));
                }
                else
                {
                    MazeGraphic[x][y].setIcon(new ImageIcon("Graphics/"+nu+"/Pacman/AB_" + n + ".gif"));
                }
            }
            
        }
        
    }
    
    /**
     * Erases pacman by replacing the image of pacman by the Maze images
     * @param i 
     * @param j 
     */
    public void Erase(int i, int j){
        MazeGraphic[i][j].setIcon(new ImageIcon("Graphics/"+nu+"/Maze/" + "CB" + ".gif"));
    }
    public void MuRight(){
         int n=1;
         for(int i=y;i<y+2;i++)
                    {
                        
                        Erase(x,i); 
                        
         
                
                        for(int j=x+1;j<x+3;j++)
                        {
                            if(j==x+2)
                            {
                               
                               Position(j,i,n,true,true,true);
                            }
                            else
                            {
                               
                               Position(j,i,n,true,true,false);
                            }
                             
                            n++;
                            
                        }
                        
                    }
    }
    public void MuLeft(){
        int n=1;
        for(int i=y;i<y+2;i++)
                    {
                            Erase(x+1,i);
                        
                        for(int j=x-1;j<x+1;j++)
                        {
                            if(j==x-1)
                            {
                               
                               Position(j,i,n,true,false,true);
                            }
                            else
                            {
                              
                               Position(j,i,n,true,false,false);
                            }
                            
                            n++;
                        }
                    } 
    }
    public void MuUp(){
        int n=1;
        for(int j=x;j<x+2;j++)
                    {
                         Erase(j,y+1);   
                        
                        
                        for(int i=y-1;i<y+1;i++)
                        {
                             
                            if(i==y-1)
                            {
                               
                                Position(j,i,n,false,true,true);
                            }
                            else
                            {
                              
                                Position(j,i,n,false,true,false);
                            }
                            
                            n++;
                        }
                    }
    }
    
    public void MuDown(){
        int n=1;
        for(int j=x;j<x+2;j++) {
        	Erase(j,y);
        	for(int i=y+1;i<y+3;i++) {
        		if(i==y+2) {
        			Position(j,i,n,false,false,true);
                }else{
                	Position(j,i,n,false,false,false);
                }
        		n++;
            }
        }                  
    }
/**
 * Sets the score of PacMan
 * @param score the score/The total things eaten by Pacman
 */
    public void setScore(int score) {
        this.score += score;
    }
    
}
