package fr.iutfbleau.projetIHM2021FI2.VUE;

/**
*La classe <code>Graph</code>gère l'affichage du graph.
*@version 1.0
*@author Desvilles Mattis & Raymond Envric 
*/
import fr.iutfbleau.projetIHM2021FI2.API.ReservationFactory;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

/**
 * Classe représentant le panel qui affiche les résultats de la recherche sous forme de graph
 */
public class Graph extends JPanel {

    /**
     * Nombre de jours de la recherche
     */
    private int nbjours = 30;

    /**
     * Date de début de la recherche
     */
    private LocalDate datedeb;

    /**
     * Date de fin de la recherche
     */
    private LocalDate datefin;

    /**
     * Moyenne d'occupation sur toute la durée
     */
    private int moy;

    /**
     * Moyenne d'occupation sur une semaine
     */
    private int moysem;

    /**
     * Formateur de dates
     */
    private DateTimeFormatter dtm = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * variable représentant Période d'recherchée
     */
    private Period periode;

    /**
     * Date de début en String
     */
    private String date_1;

    /**
     * variable utilisée pour représenter l'occupation 
     */
    private int occ;

    /**
     * Date de fin en String
     */
    private String date_2;


    /**
     * Variable qui représente une valeur aléatiore
     */
    private int[] jour;

    /**
     * Boutton retour
     */
    private JButton retour = new JButton("Retour");

    /**
     * Lien vers l'API de reservation
     */
    private ReservationFactory bookingpointcom;

    /**
     * Default constructor
     */
    public Graph() {
        this.setLayout(null);
        retour.setBackground(new Color(167,20,0));
        retour.setBounds(375,515,250,30);
        retour.setFont(new Font("TimesRoman", Font.BOLD, 30));
        this.add(retour);
    }

    /**
     * Méthode qui permet de transmettre l'api a graph
     */
    public void setAPI(ReservationFactory api){
        bookingpointcom=api;
    }
    
    /**
     * Méthode qui va afficher un graph et sa légende
     * @param g
     */
    public void paintComponent(Graphics g) {
        //Réccupération des donnés//
        periode= Period.between(datedeb,datefin);
        if(periode.getMonths()==0 && periode.getYears()==0){nbjours=periode.getDays();}
        if(periode.getMonths()>0 && periode.getYears()==0){nbjours=periode.getDays()+periode.getMonths()*30;}
        if(periode.getMonths()==0 && periode.getYears()>0){nbjours=periode.getDays()+periode.getYears()*365;}
        if(periode.getMonths()>0 && periode.getYears()>0){nbjours=periode.getDays()+periode.getYears()*365+periode.getMonths()*30;}

        date_1=dtm.format(datedeb);
        date_2=dtm.format(datefin);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,this.getWidth(),this.getHeight());

        //Génération du graph//
        g.setColor(Color.BLACK);
        g.fillRect(80,140,840,330);
        g.setColor(Color.WHITE);
        g.drawLine(90,460,910,460);
        g.drawLine(90,460,90,150);
        
        if(nbjours<366){
            moy=0;
            for(int i=0;i<nbjours;i++){
                occ=bookingpointcom.getRatio(datedeb.plusDays(i));
                g.fillRect((820/nbjours)/4+(i*820)/nbjours+90,450-occ*4,(820/nbjours)/2,occ*4);
                moy+=occ;
            }
            g.setColor(Color.BLACK);
            g.drawString("(jours)",485,500);
            moy=(int)((moy/nbjours));
        }
        if(nbjours>365){
            moy=0;
            for(int i=0;i<nbjours/7;i++){    
                moysem=0;
                for(int y=0;y<7;y++){
                    occ=bookingpointcom.getRatio(datedeb.plusDays(y+i*7));
                    moysem+=occ;
                }
                moysem=moysem/7;
                g.fillRect((820/(nbjours/7))/4+(i*820)/(nbjours/7)+90,450-moysem*4,(820/(nbjours/7))/2,moysem*4);
                moy+=moysem;
            }
            g.setColor(Color.BLACK);
            g.drawString("(Semaines)",475,500);
            moy=(int)((moy/(nbjours/7)));
        }
        g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        g.drawString(""+moy+"%",30,455-moy*4);
        g.drawString("Temps ->",475,490);
        g.drawString("Occupation (en %)",55,120);
        g.drawString(date_1,55,490);
        g.drawString(date_2,880,490);
        g.setFont(new Font("TimesRoman", Font.BOLD, 40));
        g.drawString("Taux d'occupation de l'hôtel",175,50);

        //Moyenne//
        g.setColor(Color.RED);
        g.drawLine(70,450-moy*4,920,450-moy*4);
    }

    /**
     * Méthode qui permet de set une date de debut de recherche
     * @param date
     */
    public void setdate1(LocalDate date) {
        datedeb=date;
    }

    /**
     * Méthode qui permet de set une date de fin de recherche
     * @param date
     */
    public void setdate2(LocalDate date) {
        datefin=date.plusDays(1);
    }
    
    /**
     * Méthode qui permet de get le bouton de retour
     */
    public JButton getretour() {
        return retour;
    }
}
