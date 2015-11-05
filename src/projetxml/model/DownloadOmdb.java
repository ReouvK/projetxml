/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetxml.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Réouven KIDOUCHIM
 */
public class DownloadOmdb extends DownloadAbs {
    
    String infos;
    
    /**
     *
     * @param nomSerie
     * @throws MalformedURLException
     * @throws IOException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public DownloadOmdb(String nomSerie) throws MalformedURLException, IOException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        nomSerie = nomSerie.replaceAll("[\\p{Punct}]","");
        nomSerie = nomSerie.replaceAll(" ","%20");
        
        this.infos = this.downloadFile("http://www.omdbapi.com/?t="+nomSerie+"&r=xml");
    }
    
    /**
     * return map about Omdb information
     * @return map about Omdb information
     * @throws JDOMException
     * @throws IOException
     */
    public Map<String,String> getInfos() throws JDOMException, IOException {
        //Récupération de la liste des attributs
        Map<String, String> informations = new HashMap<>();
        //On creer un fichier qui sert de relai pour stocker
        //temporairement les informations
        //C'est plus simple comme ça
        File f = new File("intermediaire.xml");
        //Ajout du string
        //Conversion sous la forme d'un xml
        try{
            FileWriter fw = new FileWriter(f);  
            BufferedWriter output = new BufferedWriter(fw);
            output.write(this.infos);
            output.flush();
            output.close();
        }catch(IOException e){}
        //Récupération de la balise movie
        Document document = new SAXBuilder().build(f);
        //Il se peut que l'url ne contienne pas de "movie"
        //Il va donc lever une exception
        //On l'attrape donc pour eviter que l'appli plante
        try{
            Element movie = document.getRootElement().getChild("movie");                                
            for(Attribute a : movie.getAttributes()){
                informations.put(a.getName(),a.getValue());
            }        
        }catch(Exception e){}
        //Suppresion du file
        f.delete();
        //Récupération de tous les attributs sous la forme d'une map        
        return informations;
    }
}

