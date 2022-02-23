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
 * Controlleur s'occupant de la gestion de l'action quand un boutton est pressé (ici le bouton de validation du champ de texte)
 */
public class ControllerRecupClient implements ActionListener {
    
    /**
     * Lien vers la classe RecuperationClient
     */
    private RecuperationClient lienvue;

    /**
     * Méthode qui appelle la methode de création de listeners dans RecuperationClient et Réccupère un lien vers RecuperationClient
     * @param recup
     */
    public ControllerRecupClient(RecuperationClient recup){
        this.lienvue = recup;
        this.lienvue.action(this);
    }
 
    /**
     * réccupère les actions menées sur le boutton du panel et agis en fonction de cela
     * @param event ActionEvent
     */
    public void actionPerformed(ActionEvent event)
    {
        if(event.getSource()==lienvue.bouton){	   
	    lienvue.validationChamp();
        }
    }
}
