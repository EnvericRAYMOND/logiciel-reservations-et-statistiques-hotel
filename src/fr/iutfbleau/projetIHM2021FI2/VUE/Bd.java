package fr.iutfbleau.projetIHM2021FI2.VUE;
/**
*La classe <code>Bd</code>gère la création des réservations.
*@version 1.0
*@author Desvilles Mattis & Raymond Envric 
*/

import fr.iutfbleau.projetIHM2021FI2.API.*;
import fr.iutfbleau.projetIHM2021FI2.APIUTIL.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.sql.*;
import java.util.*;

/**
 * Classe qui gère l'interaction avec la base de donnée et qui crée les objets de type réservation
 */
public class Bd {

    private String[] res;
    private int compteur;
    private DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Méthode qui gere l'interraction avec la bd
     */
    public Bd(PrereservationFactory preres,ReservationFactory r){
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            Connection connex = DriverManager.getConnection("retrait login+mdp de la base de donnees pour GitHub","","");
            PreparedStatement pst = connex.prepareStatement("SELECT COUNT(*) FROM Reservations");
            ResultSet rs = pst.executeQuery();
            rs.next();
            res=new String[Integer.parseInt(rs.getString(1))+1];
            PreparedStatement pst2 = connex.prepareStatement("SELECT reference FROM Reservations");
            ResultSet rs2 = pst2.executeQuery();
            compteur=0;
            while(rs2.next()){
                compteur++;
                if(compteur<Integer.parseInt(rs.getString(1))){
                   // System.out.println("res : "+rs2.getString(1));
                    res[compteur] =  rs2.getString(1);
                }
            }
            connex.close();
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
        Prereservation p;
        Chambre c;
        for (int y=1;y<compteur;y++){
            p=preres.getPrereservation(res[y]);
            
            c=r.getChambre(p);
            r.createReservation(p,c);
        }        
    }
}
