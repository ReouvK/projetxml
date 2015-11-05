/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetxml.controller;

import projetxml.exception.InputException;
import projetxml.exception.SystemException;
import projetxml.model.DbInterface;
import projetxml.model.DownloadOmdb;
import projetxml.model.DownloadRSS;
import projetxml.model.InfosTitre;
import projetxml.model.XMLManagement;
import projetxml.view.View;
import projetxml.view.ViewInterface;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.jdom2.JDOMException;

/**
 *
 * @author Réouven KIDOUCHIM
 */
public class Controller {
    
    /**
     *
     * @param v
     * @param m
     * @param xml
     * @throws SystemException
     * @throws JDOMException
     * @throws IOException
     * @throws Exception
     */
    public void run(ViewInterface v, DbInterface m, XMLManagement xml) throws SystemException, JDOMException, IOException, Exception {
        try {         
            //Telechargement auto du rss (marche pas, pb d'encodage)
            //v.showMsgDwnAutoRss();
            //new DownloadRSS().createXML();
            
            //ajout des fichier dans la BDD et mise à jour
            v.showMsgInitializeDb();
            m.useDefaultDb();
            v.showMsgUpdateDb();
            m.refreshDb();
            v.showMsgExtractSuccess();
        } catch (SystemException ex) {
            v.showDbError();
        }
        while (true) {
            try {
                //Affichage du menu
                v.showMainMenu();
                //execution d'une commande grâce au menu d'utilisateur
                switch (v.inputCommand()) {
                    //Extraire les données de la BDD en créant un fichier XML par serie
                    case View.CHOICE_EXTRACT:
                        if (m.getDatabases() == null){
                            v.showDbEmpty();
                        }else{
                            v.showMsgExtractProgress();
                            //TODO: Telechargement auto des torrents
                            //On compte le nombre de serie dans la BDD
                            int nbSeries = Integer.parseInt(m.executeQuery("count (//item/title)"));
                            //On parcoure tous les noms des series en input
                            for (String s : xml.getSeries()){
                                //On s'apprete à tester le nom de chaque serie en input avec celui de chaque serie dans le fichier RSS
                                for(int i=1;i<=nbSeries;i++){
                                    //On récupère le i-ème titre de la BDD
                                    String serieRSS = m.executeQuery("//item[position()="+i+"]/title/text()");
                                    //Si le titre input est contenu dans celui de la BDD, alors on traite
                                    if(m.executeQuery("contains(//item/title[.='"+serieRSS+"']/text(),'"+s+"')").contains("t")){
                                        //Si le fichier n'existe pas, alors on en crée un
                                        if (!xml.existeFichier(s))
                                            xml.creerFichier(s);                                                                                                            

                                        //Extraction des informations plus précises sur la serie (episode, saison, qualite)
                                        Map<String,String> infosXml = xml.getInfos(m.executeQuery("//item[title='"+serieRSS+"']"));
                                        InfosTitre it = new InfosTitre(infosXml.get("title"));
                                        infosXml.put("season", it.getSaison());
                                        infosXml.put("episode", it.getEpisode());
                                        infosXml.put("quality", it.getQualite());

                                        //On applique le traitement pour alimenter le fichier xml
                                        v.showOmdbProgress();
                                        xml.execute(s,infosXml,new DownloadOmdb(s).getInfos());
                                    }
                                }
                            }
                            //Message de succès
                            v.showMsgExtractSuccess();
                        }
                        break;
                    case View.CHOICE_SHOW_SERIES:
                        //Pour chaque fichier, ouvrir avec bloc note
                        File dir = new File(xml.getPath());
                        //On récupère la liste des fichiers contenus dans l'output
                        String[] s = dir.list();
                        if(s.length == 0){
                            v.showOpenError();
                            break;
                        }
                        //On les parcoure
                        for(int i = 0;i<s.length;i++){
                            //On teste si chaque fichier est un repertoire
                            //Car un output est forcement dans un repertoire
                            File dirT = new File(dir+"\\"+s[i]);
                            if(dirT.isDirectory()){
                                //Alors on l'affiche
                                Desktop.getDesktop().open(new File(xml.getPath()+"/"+s[i]+"/"+s[i]+".xml"));
                            }
                        }
                        break;
                    case View.CHOICE_SHOW_INPUT:
                        //Afficher le fichier d'entrée
                        Desktop.getDesktop().open(new File(xml.getFileName()));
                        break;
                    case View.CHOICE_EXIT:
                        //Quitter (Message)
                        v.inputString(View.MESSAGE_EXIT);
                        return;
                }
            } catch (InputException ex) {
                v.showInputError(ex);
            }
        }        
    }
}
