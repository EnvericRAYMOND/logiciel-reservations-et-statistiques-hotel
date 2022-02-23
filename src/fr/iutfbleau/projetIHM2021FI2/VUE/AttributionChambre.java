package fr.iutfbleau.projetIHM2021FI2.VUE;
import fr.iutfbleau.projetIHM2021FI2.API.*;
import fr.iutfbleau.projetIHM2021FI2.APIUTIL.*;
import java.util.*;
import java.util.Random;  

/**
 *La classe <code>AttributionChambre</code>AttributionChambre gere l'AttributionChambre des chambres.
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
import java.time.format.DateTimeFormatter;
import java.time.LocalDate; 

public class AttributionChambre extends JPanel {
    
    /**
    * Bouton de validation de la reservervation
    */
    public JButton boutonValidation = new JButton("Valider l’attribution de la chambre");

    /**
    * Bouton d'annulation de la reservation
    */
    public JButton boutonAnnulation = new JButton("Annuler l’attribution de la chambre");

    /**
    * Boutton qui font office de chambre dans le plan de l'hotel
    */
    public JButton[] boutons = new JButton[50];
    
    /**
     * Label qui affiche le numero de chambre attribue et choisit
     */
    private JLabel NumeroChambre = new JLabel("Numéro de chambre : ");

    /**
     * variable qui stocke le numero de reservation
     */
    private String numReservation;

    /**
     * variable qui stocke le choix du numero de chambre si celui-ci est modifie
     */
    private int chambreChoix;
    
    /**
     * variable qui stocke l'etage des chambre qui correspondent a la reservation
     */
    private int etage;
    
    /**
     * boolean qui permet de savoir si une reservation a ete choisit (si l'etape une a ete faite)
     */
    private boolean changement;

    /**
     * variable d'incrementation
     */
    private int i;

    /**
     * variable qui rectifie le positionement des boutons chambre
     */
    private int moins;

    /**
     * Lien vers l'API de prereseration
     */
    private PrereservationFactory bookingPointCom=new PrereservationFactoryUTIL();

    /**
     * Lien vers l'API de reservation
     */
    private ReservationFactory grandLivreDOrAPISeulement  = new ReservationFactoryUTIL();
    
    /**
     * Lien vers la Classe Bd
     */
    private Bd bd=new Bd(bookingPointCom,grandLivreDOrAPISeulement);

    /**
     * variable qui stocke la position x du clic sur le tableau
     */
    private int PositionBoutonsChambreX;

    /**
     * variable qui stocke la position y du clic sur le tableau
     */
    private int PositionBoutonsChambreY;

    /**
     * variable qui stocke le numero des boutons correspondant a la reservation
     */
    private int nom;

    /**
     * variable qui stocke le nombre de bouton chambre
     */
    private int nombreBoutonsChambre;

    /**
     * Lien vers Chambre
     */
    Chambre set;

    /**
     * Lien vers Prereservation
     */
    Prereservation preresa;

    /**
     * Lien vers Typechambre
     */
    TypeChambre typeChambre;

    /**
     * Lien vers Set Chambre
     */
    Set<Chambre> set2;

    /**
     * Lien vers la Classe BdAttribuerReservation
     */
    BdAttribuerReservation bd2;    
    /**
     * Default constructor
     */
    public AttributionChambre() {
	this.setLayout(null);

	this.boutonValidation.setBackground(new Color(20,167,0));
	this.boutonAnnulation.setBackground(new Color(255,80,80));

	NumeroChambre.setFont(new Font("Calibri", Font.PLAIN, 20));
	
	PositionBoutonsChambreX=80;
        PositionBoutonsChambreY = 125;
	for(i = 0; i < boutons.length; i++) {
		
	    if(i==25){
		PositionBoutonsChambreX = 80;
		PositionBoutonsChambreY = 168;
	    }
	    boutons[i] = new JButton();
	    boutons[i].setBounds(PositionBoutonsChambreX, PositionBoutonsChambreY, 22,22);
	    boutons[i].setMargin(new Insets(0,0,0,0));
	    boutons[i].setEnabled (false);
	    // boutons[i] .setFont(new Font("Calibri", Font.PLAIN, 4));
	    this.add(boutons[i]);
	    PositionBoutonsChambreX+=22;
	}
    	
	
		
	boutonValidation.setBounds(200, 215, 285,35);
	boutonAnnulation.setBounds(500, 215, 285,35);
	NumeroChambre.setBounds(80, 60, 750,20);

	this.add(boutonValidation);
	this.add(boutonAnnulation);
	this.add(NumeroChambre);
    }

    /**
    * Méthode qui ajoute un Action listener aux bouttons
    * @param action
    */
    public void action(ActionListener action){
    	nombreBoutonsChambre = boutons.length;
    	for(i = 0; i < boutons.length; i++) {
	    this.boutons[i].addActionListener(action);
    	}
        this.boutonValidation.addActionListener(action);
	this.boutonAnnulation.addActionListener(action);
    }

    /**
    * Méthode qui récupère le numéro de chambre (boutons) sur lequel l'utilisateur à cliqué au niveau du plan de l'hotel
    * @param boutonChambre
    */
    public void clicBoutons(int boutonChambre){
	NumeroChambre.setText("Numéro de chambre selectionné : "+boutons[boutonChambre].getText()+", pour reservation : "+numReservation);
	chambreChoix = Integer.parseInt(boutons[boutonChambre].getText());
    }

    /**
    * Méthode qui valide l'attribution de la chambre et l'entre dans la bd
    */
    public void Validation(){
	if(changement==true){
	    bd2=new BdAttribuerReservation(chambreChoix,numReservation);
	    JFrame jFrame = new JFrame();
	    JOptionPane.showMessageDialog(jFrame, "La reservation à bien été enregistré");
	    cleanHotel();
	    cleanValues();
	}else{
	    JFrame jFrame = new JFrame();
	    JOptionPane.showMessageDialog(jFrame, "veuillez finir l'étape du premier encadré avant de valider l'attribution de la chambre");
	}  
    }

    /**
    * Méthode qui annule l'attribution de la chambre
    */
    public void Annulation(){
	cleanHotel();
        cleanValues();
    }

    /**
    * Méthode qui recupere la reservation choit par l'utilisateur dans le tableau
    * @param reservation
    */
    public void getReservationTableau(String reservation){
	numReservation = reservation;
	
        preresa = bookingPointCom.getPrereservation(reservation);
        set=grandLivreDOrAPISeulement.getChambre(preresa);
	NumeroChambre.setText("Numéro de chambre attribué : "+set.getNumero()+", pour reservation : "+reservation);
        typeChambre = set.getType();
	changement = true;
	setTypeChambre(typeChambre);
	
	this.repaint();
    }

    /**
    * Méthode qui recupère les chambres disponible qui correspondent a la reservation
    * @param typeChambre
    */
    public void setTypeChambre(TypeChambre typeChambre){
	moins = 0;
	if(typeChambre==TypeChambre.UNLD){
	    moins=0;
	    etage=1;
	}
	if(typeChambre==TypeChambre.UNLS){
	    moins=50;
	    etage=2;
	}
	if(typeChambre==TypeChambre.DEUXLS){
	    moins=50;
	    etage=2;
	}
	
	cleanHotel();
	
        preresa = bookingPointCom.getPrereservation(numReservation);
        set2=grandLivreDOrAPISeulement.getChambres(preresa);
        for (Chambre re:set2){
	    nom = re.getNumero();
	    boutons[nom-1-moins].setText(Integer.toString(nom));
	    boutons[nom-1-moins].setEnabled(true);
        }
	this.repaint();
    }

    /**
    * Méthode qui efface le plan de l'hotel
    */
    public void cleanHotel(){
	for(i = 0; i < boutons.length; i++) {
	    this.boutons[i].setEnabled(false);
	    this.boutons[i].setText("");
    	}
    }

    /**
    * Méthode qui reinitialise les valeurs à zero
    */
    public void cleanValues(){
	NumeroChambre.setText("Numéro de chambre : ");
	numReservation="";
        chambreChoix=0;
        changement = false;
	etage=0;
	this.repaint();
    }

    /**
    * Méthode qui peint l'interface y compris l'hotel
    * @param g
    */
    @Override
    public void paintComponent(Graphics g){
	super.paintComponent(g);

	Graphics2D g2 = (Graphics2D) g;
	g2.setStroke(new BasicStroke(2.0f));

	/*=== plan hotel  ===*/

	//fond hotel
	g.setColor(new Color(167,167,167));
	g.fillRect(80, 125,550, 65);
	
	//rez de chaussé
        if(etage==1) {
	    g.setColor(Color.black);
	    g.drawRect(630, 135,24, 45);
	    g.drawRect(630, 140,19, 35);
	    g.drawRect(630, 145,14, 25);
	    g.drawString("Entrée",660,160);
	    g.drawString("Rez-de-chausée",300,160);
	}

	//premier etage de l'hotel
	if(etage==2) {
	    g.setColor(Color.black);
	    g.drawRect(82, 150,24, 14);
	    g.drawLine(87, 150,87, 164);
	    g.drawLine(92, 150,92, 164);
	    g.drawLine(97, 150,97, 164);
	    g.drawLine(102, 150,102, 164);
	    g.drawString("Premier étage  ",300,160);
	}

	
	/*=== encadre  ===*/
	g.setColor(Color.black);
	g.setFont(new Font("Calibri", Font.BOLD, 12));

	//encadre noir
	g2.setPaint(Color.black);
	g2.draw(new RoundRectangle2D.Double(50, 10, 880, 260, 30, 30));

	//encadre indication
	g.drawString("Validez la réservation attribuée ou modifiez le numéro de la chambre en cliquant sur une chambre puis validez : ",80,115);  

	//titre de l'encadre
	g.setColor(Color.black);
	g.setFont(new Font("Calibri", Font.PLAIN, 26));
	g.drawString("Attribution",80,37);
    }
}
