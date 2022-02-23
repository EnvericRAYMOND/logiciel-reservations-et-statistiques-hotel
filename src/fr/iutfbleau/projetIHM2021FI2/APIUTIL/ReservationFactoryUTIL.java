package fr.iutfbleau.projetIHM2021FI2.APIUTIL;
import fr.iutfbleau.projetIHM2021FI2.API.*;
import fr.iutfbleau.projetIHM2021FI2.MNP.*;
import java.time.format.DateTimeFormatter; 
import java.time.LocalDate;
import java.util.*;
import java.sql.*;
import java.util.function.Predicate;
import java.lang.*;

/**
 * Usine non persistante stockant les réservations dans une structure de données permettant de simuler un ensemble.
 * 
 * L'hôtel est magique.
 * Il y a trop de chambres (3 millions, 1 million de chaque sorte).
 * on va juste prendre la prochaine chambre.
 * on ne fera même peut-être jamais le ménage (beurk).
 *
 * Par paresse, la réservation possède la même référence que la préréservation correspondante.
 *
 */
public class ReservationFactoryUTIL implements ReservationFactory{

    // BUGFIX. ^ c'est le xor en java donc 10^6 ça ne fait pas un million mais autre chose
    // Du coup en dessous j'écris 1000000 à la place.
    // plus petite et plus grande valeur pour les chambres UNLD
    private static int MIN_UNLD  =  1;
    private static int MAX_UNLD  =  50;
    // plus petite et plus grande valeur pour les chambres UNLS
    private static int MIN_UNLS   = 51;
    private static int MAX_UNLS   = 75;
    // plus petite et plus grande valeur pour les chambres DEUXLS
    private static int MIN_DEUXLS = 76;
    private static int MAX_DEUXLS = 100;
    
    private static int NBCHAMBRES = 100;

    /**
     * Formateur de dates
     */
    private DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // plus grand nombre de chambres à proposer si le client souhaite choisir (va automatiquement salir les chambres précédentes)
    private static int ADEQUATE = 5;
    // 
    private HashMap<LocalDate,Set<Reservation>> brain;

    // /**
    //  * Constructeur
    //  */
    public ReservationFactoryUTIL(){
        brain = new HashMap<LocalDate,Set<Reservation>>();
    }
    
    /**
     * Recherche une chambre adéquate à partir de
     * @param  p une  préréservation 
     * @return la chambre
     * @throws NullPointerException si un argument est null
     * @throws IllegalStateException si une chambre correspondant à cette Préréservation n'existe pas.
     *
     * Ne devrait pas retourner un objet null.
     */
    public Chambre getChambre(Prereservation p){
        Objects.requireNonNull(p,"La préréservation est null.");
        Chambre choix=null;
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            Connection cnx = DriverManager.getConnection("retrait login+mdp de la base de donnees pour GitHub","","");
            PreparedStatement pst = cnx.prepareStatement("SELECT chambre FROM Reservations where reference=?");
            pst.setString(1,p.getReference());
            ResultSet rs = pst.executeQuery();
	    rs.next();
            choix =new ChambreNP(Integer.parseInt(rs.getString(1)),p.getTypeChambre());
            cnx.close();
        }
        catch(ClassNotFoundException c)
        {
            System.err.println(c);
            System.exit(2);
        }
        
        catch(SQLException s)
        {
            System.err.println(s);
            System.exit(2);
        }
        return choix;
    }
    private int occupied(Chambre c,LocalDate date){
        Objects.requireNonNull(date,"La date proposée est null.");
        int occ=0;
        Set<Reservation> s;
        if (this.brain.containsKey(date)){
            s = this.brain.get(date);
            }
        else{
            s = new HashSet<Reservation>();
        } 
        if (s.isEmpty()==false){
            for(Reservation r : s){
                if(r.getChambre().getNumero()==c.getNumero()){
                    occ=1;
                }
            }
        }
        return occ;
    }

    /**
     * Recherche toutes les chambres adéquates à partir de
     * @param  p une  préréservation 
     * @return les chambres (set de chambre)
     * @throws NullPointerException si un argument est null
     * @throws IllegalStateException si une chambre correspondant à cette Préréservation n'existe pas.
     * Ne devrait pas retourner un objet null.
     *
     * NB. On considère que seulement les this.ADEQUATE prochaines chambres disponibles sont adéquates. 
     * 
     */
    public Set<Chambre> getChambres(Prereservation p){
        Objects.requireNonNull(p,"La préréservation est null.");
        Set<Chambre> chambre=new HashSet<Chambre>();
        int nbtype=0,found=1,mintype=0;
        Chambre c=null;
        if(p.getTypeChambre()==TypeChambre.UNLS){nbtype=MAX_UNLS - MIN_UNLS+1;mintype=MIN_UNLS;}
        if(p.getTypeChambre()==TypeChambre.UNLD){nbtype=MAX_UNLD - MIN_UNLD+1;mintype=MIN_UNLD;}
        if(p.getTypeChambre()==TypeChambre.DEUXLS){nbtype=MAX_DEUXLS - MIN_DEUXLS+1;mintype=MIN_DEUXLS;}
        for (int i=0; i<nbtype  ;i++) {
            found=1;
            for (int j=0;j<p.getJours();j++){

                c=new ChambreNP(i+mintype,p.getTypeChambre());
                if(occupied(c,p.getDateDebut().plusDays(j))==1){
                    found=0;
                }
            }
            if(found==1){
                chambre.add(c);
            }
        }
        return chambre;
    }

    /*  
     * Prend en compte la réservation (ajout dans brain, mis à jour du prochain numéro)
     * @param r une réservation (qu'on suppose correcte)
     * 
     * NB. pas de vérification.
     * Utiliser les vérifications avant.
     * 
     */
    private void addReservationToBrain(Reservation r){       
        // Ajout pour toutes les dates de la réservation.
        LocalDate d = r.getDateDebut();
        for (int i=0; i< r.getJours() ;i++) {
            this.addReservationToBrain(r,d);
            d = d.plusDays(1); // Le jour suivant le matou revient.
        }
    }

    /*  
     * Ajout dans brain de la réservation à la date donnée.
     * @param r une réservation (qu'on suppose correcte)
     * 
     * NB. pas de vérification.
     * Utiliser les vérifications avant.
     * 
     */
    private void addReservationToBrain(Reservation r, LocalDate d){
        Set<Reservation> s = this.getReservation(d);
        s.add(r);
        this.brain.put(d,s);
    }

    public String prochainesChambresLibres(){
        return "pas implanté";
    }
    
    /**
     * Fabrique (ajoute) une réservation
     * @param  p une  préréservation 
     * @param  c une  chambre (normalement libre et adaptée à la préréservation) 
     * @return la réservation
     * @throws NullPointerException si un argument est null
     * @throws IllegalArgumentException si la chambre ne correspondant pas au type de chambre de la préréservation.
     * @throws IllegalStateException si la chambre n'est pas disponible.
     *
     * Ne devrait pas retourner un objet null.
     */    
    public Reservation createReservation(Prereservation p, Chambre c){
        Objects.requireNonNull(p,"La préréservation est null.");
        Objects.requireNonNull(p,"La chambre est null.");
        if (c.getType()!=p.getTypeChambre()) {
            throw new IllegalArgumentException("Erreur sur le type de la chambre: la préréservation indique " + p.getTypeChambre() + " mais la chambre est  " + c.getType());
        }
        else if (false) // on fait comme si ça n'arrive jamais dans l'hôtel magique (pour l'instant).
            {
                throw new IllegalArgumentException("La chambre " + c.monPrint() + " n'est pas disponible pour fabriquer une réservation à partir de la préréservation " + p.monPrint());
            }
        else {
            Reservation r = new ReservationNP(p.getReference(), p.getDateDebut(), p.getJours(), c, p.getClient());
            this.addReservationToBrain(r);
            return r;
            }
    }

    /**
     * Cherche les réservations
     * @param  d une date
     * @return la ou les réservation(s) à cette date sous forme d'un ensemble
     * @throws NullPointerException si un argument est null
     *
     * Ne devrait pas retourner un objet null, par contre peut être un ensemble qui est vide.
     */    
    public Set<Reservation> getReservation(LocalDate d){
        Objects.requireNonNull(d,"La date proposée est null.");
        
        Set<Reservation> s;
        if (this.brain.containsKey(d)){
            s = this.brain.get(d);
            }
        else{
            s = new HashSet<Reservation>();
        }        
        return s;
    }

    /**
     * Cherche le nombre de chambres disponibles pour une date (réservées ou non).
     * @param  d une date
     * @return un entier
     * @throws NullPointerException si un argument est null
     *
     * Ne devrait pas retourner un entier négatif.
     */    
    public int getDisponibles(LocalDate d){
        int occ=0;
        Set<Reservation> set;
        set=this.getReservation(d);
        for (Reservation re:set){
            occ+=1;
           // System.out.println(re.monPrint());   
        }
        int disp=NBCHAMBRES-occ;
        return disp;
    }

    /**
     * Cherche les réservations
     * @param  d une date
     * @param  t un type de chambre
     * @return la ou les réservation(s) pour ce type de chambre à cette date sous forme d'un ensemble
     * @throws NullPointerException si un argument est null
     *
     * Ne devrait pas retourner un objet null, par contre peut être un ensemble qui est vide.
     */    
    public Set<Reservation> getReservation(LocalDate d, TypeChambre t){
        System.out.println("ne sera pas implanté");
        return null;
    }
    
    /**
     * Cherche le nombre de chambres disponibles d'un certain type pour une date (réservées ou non).
     * @param  d une date
     * @param  t un type de chambre
     * @return un entier
     * @throws NullPointerException si un argument est null
     *
     * Ne devrait pas retourner un entier négatif.
     */    
    public int getDisponibles(LocalDate d, TypeChambre t){
        System.out.println("ne sera pas implanté");
        return 1;
    }
    

    /**
     * Cherche la proportion de chambres disponibles pour une date (réservées sur réservables).
     * @param  d une date
     * @return un entier entre 0 et 100
     * @throws NullPointerException si un argument est null
     */    
    public int getRatio(LocalDate d){
        int disp =this.getDisponibles(d);
        int occ = 100*(NBCHAMBRES-disp);
        int ratio=occ/NBCHAMBRES;
        return ratio;
    }
    

    /**
     * Cherche la proportion de chambres disponibles d'un certain type pour une date (réservées sur réservables).
     * @param  d une date
     * @param  t un type de chambre
     * @return un entier entre 0 et 100
     * @throws NullPointerException si un argument est null
     */    
    public int getRatio(LocalDate d, TypeChambre t){
        System.out.println("ne sera pas implanté");
        return 1;
    }
    

    /**
     * Cherche le nombre moyen de chambres disponibles entre deux date (réservées ou non), arrondies à l'entier inférieur.
     * @param  d1 une date
     * @param  d2 une date
     * @return un entier
     * @throws NullPointerException si un argument est null
     * @throws IllegalArgumentException si l'ordre temporel d1 avant d2 n'est pas respecté.
     *
     * Ne devrait pas retourner un entier négatif.
     */    
    public int getDisponibles(LocalDate d1, LocalDate d2){
        System.out.println("ne sera pas implanté");
        return 1;
    }
    

    /**
     * Cherche les réservations
     * @param  d1 une date
     * @param  d2 une date
     * @param  t un type de chambre
     * @return la ou les réservation(s) pour ce type de chambre entre les dates sous forme d'un ensemble
     * @throws NullPointerException si un argument est null
     * @throws IllegalArgumentException si l'ordre temporel d1 avant d2 n'est pas respecté.
     *
     * Ne devrait pas retourner un objet null, par contre peut être un ensemble qui est vide.
     */    
    public Set<Reservation> getReservation(LocalDate d1, LocalDate d2, TypeChambre t){
        System.out.println("ne sera pas implanté");
        return null;
    }
    
    
    /**
     * Cherche le <b>nombre moyen</b> de chambres disponibles d'un certain type entre deux date (réservées ou non), arrondies à l'entier inférieur.
     * @param  d1 une date
     * @param  d2 une date
     * @param  t un type de chambre
     * @return un entier
     * @throws NullPointerException si un argument est null
     * @throws IllegalArgumentException si l'ordre temporel d1 avant d2 n'est pas respecté.
     *
     * Ne devrait pas retourner un entier négatif.
     */    
    public int getDisponibles(LocalDate d1, LocalDate d2, TypeChambre t){
        System.out.println("ne sera pas implanté");
        return 1;
    }
    

    /**
     * Cherche la <b>proportion moyenne</b> de chambres disponibles pour une date (réservées sur réservables).
     * @param  d1 une date
     * @param  d2 une date
     * @return un entier entre 0 et 100
     * @throws NullPointerException si un argument est null
     */    
    public int getRatio(LocalDate d1, LocalDate d2){
        System.out.println("ne sera pas implanté");
        return 1;
    }
    

    /**
     * Cherche la <b>proportion moyenne</b> de chambres disponibles d'un certain type pour une date (réservées sur réservables).
     * @param  d1 une date
     * @param  d2 une date
     * @param  t un type de chambre
     * @return un entier entre 0 et 100
     * @throws NullPointerException si un argument est null
     */    
    public int getRatio(LocalDate d1, LocalDate d2, TypeChambre t){
        System.out.println("ne sera pas implanté");
        return 1;
    }
    
    
}
