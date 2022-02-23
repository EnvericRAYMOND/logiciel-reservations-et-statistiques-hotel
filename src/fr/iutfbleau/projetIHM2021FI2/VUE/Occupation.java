package fr.iutfbleau.projetIHM2021FI2.VUE;
/**
*La classe <code>Occupation</code>gère la fenêtre.
*@version 1.0
*@author Desvilles Mattis & Raymond Envric 
*/

import fr.iutfbleau.projetIHM2021FI2.API.*;
import fr.iutfbleau.projetIHM2021FI2.APIUTIL.*;
import java.awt.event.*;
import java.time.LocalDate; 
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter; 
import java.time.LocalDate;
import java.util.*;

/**
 * Classe représentant la Fenêtre et le parent des panels Graph et Question
 */
public class Occupation extends JFrame {

	/**
     * Lien vers la classe Graph
     */
    private Graph graph =new Graph();

    /**
     * Conteneur des panels
     */
    private JPanel conteneur =new JPanel();

    /**
     * lien vers le CardLayout
     */
    private CardLayout cardl =new CardLayout();

    /**
     * Formateur de dates
     */
    private DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Lien vers la classe Question
     */
    private Question question=new Question();

    /**
     * Lien vers l'API de prereseration
     */
    private PrereservationFactory bookingpointcom=new PrereservationFactoryUTIL();
    /**
     * Lien vers l'API de reservation
     */
    private ReservationFactory grandLivreDOrAPISeulement  = new ReservationFactoryUTIL();
    /**
     * Lien vers la Classe Bd
     */
    private Bd bd=new Bd(bookingpointcom,grandLivreDOrAPISeulement);
    /**
     * Default constructor
     */
    public Occupation() {
        this.setTitle("Hotel Occupation");
	this.setIconImage(Toolkit.getDefaultToolkit().getImage("Res/fr/iutfbleau/projetIHM2021FI2/logo.png"));
        this.setSize(1000,600);
        graph.setAPI(grandLivreDOrAPISeulement);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        conteneur.setLayout(cardl);
        conteneur.add(question,"panelQuestion");
        conteneur.add(graph,"panelGraph");
        this.add(conteneur);
        this.setVisible(true);
    }

    /**
     * Méthode qui ajoute les listeners au bouttons
     * @param action
     */
    public void action(ActionListener action) {
        graph.getretour().addActionListener(action);
        question.getresearch().addActionListener(action);
	question.getoneday().addActionListener(action);
	question.getonemonth().addActionListener(action);
	question.getoneyear().addActionListener(action);
    }

    /**
     * Méthode qui peint le fond en blanc
     * @param g
     */
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
    }

    /**
     * Méthode qui permet de get le cardlayout
     */
    public CardLayout getcl() {
        return cardl;
    }

    /**
     * Méthode qui permet de get le conteneur des cards
     */
    public JPanel getconteneur() {
        return conteneur;
    }

    /**
     * Méthode qui permet de get le panel de question
     */
    public Question getquestion() {
        return question;
    }

    /**
     * Méthode qui permet de get le panel de graph
     */
    public Graph getgraph() {
        return graph;
    }
}
