package fr.iutfbleau.projetIHM2021FI2.VUE;
import fr.iutfbleau.projetIHM2021FI2.CONTROLLER.*;
/**
 *La classe <code>Affichage</code>Affichage gere l'affichage du JFrame pour l'RecuperationClient des chambres.
 *@version 1.0
 *@author Desvilles Mattis & Raymond Envric 
 */
import javax.swing.*;
import java.awt.*;
import javax.swing.UIManager;

/**
 * Classe qui contiens le main, crée la fenetre, les controlleurs, les panels
 */
public class Frame1 extends JFrame {

    /**
     * Lien vers la classe RecuperationClient
     */
    private RecuperationClient recup = new RecuperationClient();
    
    /**
     * Lien vers la classe AttributionChambre
     */
    private AttributionChambre att = new AttributionChambre();

    /**
     * Conteneur des panels
     */
    private JPanel panel = new JPanel();

    /**
     * lien vers le GridLayout
     */
    private GridLayout layout = new GridLayout(2,1);

    /**
     * lien vers ControllerRecupClient
     */
    private ControllerRecupClient controleur;

    /**
     * lien vers ControllerRecupClientTableau
     */
    private ControllerRecupClientTableau controleur3;

    /**
     * lien vers ControllerAttChambre
     */
    private ControllerAttChambre controleur2;

    /**
     * Default constructor
     */
    public Frame1() {
	panel.setLayout(layout);

	panel.add(recup);//topPanel
	panel.add(att);//bottomPanel

	panel.setVisible(true);
	recup.setVisible(true);
	att.setVisible(true);
		
	this.setTitle("Hotel attribution chambre ");
	this.setIconImage(Toolkit.getDefaultToolkit().getImage("Res/fr/iutfbleau/projetIHM2021FI2/logo.png"));
	this.setSize(1000,600);
	this.setResizable(false);
	this.setLocationRelativeTo(null);
		
	controleur = new ControllerRecupClient(recup);
        controleur3 = new ControllerRecupClientTableau(recup,att);
	controleur2 = new ControllerAttChambre(att);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(true);
		
	this.add(panel);
	//this.add(occ);
		
    }
    
    /**
     * Main du programme
     * @param args[]
     */
    public static void main(String args[]) {
	Frame1 fen= new Frame1();
    }
	
}
