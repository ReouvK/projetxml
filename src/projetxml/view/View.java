/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetxml.view;

import projetxml.exception.InputException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Réouven KIDOUCHIM
 */

public class View implements ViewInterface {
    
    BufferedReader bufferRead;
    
    /**
     * constructor initialize the buffer reader
     */
    public View() {
        bufferRead = new BufferedReader(new InputStreamReader(System.in));
    }          
    
    /**
     *
     */
    @Override
    public void showMainMenu() {
        System.out.println("Bienvenue dans le gestionnaire de séries!\nQue désirez-vous faire?");
        System.out.println("1. Extraire les séries");
        System.out.println("2. Afficher les séries existantes");        
        System.out.println("3. Modifier le fichier d'entrée");        
        System.out.println("0. Exit");
    }
    
    /**
     *
     */
    @Override
    public void showDwnError() {
        System.out.println("=== Téléchargement annulé ===");
        System.out.println("=== Erreur ===");
    }    
    
    /**
     *
     */
    @Override
    public void showMsgExtractSuccess(){
        System.out.println("=== L'opération s'est déroulée avec succès ===");
    }
        
    /**
     *
     */
    @Override
    public void showMsgInitializeDb(){
        System.out.println("Initialisation de la base de donnée...");
    }
        
    /**
     *
     */
    @Override
    public void showMsgUpdateDb(){
        System.out.println("Mise a jour de la base de donnée...");
    }
    
    /**
     *
     */
    @Override
    public void showMsgDwnAutoRss(){
        System.out.println("Téléchargement automatique du rss...");
    }    
    
    /**
     *
     */
    @Override
    public void showMsgExtractProgress(){
        System.out.println("Extraction des données en cours...");
    }
    
    /**
     *
     * @return
     * @throws InputException
     */
    @Override
    public String inputCommand() throws InputException {
        try {
            return bufferRead.readLine();
        } catch (IOException ex) {
            throw new InputException(ex.toString());
        }
    }
    
    /**
     *
     * @param e
     */
    @Override
    public void showInputError(Exception e) {
        System.out.println("\n\n=== Input Error ===");        
        System.out.println("\n\n=== Trace End ===");
    }
    
    /**
     *
     * @param s
     */
    @Override
    public void inputString(String s){
        System.out.println(s);
    }
    
    /**
     *
     */
    public void showOpenError(){
        System.out.println("Il n'y a aucun fichier à afficher!");
    }
    
    /**
     * show the database error
     */
    @Override
    public void showDbError() {
        System.out.println("=== Ouverture annulée ===");
        System.out.println("=== Base de données inconue ===");
    }
    
    /**
     *
     */
    @Override
    public void showOmdbProgress() {
        System.out.println("Extraction des données depuis Omdb...");
    }
    
    /**
     * 
     */
    public void showDbEmpty(){
        System.out.println("La base de donnée est vide!\nOpération annulée");
    }

}
