package fr.iutfbleau.projetIHM2021FI2.VUE;
/**
*La classe <code>Question</code>gère l'affichage du panel ou l'on demande à l'utilisateur pour quels jours veut il voir l'occupation.
*@version 1.0
*@author Desvilles Mattis & Raymond Envric 
*/

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Classe représentant le Panel du menu où l'on choisit le début et la fin de la recherche
 */
public class Question extends JPanel {

    /**
     * Boutton rechercher
     */
    private JButton research = new JButton("Rechercher");

    /**
     * Champ de texte de la date début de recherche
     */
    private TextField txt = new TextField();

    /**
     * Champ de texte de la  fin de recherche
     */
    private TextField txt1 = new TextField();

    /**
    * Champ de texte de la date début de recherche pour le cas ou l'on utiulise les bouttons et non deux dates
    */
     private TextField txt2 = new TextField();

    /**
    * Boutton rechercher pour une journee
    */
     private JButton oneday = new JButton("1 jour");

    /**
    * Boutton rechercher pour un mois
    */
    private  JButton onemonth = new JButton("1 mois");

    /**
    * Boutton rechercher pour un an
    */
    private  JButton oneyear = new JButton("1 an");
    
    /**
     * variable qui permet de savoir si une erreur est arrivée
     */
    private int e;

    /**
     * Default constructor
     */
    public Question() {
        this.setLayout(null);
        txt.setBounds(250,150,500,30);
        txt.setFont(new Font("TimesRoman", Font.BOLD, 30));
        this.add(txt);

        txt1.setBounds(250,260,500,30);
        txt1.setFont(new Font("TimesRoman", Font.BOLD, 30));
        this.add(txt1);

	this.research.setBackground(new Color(20,167,0));
        research.setBounds(375,325,250,30);
        research.setFont(new Font("TimesRoman", Font.BOLD, 20));
        this.add(research);

	txt2.setBounds(250,465,500,30);
        txt2.setFont(new Font("TimesRoman", Font.BOLD, 30));
        this.add(txt2);

	this.oneday.setBackground(new Color(20,167,0));
	oneday.setBounds(232,515,125,30);
        oneday.setFont(new Font("TimesRoman", Font.BOLD, 20));
        this.add(oneday);

	this.onemonth.setBackground(new Color(20,167,0));
	onemonth.setBounds(432,515,125,30);
        onemonth.setFont(new Font("TimesRoman", Font.BOLD, 20));
        this.add(onemonth);

	this.oneyear.setBackground(new Color(20,167,0));
	oneyear.setBounds(632,515,125,30);
        oneyear.setFont(new Font("TimesRoman", Font.BOLD, 20));
        this.add(oneyear);
    }

    /**
     * Méthode qui dessine les lignes de texte et le fond blanc
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.WHITE);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, 40));
        g.drawString("Taux d'occupation de l'hôtel",175,50);
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g.drawString("Entrez une date de debut ici :",250,110);
	g.drawString("Entrez une date de debut ici :",250,425);
        g.drawString("Entrez une date de fin ici :",250,230);
        g.setFont(new Font("TimesRoman", Font.BOLD,20 ));
        g.drawString("Format: jj/mm/aaaa",250,130);
	g.drawString("Format: jj/mm/aaaa",250,130);
        g.drawString("Format: jj/mm/aaaa",250,445);
        if(e==1){
            g.setColor(Color.RED);
            g.drawString("Erreur: les dates ne sont pas valides",300,15);
            e=0;
        }
        //encadre noir
        g2.setPaint(Color.black);
        g2.draw(new RoundRectangle2D.Double(50, 63, 880, 300, 30, 30));
        g2.draw(new RoundRectangle2D.Double(50, 375, 880, 180, 30, 30));

    }
    /**
    * methode qui permet d'afficher et traiter les erreurs dans les champs de texte
    */
    public void err(){
        e=1;
        this.repaint();
    }
    /**
    * methode qui permet de reccuperer le bouton a partir d'une autre classe
    */
    public JButton getresearch(){
        return research;
    }

    /**
    * methode qui permet de reccuperer le bouton a partir d'une autre classe
    */
    public JButton getoneday(){
        return oneday;
    }

    /**
    * methode qui permet de reccuperer le bouton a partir d'une autre classe
    */
    public JButton getonemonth(){
        return onemonth;
    }

    /**
    * methode qui permet de reccuperer le bouton a partir d'une autre classe
    */
    public JButton getoneyear(){
        return oneyear;
    }

    /**
    * methode qui permet de reccuperer le textfield a partir d'une autre classe
    */
    public TextField gettxt(){
        return txt;
    }

    /**
    * methode qui permet de reccuperer le textfield a partir d'une autre classe
    */
    public TextField gettxt1(){
        return txt1;
    }

    /**
    * methode qui permet de reccuperer le textfield a partir d'une autre classe
    */
    public TextField gettxt2(){
        return txt2;
    }
    
}
