package fr.iutfbleau.projetIHM2021FI2.VUE;
import fr.iutfbleau.projetIHM2021FI2.API.*;
import fr.iutfbleau.projetIHM2021FI2.APIUTIL.*;

import java.util.*;

/**
 *La classe <code>RecuperationClient</code>RecuperationClient gere l'RecuperationClient des chambres.
 *@version 1.0
 *@author Desvilles Mattis & Raymond Envric 
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

public class RecuperationClient extends JPanel {
    
    /**
    * Bouton de validation du champ de texte
    */
    public JButton bouton = new JButton("Valider");

    /**
    * Champ de texte pour recuperer la reservation a partir du nom prenom ou numero de reservation
    */
    private JTextField champ = new JTextField(20);

    /**
    * Modele du tableau
    */
    private DefaultTableModel tableModel = new DefaultTableModel();

    /**
    * Tableau
    */
    public JTable table = new JTable(tableModel);

    /**
    * ScrollPane pour ajouter un slider au tableau
    */
    private JScrollPane scroll;

    /**
    * Label pour afficher des messages d'erreurs
    */
    private JLabel messageErreur = new JLabel(" ");

    /**
    * Variable qui stocke le type de chambre
    */
    private String type;

    /**
    * Variable qui stocke toute la chaine de caractere entree par l'utilisateur dans le champ de texte
    */
    private String phrase;

    /**
    * Variable qui stocke les differentes partie de la chaine de caractere (soit mots[0]=nom et mots[1]=prenom soit mots[0]=numero de reservation)
    */
    private String[] mots;

    /**
    * Variable qui stocke la ligne sur laquel l'utilisateur a clique dans le tableau
    */
    private int row;

    /**
    * Variable qui stocke le type de chambre avec l'orthographe ameliore
    */
    private String nom;

    /**
     * Lien vers l'API de reservation
     */
    PrereservationFactory bookingpointcom = new PrereservationFactoryUTIL();

    /**
     * Lien vers Set Prereservation
     */
    Set<Prereservation> preresas;

     /**
     * Default constructor
     */
    public RecuperationClient() {
	this.setLayout(null);
    	
	this.bouton.setBackground(new Color(20,167,0));
		
	messageErreur.setFont(new Font("Calibri", Font.PLAIN, 15));
	messageErreur.setBounds(80, 115, 600,100);
	messageErreur.setForeground(new Color(255,80,80));
		
	champ.setFont(new Font("Calibri", Font.PLAIN, 20));
	champ.setBounds(80, 120, 550,35); 
		
	bouton.setBounds(630, 120, 200,35);

	tableModel.addColumn("Reservation");
	tableModel.addColumn("Nom");
	tableModel.addColumn("Prenom");
	tableModel.addColumn("Date");
	tableModel.addColumn("nuits");
	tableModel.addColumn("Type de chambre");
	scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	scroll.setBounds(80, 180, 700,90);

	table.setDefaultEditor(Object.class, null);

		
	this.add(scroll);
	this.add(messageErreur);
	this.add(champ);
	this.add(bouton);
    }

    /**
    * Méthode qui ajoute un Action listener au boutton
    * @param action
    */
    public void action(ActionListener action){
	this.bouton.addActionListener(action);
    }

    /**
    * Méthode qui ajoute un Mouse listener au tableau
    * @param action2
    */
    public void action2(MouseListener action2){
	this.table.addMouseListener(action2);
    }

    /**
    * Méthode qui recupere le numero de reservation de la case du tableau qui a reçu un clic
    * @param positionXY
    */
    public String recupClicCaseTableau(Point positionXY){
	row=table.rowAtPoint(positionXY);
	return table.getValueAt(row, table.convertColumnIndexToView(table.getColumn("Reservation").getModelIndex())).toString();
    }

    /**
    * Méthode qui recupère la reservation a partir du champ de texte
    */
    public void validationChamp(){
	phrase  = champ.getText();
	phrase = phrase.replaceAll("\\s{2,}", " ").trim();
	mots = phrase.split(" ");

	

	deleteAllTableRows();
	messageErreur.setText(" ");
	    
	if (mots.length <= 2) {
	    if (mots.length == 2) {
		mots[0] = mots[0].substring(0, 1).toUpperCase() + mots[0].substring(1).toLowerCase();
		mots[1] = mots[1].substring(0, 1).toUpperCase() + mots[1].substring(1).toLowerCase();
		try{		
		    preresas  = bookingpointcom.getPrereservations(mots[0],mots[1]);
		    for (Prereservation p : preresas) {
			tableModel.insertRow(0, new Object[] { p.getReference(), p.getClient().getNom(), p.getClient().getPrenom(), p.getDateDebut(), p.getJours(), changeNomTypeChambre(p.getTypeChambre()) });
		    }
		}
		catch(Exception e){
		    try{
		        bookingpointcom = new PrereservationFactoryUTIL();
		        preresas  = bookingpointcom.getPrereservations(mots[1],mots[0]);
			for (Prereservation p : preresas) {
			    tableModel.insertRow(0, new Object[] { p.getReference(), p.getClient().getNom(), p.getClient().getPrenom(), p.getDateDebut(), p.getJours(), changeNomTypeChambre(p.getTypeChambre()) });
			}
		    }
		    catch(IllegalStateException e2){
			messageErreur.setText("Il n'y a pas de reservation avec ce nom et prenom");
		    }
		}	    
	    }
	    if (mots.length == 1) {
		mots[0] = mots[0].substring(0).toUpperCase();
		try{
		    Prereservation preresa = bookingpointcom.getPrereservation(mots[0]);
		    tableModel.insertRow(0, new Object[] { preresa.getReference(), preresa.getClient().getNom(), preresa.getClient().getPrenom(), preresa.getDateDebut(), preresa.getJours(), changeNomTypeChambre(preresa.getTypeChambre()) });
		}
		catch(IllegalStateException e3){
		    try{
			mots[0] = mots[0].substring(0,4)+"-"+mots[0].substring(4,8)+"-"+mots[0].substring(8);
			Prereservation preresa = bookingpointcom.getPrereservation(mots[0]);
			tableModel.insertRow(0, new Object[] { preresa.getReference(), preresa.getClient().getNom(), preresa.getClient().getPrenom(), preresa.getDateDebut(), preresa.getJours(), changeNomTypeChambre(preresa.getTypeChambre()) });
		    }
		    catch(IllegalStateException e4){
			messageErreur.setText("Il n'y a pas de reservation avec cet identifiant");
		    }
		    catch(StringIndexOutOfBoundsException e5){
		        messageErreur.setText("Il n'y a pas de reservation avec cet identifiant");
		    }
		}
	    }
	}
	else {
	    messageErreur.setText("mauvais nombres de mots");
	}
    }

    /**
    * Méthode qui efface le tableau
    */
    public void deleteAllTableRows() {
	while( tableModel.getRowCount() > 0 ){
	    tableModel.removeRow(0);
	}
    }

    /**
    * Méthode modifie l'orthographe des types de chambre pour les rendre compréhensible par l'utilisateur
    * @param typeChambre
    */
    public String changeNomTypeChambre(Object typeChambre) {
    	type=typeChambre.toString();  
    	nom ="";
    	if(type.equals("UNLS")){
    		nom="un lit simple";
    	}
    	if(type.equals("UNLD")){
    		nom="un lit double";
    	}
    	if(type.equals("DEUXLS")){
    		nom="deux lits simples";
    	}
    	return nom;
    }

    /**
    * Méthode qui peint l'interface
    * @param g
    */
    @Override
    public void paintComponent(Graphics g){
	super.paintComponent(g);

	Graphics2D g2 = (Graphics2D) g;
	g2.setStroke(new BasicStroke(2.0f));

	/*=== logo titre de la fenetre  ===*/
	//carre or
	g2.setPaint(new Color(204,162,107));
	g2.fillRoundRect((getWidth()/2)-200, 5, 400, 50, 25, 25);

	//carre bleu
	g2.setPaint(new Color(0,32,96));
	g2.fillRoundRect((getWidth()/2)-196, 10, 392, 40, 25, 25);

	//titre blanc
	g.setColor(Color.white);
	g.setFont(new Font("Calibri", Font.PLAIN, 30));
	g.drawString("Attribution des chambres",305,40);

	/*=== encadre  ===*/
	//encadre noir 
	g2.setPaint(Color.black);
	g2.draw(new RoundRectangle2D.Double(50, 63, 880, 215, 30, 30));

	//titre de l'encadre
	g.setColor(Color.black);
	g.setFont(new Font("Calibri", Font.PLAIN, 26));
	g.drawString("La reservation",80,88);

	//enonce de l'encadre
	g.setColor(Color.black);
	g.setFont(new Font("Calibri", Font.BOLD, 11));
	g.drawString("Entrez le numéro de réservation ou le nom et prénom du client puis selectionnez la réservation souhaitée dans le tableau : ",80,115); 
    }	
}
