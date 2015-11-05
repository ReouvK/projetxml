/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetxml.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Réouven KIDOUCHIM
 */
public abstract class DownloadAbs {
    
    /**
     *
     * @param link
     * @return
     * @throws IOException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public String downloadFile(String link) throws IOException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{        
        try{
            //TODO:Enlever caractères spéciaux du titre
            //Remplacer espace par %20
            //http://omdbapi.com/
            /* paramètre t pour le titre r pour indiquer que l'on veut du xml */            

            // transformation du String en url
            URL url = new URL(link);
            //connexion
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //si la connexion n'a pas pu avoir lieu, on lance une exception
            //200 est le code pour dire que la connexio est OK (404 pour dire que la page est introuvable)
            if (conn.getResponseCode() != 200) {
                throw new IOException(conn.getResponseMessage());
            }
            
            
            //Initialisation du BufferReader pour lire la réponse HTTP
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            //initialisation du constructeur de chaîne de caractère
            StringBuilder sb = new StringBuilder();
            String line;
            //on transforme les lignes en chaînes de caractère
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            // on clos la connexion
            br.close();
            conn.disconnect();

            //on affiche le xml retournée
            return sb.toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(DownloadOmdb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DownloadOmdb.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return "";
    }
}
