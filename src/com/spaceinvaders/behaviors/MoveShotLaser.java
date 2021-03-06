/*
 * Class :      MoveShootMissile
 * Creation:    Jan 23, 2015
 * Author :     Constantin MASSON
 * 
 */

package com.spaceinvaders.behaviors;

import com.spaceinvaders.weapons.Laser;
import java.awt.Point;





/**
 * <h1>MoveShootBomb</h1>
 * <p>public class MoveShootBomb</p>
 *
 * @author Constantin MASSON
 */
public class MoveShotLaser implements MoveShotType{
    //**************************************************************************
    // Constants - Variables
    //**************************************************************************
    private     final Laser       laser;
    
    
    
    
    

    //**************************************************************************
    // Constructor - Initialization
    //**************************************************************************
    /**
     * Create a new MoveShotType for a laser
     * @param pLaser laser tie
     */
    public MoveShotLaser(Laser pLaser){
        this.laser = pLaser;
    }
    
    
    
    
    

    //**************************************************************************
    // Functions
    //**************************************************************************
    @Override
    public void move(){
        this.laser.setCenter(new Point(this.laser.getPosX(), this.laser.getPosY()+LASER_SPEED ));
    }
    
    @Override
    public void moveDirection(int direction){
    }
}
