/*
 * Class :      ModelGame
 * Creation:    Jan 19, 2015
 * Author :     Constantin MASSON
 * 
 */

package com.spaceinvaders.models;

import com.spaceinvaders.constants.Commons;
import com.spaceinvaders.observers.ObservableGame;
import com.spaceinvaders.observers.ObserverGame;
import com.spaceinvaders.weapons.Projectile;
import java.util.ArrayList;





/**
 * <h1>ModelGame</h1>
 * <p>public class ModelGame</p>
 * 
 * <p>Data about the game</p>
 *
 * @author Constantin MASSON
 */
public class ModelGame implements Commons, ObservableGame{
    //**************************************************************************
    // Constants - Variables
    //**************************************************************************
    private     Player                  player;
    private     ArrayList<Sprite>       listAliens; //Only alien inside
    private     ArrayList<Projectile>   listAlienShoot;
    private     ArrayList<Projectile>   listPlayerShoot;
    private     ArrayList<ObserverGame> listObservers;
    private     int                     currentScore;
    private     int                     nbAliens;
    
    
    
    
    

    //**************************************************************************
    // Constructor - Initialization
    //**************************************************************************
    /**
     * Create the game model
     * Initialize the map with default value
     */
    public ModelGame(){
        this.listObservers      = new ArrayList();
        this.initMap();
    }
    
    
    /*
     * Initialize the map
     */
    public void initMap(){
        this.player             = new Player(DEFAULT_PLAYER_POS_X, DEFAULT_PLAYER_POS_Y);
        this.listPlayerShoot    = new ArrayList();
        this.listAliens         = new ArrayList();
        this.listAlienShoot     = new ArrayList();
        
        this.nbAliens           = 55; //11 * 5
        this.currentScore       = 0;
        
        this.placeInitialeSpaceInvaders();
        this.notifyObservers();
    }
    
    
    /*
     * Place the initiale space invaders
     * There are 5 lines with 11 invaders by lines
     * The highest is filled by alien3, the 2 after are with alien2
     * the 2 last are filled with alien1
     */
    private void placeInitialeSpaceInvaders(){
        //First line with alien3 (Calculation start at x=0, y=0)
        for(int x=0; x<11; x++){
            int posX = DEFAULT_LEFT_POSITION + (x*(ALIEN_WIDTH+GAP_BETWEEN_ALIENS));
            this.listAliens.add(new Alien3(posX, DEFAULT_Y_POSITION));
        }
        
        //Lines 2-3
        for(int x=0; x<11; x++){
            for(int y=1; y<3; y++){
                int posX = DEFAULT_LEFT_POSITION + (x*(ALIEN_WIDTH+GAP_BETWEEN_ALIENS));
                int posY = DEFAULT_Y_POSITION + (y*(ALIEN_HEIGHT+GAP_BETWEEN_ALIENS));
                this.listAliens.add(new Alien2(posX, posY));
            }
        }
        
        //Lines 4-5
        for(int x=0; x<11; x++){
            for(int y=3; y<5; y++){
                int posX = DEFAULT_LEFT_POSITION + (x*(ALIEN_WIDTH+GAP_BETWEEN_ALIENS));
                int posY = DEFAULT_Y_POSITION + (y*(ALIEN_HEIGHT+GAP_BETWEEN_ALIENS));
                this.listAliens.add(new Alien1(posX, posY));
            }
        }
    }
    
    
    
    
    
    
    

    //**************************************************************************
    // Functions
    //**************************************************************************
    /**
     * Kill one alien. This alien will be removed from the list
     * @param pAlien
     */
    public void killOneAlien(Sprite pAlien){
        this.listAliens.remove(pAlien);
    }
    
    
    
    
    

    //**************************************************************************
    // Getters - Setters
    //**************************************************************************
    /**
     * Get the current player score
     * @return score
     */
    public int getScore(){
        return this.currentScore;
    }
    
    /**
     * Get the number of remaining aliens
     * @return 
     */
    public int getNbAliens(){
        return this.nbAliens;
    }
    
    /**
     * Get the player
     * @return 
     */
    public Player getPlayer(){
        return this.player;
    }
    
    /**
     * Return the alien list
     * @return 
     */
    public ArrayList<Sprite> getListAliens(){
        return this.listAliens;
    }
    
    /**
     * Get the list of player shoots
     * @return 
     */
    public ArrayList<Projectile> getPlayerShoot(){
        return this.listPlayerShoot;
    }
    
    /**
     * Get the list of alien shoots
     * @return 
     */
    public ArrayList<Projectile> getAlienShoot(){
        return this.listAlienShoot;
    }
    
    
    
    
    

    //**************************************************************************
    // Observers
    //**************************************************************************
    @Override
    public void addObserver(ObserverGame obs){
        this.listObservers.add(obs);
    }

    @Override
    public void deleteAllObservers(){
        this.listObservers = new ArrayList();
    }
    
    @Override
    public void notifyObservers(){
        for(ObserverGame obs : this.listObservers){
            obs.update(this);
        }
    }
}