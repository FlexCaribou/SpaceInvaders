/*
 * Class :      Commons
 * Creation:    Jan 18, 2015
 * Author :     Constantin MASSON
 * 
 */

package com.spaceinvaders.constants;

import java.awt.Dimension;





/**
 * <h1>Commons</h1>
 * <p>public interface Commons</p>
 *
 * @author Constantin MASSON
 */
public interface Commons {
    //**************************************************************************
    // Game constants
    //**************************************************************************
    public static final int DELAY                           = 17;
    
    public static final int DEFAULT_LEFT_POSITION           = 50;
    public static final int DEFAULT_Y_POSITION              = 50;
    public static final int GAP_BETWEEN_ALIENS              = 10;
    
    public static final int BOMB_WIDTH                      = 1;
    public static final int BOMB_HEIGHT                     = 5;
    public static final int LASER_WIDTH                     = 1;
    public static final int LASER_HEIGHT                    = 5;
    public static final int MISSILE_WIDTH                   = 1;
    public static final int MISSILE_HEIGHT                  = 5;
    
    public static final int ALIEN_WIDTH                     = 12;
    public static final int ALIEN_HEIGHT                    = 12;
    public static final int PLAYER_WIDTH                    = 15;
    public static final int PLAYER_HEIGHT                   = 10;
    
    
    public static final int GAME_WIDTH                      = 400;
    public static final int GAME_HEIGHT                     = 300;
    public static final int GROUND                          = 250;
    public static final int DEFAULT_PLAYER_POS_X            = 10;
    public static final int DEFAULT_PLAYER_POS_Y            = GROUND-PLAYER_HEIGHT-3;
    
    
    
    //**************************************************************************
    // View - dimension constants
    //**************************************************************************
    public static final Dimension   DIM_ICON_TOOLS          = new Dimension(40,40);
    public static final Dimension   DIM_FRAME_MIN           = new Dimension(450,250);
    public static final Dimension   DIM_GAME                = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    
    
    
    //**************************************************************************
    // Label - Font constants
    //**************************************************************************
    public static final int         LEFT_LABEL_SIZE         = 20;
    public static final int         LEFT_LABEL_TITLE_SIZE   = 25;
    
    
    
    //**************************************************************************
    // Paths constants
    //**************************************************************************
    public static final String PATH_ICON_TOOLS  = "img/icon/miniatures/";
    public static final String PATH_PLAYER_IMG  = "img/player.png";
    public static final String PATH_ALIEN1_IMG  = "img/alien1.png";
    public static final String PATH_ALIEN2_IMG  = "img/alien1.png";
    public static final String PATH_ALIEN3_IMG  = "img/alien1.png";
}
