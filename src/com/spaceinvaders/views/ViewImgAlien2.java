/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.spaceinvaders.views;

import com.spaceinvaders.constants.Commons;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * @deprecated 
 * @author Constantin MASSON
 */
public class ViewImgAlien2 extends JPanel implements Commons{
    private     final Image img;
    
    /**
     * Create a new player image
     * This is used to display player lives
     */
    public ViewImgAlien2(){
        ImageIcon i = new ImageIcon(PATH_ALIENS_IMG+"alien2_pos2.png");
        this.img    = i.getImage();
        Dimension dim = new Dimension(img.getWidth(null), img.getHeight(null));
        this.setPreferredSize(dim);
    }
    

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(this.img, 0, 0, this);
    }
}
