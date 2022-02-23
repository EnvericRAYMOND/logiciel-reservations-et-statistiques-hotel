package fr.iutfbleau.projetIHM2021FI2.CONTROLLER;
/**
*La classe <code>Controller1</code>gère les transitions et transmissions de donnés entre les panels.
*@version 1.0
*@author Desvilles Mattis & Raymond Envric 
*/

import fr.iutfbleau.projetIHM2021FI2.API.*;
import fr.iutfbleau.projetIHM2021FI2.VUE.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Controlleur s'occupant de la gestion des Actions quand un boutton est pressé
 */
public class Controller1 implements ActionListener {

    /**
     * Lien vers la classe Occupation
     */
    private Occupation lienvue;

    /**
     * Date de début de la recherche
     */
    private LocalDate date1;

    /**
     * Date de fin de la recherche
     */
    private LocalDate date2;

    /**
     * Compteur pour erreur sur les TextField
     */
    private int err;

    /**
     * Formateur de dates
     */
    private DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Méthode qui appelle la methode de création de listeners dans occupation et Réccupère un lien vers Occupation
     * @param occ
     */
    public void listeners(Occupation occ) {
        this.lienvue = occ;
        lienvue.action(this);
    }

    /**
     * réccupère les actions menées sur les bouttons de la fenetre et agis en fonction de cela
     * @param event ActionEvent
     */
    public void actionPerformed(ActionEvent event ) {
        if(event.getSource()==lienvue.getquestion().getresearch()){

            String sdatedeb = lienvue.getquestion().gettxt().getText();
            String sdatefin = lienvue.getquestion().gettxt1().getText();
            err=0;
            try{
                date1=LocalDate.parse(sdatedeb,sdf);  
                date2=LocalDate.parse(sdatefin,sdf);  
            }catch(DateTimeParseException c){err=1;lienvue.getquestion().err();}
        
        if(err==0 && date1.isBefore(date2)==true){
            lienvue.getgraph().setdate1(date1);
            lienvue.getgraph().setdate2(date2);
            lienvue.getcl().show(lienvue.getconteneur(),"panelGraph");
        }else{lienvue.getquestion().err();err=1;}
	}
        if(event.getSource()==lienvue.getgraph().getretour()){
            lienvue.getcl().show(lienvue.getconteneur(),"panelQuestion");
        }
	
	if(event.getSource()==lienvue.getquestion().getoneday()){
	    String sdatedeb = lienvue.getquestion().gettxt2().getText();
	    err=0;
	    try{
		date1=LocalDate.parse(sdatedeb,sdf);
		date2=date1;
	    }catch(DateTimeParseException c){err=1;lienvue.getquestion().err();}
	    if(err==0){
		lienvue.getgraph().setdate1(date1);
		lienvue.getgraph().setdate2(date2);
		lienvue.getcl().show(lienvue.getconteneur(),"panelGraph");
	    }
	}

	if(event.getSource()==lienvue.getquestion().getonemonth()){
	    String sdatedeb = lienvue.getquestion().gettxt2().getText();
	    err=0;
	    try{
		date1=LocalDate.parse(sdatedeb,sdf);
		date2=date1.plusDays(31);
	    }catch(DateTimeParseException c){err=1;lienvue.getquestion().err();}
	    if(err==0){
		lienvue.getgraph().setdate1(date1);
		lienvue.getgraph().setdate2(date2);
		lienvue.getcl().show(lienvue.getconteneur(),"panelGraph");
	    }
	}
	
	if(event.getSource()==lienvue.getquestion().getoneyear()){
	    String sdatedeb = lienvue.getquestion().gettxt2().getText();
	    err=0;
	    try{
		date1=LocalDate.parse(sdatedeb,sdf);
		date2=date1.plusDays(365);
	    }catch(DateTimeParseException c){err=1;lienvue.getquestion().err();}
	    if(err==0){
		lienvue.getgraph().setdate1(date1);
		lienvue.getgraph().setdate2(date2);
		lienvue.getcl().show(lienvue.getconteneur(),"panelGraph");
	    }
	}
    }

}
