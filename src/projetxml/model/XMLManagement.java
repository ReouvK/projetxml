/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetxml.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Réouven KIDOUCHIM
 */
public class XMLManagement {
    
    String nomFichier;
    String pathOutput;
    Document documentInput;
    Document documentOutput;
    Element racine;
    List<Element> listSeries;
    
    /**
     *
     * @param nomFichier
     * @param pathOutput
     * @throws FileNotFoundException
     * @throws JDOMException
     * @throws IOException
     */
    public XMLManagement(String nomFichier, String pathOutput) throws FileNotFoundException, JDOMException, IOException{
        this.nomFichier = nomFichier;
        this.pathOutput = pathOutput;
        //Ouvrir fichier input
        this.documentInput = new SAXBuilder().build(new File(nomFichier));
        this.racine = this.documentInput.getRootElement();
        this.listSeries = racine.getChildren("serie");
        
    }    
    
    /**
     *
     * @return
     */
    public List<String> getSeries(){
        
        List<String> series = new ArrayList<>();
        Iterator i = listSeries.iterator();
        
        //Parcourir nom des series fichier input
        while(i.hasNext())
        {
           Element courant = (Element)i.next();
           series.add(courant.getText());
        }
        
        return series;
    }
    
    /**
     *
     * @param nomFichier
     * @return true if the given file exists
     */
    public boolean existeFichier(String nomFichier){        
        if(new File(this.pathOutput+"\\"+nomFichier).exists())
            return true;
        return false;
        
    }
    
    /**
     * Create file that we'll put datas about the serie
     * This method creates also the xsd and the xsl equivalent
     * @param fichier
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void creerFichier(String fichier) throws FileNotFoundException, IOException{
        
        Element racine = new Element("episodes");
        documentOutput = new Document(racine);
        //Creation du repertoire de la serie                
        new File(this.pathOutput+"\\"+fichier).mkdir();
        //Ajout des fichier xsl et xsd
        copier(this.pathOutput+"\\output.xsd", this.pathOutput+"\\"+fichier+"\\output.xsd");
        copier(this.pathOutput+"\\output.xsl", this.pathOutput+"\\"+fichier+"\\output.xsl");
        //Ajout de la balise de lien pour le xsl
        Map instructions = new HashMap();
        instructions.put("href", "output.xsl");
        instructions.put("type", "text/xsl");
        ProcessingInstruction pi = new ProcessingInstruction("xml-stylesheet", instructions);
        documentOutput.getContent().add(0, pi);
 
        sauverFichier(fichier);
    }    
    
    /**
     *
     * @param nomFichier
     * @param infos
     * @param infosOmdb
     * @throws Exception
     */
    public void execute(String nomFichier, Map<String,String> infos, Map<String,String> infosOmdb) throws Exception{        
        //On lit ensuite le fichier
        lireFichier(nomFichier);
        //On ajoute les données dans le fichier output
        ajouterDonnees(nomFichier.replaceAll(".xml", ""), infos, infosOmdb);
        //On sauvegarde ensuite
        sauverFichier(nomFichier);
    }
    
    //On parse le fichier et on initialise la racine de
    //notre arborescence
    private void lireFichier(String nomFichier) throws Exception
    {
        SAXBuilder sxb = new SAXBuilder();
        documentOutput = sxb.build(new File(this.pathOutput+"\\"+nomFichier+"\\"+nomFichier+".xml"));
        racine = documentOutput.getRootElement();
    }
   
    private void sauverFichier(String nomFichier){
        try
        {
           XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
           String rep = nomFichier.replaceAll(".xml", "");
           sortie.output(documentOutput, new FileOutputStream(this.pathOutput+"\\"+nomFichier+"\\"+nomFichier+".xml"));
        }
        catch (java.io.IOException e){}        
    }

   private void ajouterDonnees(String nomFichier, Map<String,String> infos, Map<String,String> infosOmdb){
       Element racine = documentOutput.getRootElement();              
       Namespace xsi = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
       racine.addNamespaceDeclaration(xsi);
       racine.setAttribute("noNamespaceSchemaLocation", "input.xsd", xsi);                     
       
       Element episode = new Element("episode");
       racine.addContent(episode);
       
       Element titre = new Element("titre");
       titre.setText(nomFichier);
       episode.addContent(titre);

       Element titreComplet = new Element("titreComplet");
       titreComplet.setText(infos.get("title"));
       episode.addContent(titreComplet);
       
       Element numero = new Element("numero");
       numero.setText(infos.get("episode"));
       episode.addContent(numero);
       
       Element saison = new Element("saison");
       saison.setText(infos.get("season"));
       episode.addContent(saison);
       
       Element qualite = new Element("qualite");
       qualite.setText(infos.get("quality"));
       episode.addContent(qualite);
       
       Element lien = new Element("lien");
       lien.setText(infos.get("link"));
       episode.addContent(lien);
       
       Element note = new Element("note");
       note.setText(infosOmdb.get("rated"));
       episode.addContent(note);
       
       Element description = new Element("description");
       description.setText(infosOmdb.get("plot"));
       episode.addContent(description);
       
       Element image = new Element("image");
       image.setText(infosOmdb.get("poster"));
       episode.addContent(image);       
       
       Element torrent = new Element("torrent");
       episode.addContent(torrent);
       
       Element infoHash = new Element("infoHash");
       infoHash.setText(infos.get("infoHash"));
       torrent.addContent(infoHash);
       
       Element magnetURI = new Element("magnetURI");
       magnetURI.setText(infos.get("magnetURI"));
       torrent.addContent(magnetURI);
       
       Element seeds = new Element("seeds");
       seeds.setText(infos.get("seeds"));
       torrent.addContent(seeds);
       
       Element peers = new Element("peers");
       peers.setText(infos.get("peers"));
       torrent.addContent(peers);
       
       Element verified = new Element("verified");
       verified.setText(infos.get("verified"));
       torrent.addContent(verified);
       
       Element filename = new Element("filename");
       filename.setText(infos.get("filename"));
       torrent.addContent(filename);
       
       Element enclosure = new Element("enclosure");
       enclosure.setText(infos.get("enclosure"));
       torrent.addContent(enclosure);
       
   }
   
    /**
     *
     * @return
     */
    public String getPath(){
        return this.pathOutput;
    }
   
    /**
     *
     * @return
     */
    public String getFileName(){
        return this.nomFichier;
    }
   
    /**
     * return map about serie information
     * @param infos
     * @return map
     * @throws JDOMException
     * @throws IOException
     */
    public Map<String,String> getInfos(String infos) throws JDOMException, IOException{        
        
        File f = new File("intermediaire.xml");
        f.createNewFile();
        //Récupération de la liste des attributs
        Map<String, String> informations = new HashMap<>();
        //Ajout du string
        //Conversion sous la forme d'un xml
        try{
            FileWriter fw = new FileWriter(f);  
            BufferedWriter output = new BufferedWriter(fw);
            output.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");
            output.write(infos);
            output.flush();
            output.close();
        }catch(IOException e){}
        //Récupération de la balise movie
        Document document = new SAXBuilder().build(f);
        
        for(Element e : document.getRootElement().getChildren()){
            informations.put(e.getName(), e.getText());
        }
        
        //Suppresion du file
        f.delete();
        //Récupération de tous les attributs sous la forme d'une liste
        
        return informations;        
    }
   
    //Copie les fichiers xsl et xsd dans le dossier de la serie nouvellement créée
    private static void copier(String fichier_source, String fichier_dest) throws IOException {
        FileInputStream src = new FileInputStream(fichier_source);
        FileOutputStream dest = new FileOutputStream(fichier_dest);

        FileChannel inChannel = src.getChannel();
        FileChannel outChannel = dest.getChannel();

        for (ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
            inChannel.read(buffer) != -1;
            buffer.clear()) {
            buffer.flip();
            while (buffer.hasRemaining()) outChannel.write(buffer);
        }

        inChannel.close();
        outChannel.close();
    }   
}
