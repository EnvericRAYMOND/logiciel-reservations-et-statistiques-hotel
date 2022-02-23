package fr.iutfbleau.projetIHM2021FI2.CONTROLLER;

/**
 *La classe <code>Affichage</code>Affichage gère l'affichage du jeu.
 *@version 1.0
 *@author Desvilles Mattis & Raymond Envric 
 */
import fr.iutfbleau.projetIHM2021FI2.API.*;
import fr.iutfbleau.projetIHM2021FI2.VUE.*;

import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;
 
/**
 * Controlleur s'occupant de la gestion des Actions quand un clic est effectué sur le tableau
 */
public class ControllerRecupClientTableau implements MouseListener {
    
    /**
     * Lien vers la classe RecuperationClient
     */
    private RecuperationClient lienvue;
    
    /**
     * Lien vers la classe AttributionChambre
     */
    private AttributionChambre lienvue2;

    /**
     * Méthode qui appelle la methode de création de listeners dans RecuperationClient et Réccupère deux liens, un vers RecuperationClient et un vers AttributionChambre
     * @param recup
     * @param att
     */
    public ControllerRecupClientTableau(RecuperationClient recup,AttributionChambre att){
        this.lienvue = recup;
        this.lienvue.action2(this);
	this.lienvue2 = att;
    }

    /**
     * récupère les actions menées sur la table (JTable) du panel et agis en fonction de cela
     * @param event MouseEvent
     */
    public void mouseClicked(MouseEvent event) {
	if(event.getSource()==lienvue.table){
	    String reservation  = lienvue.recupClicCaseTableau(event.getPoint());
	    lienvue2.getReservationTableau(reservation);
	}
    }  
    public void mouseEntered(MouseEvent event) {  
    }  
    public void mouseExited(MouseEvent event) {
    }  
    public void mousePressed(MouseEvent event) {   
    }  
    public void mouseReleased(MouseEvent event) {   
    }  
}
