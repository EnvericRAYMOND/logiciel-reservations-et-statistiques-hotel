package fr.iutfbleau.projetIHM2021FI2.CONTROLLER;

/**
 *La classe <code>Affichage</code>Affichage gère l'affichage du jeu.
 *@version 1.0
 *@author Desvilles Mattis & Raymond Envric 
 */

import fr.iutfbleau.projetIHM2021FI2.API.*;
import fr.iutfbleau.projetIHM2021FI2.VUE.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
 
/**
 * Controlleur s'occupant de la gestion des Actions quand un boutton (qui represente une chambre dans le plan de l'hotel) est pressé
 */
public class ControllerAttChambre implements ActionListener {
    
    /**
     * Lien vers la classe AttributionChambre
     */
    private AttributionChambre lienvue;

    /**
     * Compteur pour les boutons correspondant aux chambres sur le plan de l'hotel
     */
    private int nombreBoutons;
    
    /**
     * Méthode qui appelle la methode de création de listeners dans AttributionChambre et Réccupère un lien vers AttributionChambre
     * @param att
     */
    public ControllerAttChambre(AttributionChambre att){
        this.lienvue = att;
        this.lienvue.action(this);
    }
 
    /**
     * récupère les actions menées sur les boutons du panel et agis en fonction de cela
     * @param event ActionEvent
     */
    public void actionPerformed(ActionEvent event) {
	if(event.getSource()==lienvue.boutonValidation){
	    lienvue.Validation();
	}
	if(event.getSource()==lienvue.boutonAnnulation){
	    lienvue.Annulation();
	}else{
	    for(nombreBoutons = 0; nombreBoutons < 50; nombreBoutons++) {
		if(event.getSource()==lienvue.boutons[nombreBoutons]){	   
		    lienvue.clicBoutons(nombreBoutons);
		}
	    }
	}
    }
}
