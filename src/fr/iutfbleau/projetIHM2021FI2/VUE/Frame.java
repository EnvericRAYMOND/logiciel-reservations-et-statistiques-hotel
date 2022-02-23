package fr.iutfbleau.projetIHM2021FI2.VUE;
/**
*La classe <code>Affichage</code>Affichage gère l'affichage du jeu.
*@version 1.0
*@author Desvilles Mattis & Raymond Envric 
*/

import fr.iutfbleau.projetIHM2021FI2.CONTROLLER.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Classe qui contiens le main et Crée la fenetre, le controlleur et la bd
 */
public class Frame {

    /**
     * Lien vers la classe Occupation
     */
    private Occupation occ;

    /**
     * Default constructor
     */
    public Frame() {
        Controller1 controller = new Controller1();
        Occupation occ = new Occupation();
        controller.listeners(occ);
    }



    /**
     * Main du programme
     * @param args[]
     */
    public static void main(String args[]) {
        Frame f=new Frame();
    }

}