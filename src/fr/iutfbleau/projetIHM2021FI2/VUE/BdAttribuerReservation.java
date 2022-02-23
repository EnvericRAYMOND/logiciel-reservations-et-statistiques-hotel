package fr.iutfbleau.projetIHM2021FI2.VUE;
/**
*La classe <code>Affichage</code>Affichage gère l'affichage du jeu.
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
 * Classe qui permet de modifier le numero de chambre d'une reservation dans la base de donnée 
 */
public class BdAttribuerReservation {
    /**
     * Méthode qui permet de modifier le numero de chambre d'une reservation dans la base de donnée 
     *@param numChambre
     *@param reference
     */
    public BdAttribuerReservation(int numChambre,String reference){
    
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            Connection cnx = DriverManager.getConnection("retrait login+mdp de la base de donnees pour GitHub", "", "");

            PreparedStatement pst = cnx.prepareStatement("UPDATE Reservations SET chambre=? WHERE reference=?");

            pst.setInt(1, numChambre);//args[0]

	    pst.setString(2, reference);//args[1]

            ResultSet rs = pst.executeQuery();

            cnx.close();

        } catch (ClassNotFoundException c) {
            System.err.println(c);
            System.exit(2);
        } catch (SQLException s) {
            System.err.println(s);
            System.exit(2);
        }
    }
}
