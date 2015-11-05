/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetxml.view;

import projetxml.exception.InputException;

/**
 *
 * @author Réouven KIDOUCHIM
 */
public interface ViewInterface {
    
    /**
     * Choice to exit on the menu
     */
    public static final String CHOICE_EXIT = "0";   

    /**
     * Choice to extract datas on the menu
     */
    public static final String CHOICE_EXTRACT = "1";

    /**
     * Choice to show series on the menu
     */
    public static final String CHOICE_SHOW_SERIES = "2";

    /**
     * Choice to show input file on the menu
     */
    public static final String CHOICE_SHOW_INPUT = "3";

    /**
     * Message to exit
     */
    public static final String MESSAGE_EXIT = "A bientot!";

    /**
     * Message to download
     */
    public static final String MENU_DWNLD = "Téléchargement auto.";     

    /**
     * Message indicates error
     */
    public static final String MESSAGE_ERROR = "=== Erreur de saisie ===\n\n";         

    /**
     *
     * @return
     * @throws InputException
     */
    String inputCommand() throws InputException;

    /**
     *
     * @param s
     */
    void inputString(String s);

    /**
     *
     * @param e
     */
    void showInputError(Exception e);
    
    /**
     *
     */
    void showDwnError();

    /**
     *
     */
    void showMainMenu(); 

    /**
     *
     */
    void showMsgExtractSuccess();
    
    /**
     *
     */
    void showMsgExtractProgress();
    
    /**
     *
     */
    void showOpenError();
    
    /**
     *
     */
    void showMsgInitializeDb();
    
    /**
     *
     */
    void showMsgUpdateDb();
    
    /**
     *
     */
    void showDbError();
    
    /**
     *
     */
    void showMsgDwnAutoRss();
    
    /**
     *
     */
    void showOmdbProgress();
    
    /**
     * 
     */
    void showDbEmpty();
}
