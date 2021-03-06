/*
 * Class :      GameModel
 * Creation:    Jan 19, 2015
 * Author :     Constantin MASSON
 * 
 */

package com.spaceinvaders.models;

import com.spaceinvaders.tools.events.DynamicEvent;
import com.spaceinvaders.tools.events.ExplosionAlien;
import com.spaceinvaders.tools.timers.TimerShoot;
import com.spaceinvaders.tools.timers.TimerBreak;
import com.spaceinvaders.tools.timers.TimerGeneral;
import com.spaceinvaders.tools.timers.TimerAlien;
import com.spaceinvaders.tools.timers.TimerPlayer;
import com.spaceinvaders.constants.Commons;
import com.spaceinvaders.observers.ObservableGame;
import com.spaceinvaders.observers.ObserverGame;
import com.spaceinvaders.tools.*;
import com.spaceinvaders.tools.events.Smoke;
import com.spaceinvaders.weapons.Projectile;
import java.util.ArrayList;





/**
 * <h1>GameModel</h1>
 * <p>
 * public class GameModel<br/>
 * extends Map<br/>
 * implements Commons, ObservableGame</p>
 * 
 * <p>Data about the game</p>
 *
 * @author Constantin MASSON
 */
public class GameModel extends Map implements Commons, ObservableGame{
    //**************************************************************************
    // Constants - Variables
    //**************************************************************************
    private     Player                  player;
    private     ArrayList<Alien>        listAliens; //Only alien inside
    private     ArrayList<Projectile>   listAlienShoot;
    private     ArrayList<Projectile>   listPlayerShoot;
    private     ArrayList<DynamicEvent>    listExplosions; //Tmp object: explosion
    private     ArrayList<ObserverGame> listObservers;
    
    private     int                     currentScore;
    private     int                     nbAliens;
    private     int                     mode;
    
    private     TimerPlayer             timerPlayer;
    private     TimerShoot              timerShoot;
    private     TimerAlien              timerAlien;
    private     TimerGeneral            timerGeneral;
    private     TimerBreak              timerBreak;
    
    private     ManagerCollision        managerCollision;
    
    private     boolean                 isGameOver;
    private     boolean                 isVictory;
    
    
    
    
    

    //**************************************************************************
    // Constructor - Initialization
    //**************************************************************************
    /**
     * Create the game model
     * Initialize the map with default value
     * @param pMode Game mode
     */
    public GameModel(int pMode){
        this.listObservers      = new ArrayList();
        this.managerCollision   = new ManagerCollision(this);
        this.mode               = pMode;
    }
    
    
    /*
     * Initialize the map
     * Create the list, the player, set the nbAlien and score
     * Place all space invaders and update the view
     * Then, the timers start
     */
    public void initMap(){
        this.player             = new Player(DEFAULT_PLAYER_POS_X, DEFAULT_PLAYER_POS_Y, this, this.mode);
        this.listAliens         = new ArrayList();
        this.listPlayerShoot    = new ArrayList();
        this.listAlienShoot     = new ArrayList();
        this.listExplosions     = new ArrayList();
        
        this.nbAliens           = 55; //11 * 5
        this.currentScore       = 0;
        this.isGameOver         = false;
        this.isVictory          = false;
        
        
        //Create the timer
        this.timerPlayer        = new TimerPlayer(this.player);
        this.timerAlien         = new TimerAlien(this);
        this.timerGeneral       = new TimerGeneral(this);
        this.timerBreak         = new TimerBreak(this);
        this.timerShoot         = new TimerShoot(   this.listAlienShoot, 
                                                    this.listPlayerShoot,
                                                    this.managerCollision);
        
        this.placeInitialeSpaceInvaders();
        this.notifyCreateMap();
        this.startTimers();
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
        //for(int x=0; x<0; x++){ //DEBUG MODE ***************************************
            int posX = GAP_LEFT + (x*GAP_BETWEEN_ALIENS);
            this.listAliens.add(new Alien1(posX, GAP_TOP, this));
        }
        
        //Lines 2-3
        for(int x=0; x<11; x++){
        //for(int x=0; x<0; x++){ //DEBUG MODE ***************************************
            for(int y=1; y<3; y++){
                int posX = GAP_LEFT + (x*GAP_BETWEEN_ALIENS);
                int posY = GAP_TOP + (y*GAP_BETWEEN_ALIENS);
                this.listAliens.add(new Alien2(posX, posY, this));
            }
        }
        
        //Lines 4-5
        for(int x=0; x<11; x++){
        //for(int x=0; x<1; x++){ //DEBUG MODE ***************************************
            for(int y=3; y<5; y++){
                int posX = GAP_LEFT + (x*GAP_BETWEEN_ALIENS);
                int posY = GAP_TOP + (y*GAP_BETWEEN_ALIENS);
                this.listAliens.add(new Alien3(posX, posY, this));
            }
        }
    }
    
    /**
     * Start every timer, means the game start
     */
    public void startTimers(){
        this.startGameTimers();
        this.timerGeneral.startTimer();
    }
    
    /**
     * Stop the game
     */
    public void stopTimers(){
        this.stopGameTimers();
        this.timerGeneral.stopTimer();
    }
    
    /**
     * Start only game timer
     */
    public void startGameTimers(){
        this.timerPlayer.startTimer();
        this.timerShoot.startTimer();
        this.timerAlien.startTimer();
    }
    
    
    /**
     * Stop only game timer
     */
    public void stopGameTimers(){
        this.timerPlayer.stopTimer();
        this.timerShoot.stopTimer();
        this.timerAlien.stopTimer();
    }
    
    
    
    
    
    //**************************************************************************
    // Functions Player - Alien management
    //**************************************************************************
    /**
     * Kill one alien. This alien will be removed from the list
     * @param pAlien
     */
    public void killOneAlien(Alien pAlien){
        this.currentScore += pAlien.getScoreValue();
        this.listAliens.remove(pAlien);
        SoundEffect.INVADER_KILLED.play();
        this.nbAliens--;
        this.listExplosions.add(new ExplosionAlien(pAlien.getCenter(), this));
    }
    
    /**
     * Player lost one life and, player hurt image displayed during a small break
     */
    public void playerHurt(){
        SoundEffect.EXPLOSION.play();
        this.player.lostOneLife();
        if(!this.player.isAlive){
            this.gameOver();
            return;
        }
        this.timerBreak.makeBreak();
        Thread t = new Thread(new Runnable(
        ) {
            @Override
            public void run(){
                try {
                    Thread.sleep(DELAY_BREAK);
                } catch(InterruptedException ex) {
                }
                player.resetImg();
            }
        });
        t.start();
    }
    
    
    
    
    
    //**************************************************************************
    // Functions Explosion and dynamics events
    //**************************************************************************
    /**
     * Destroy this projectile
     * @param p projectile to destroy
     */
    public void destroyeProjectile(Projectile p){
        this.listPlayerShoot.remove(p);
        this.listAlienShoot.remove(p);
    }
    
    /**
     * Add an explosion in the GameModel
     * @param pExplosion explosion to add
     */
    public void projectileExplode(DynamicEvent pExplosion){
        this.listExplosions.add(pExplosion);
    }
    
    /**
     * Remove an explosion
     * @param pExplosion 
     */
    public void removeExplosion(DynamicEvent pExplosion){
        this.listExplosions.remove(pExplosion);
        //this.notifyObservers();
    }
    
    /**
     * Create a new alien's projectile in the map
     * @param pProjectile projectile launched
     */
    public void addAlienProjectile(Projectile pProjectile){
        Smoke s = pProjectile.createFireSmoke();
        if(s != null){
            this.listExplosions.add(s);
        }
        this.listAlienShoot.add(pProjectile);
    }
    
    /**
     * Create a new alien's projectile in the map
     * @param pProjectile projectile launched
     */
    public void addPlayerProjectile(Projectile pProjectile){
        Smoke s = pProjectile.createFireSmoke();
        if(s != null){
            this.listExplosions.add(s);
        }
        this.listPlayerShoot.add(pProjectile);
    }
    
    
    
    
    
    //**************************************************************************
    // Functions game manager
    //**************************************************************************
    /**
     * Game over
     * Finish the game
     */
    public void gameOver(){
        SoundEffect.GAME_OVER.play();
        this.isGameOver = true;
        this.stopTimers();
        this.notifyObservers();
    }
    
    /**
     * Check if game is over
     * @return 
     */
    public boolean isGameOver(){
        return this.isGameOver;
    }
    
    /**
     * PLayer is victorious
     */
    public void victory(){
        SoundEffect.VICTORY.play();
        this.isVictory = true;
        this.stopTimers();
        this.notifyObservers();
    }
    
    /**
     * Check if game is a victory
     * @return 
     */
    public boolean isVictory(){
        return this.isVictory;
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
    public ArrayList<Alien> getListAliens(){
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
    
    /**
     * Get the explosion list
     * @return 
     */
    public ArrayList<DynamicEvent> getListExplosions(){
        return this.listExplosions;
    }
    
    /**
     * Get current game mode
     * @return current mode
     */
    public int getMode(){
        return this.mode;
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
    
    @Override
    public void notifyCreateMap(){
        for(ObserverGame obs : this.listObservers){
            obs.updateInitMap(this);
        }
    }
}
