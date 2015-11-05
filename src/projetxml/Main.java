/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetxml;

import projetxml.config.configLoader;
import projetxml.controller.Controller;
import projetxml.exception.SystemException;
import projetxml.exception.configException;
import projetxml.model.DbInterface;
import projetxml.model.DbManagement;
import projetxml.model.XMLManagement;
import projetxml.view.View;
import projetxml.view.ViewInterface;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.JDOMException;

/**
 *
 * @author Réouven KIDOUCHIM
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SystemException, JDOMException, IOException, Exception {
        try {
            /* Load the config */
            configLoader config = new configLoader(configLoader.CONFIG_PATH);
            /* Intialize View, Model and Controller */
            ViewInterface v = new View();
            
            /* The XQueryModel requires a database name and te path to the folder containing the xml */
            DbInterface dbManagement = new DbManagement(config.getProp(configLoader.DATABASE), config.getProp(configLoader.DB_PATH));
            XMLManagement xml = new XMLManagement(config.getProp(configLoader.FILE_INPUT), config.getProp(configLoader.XML_PATH_OUT));
            Controller c = new Controller();

            /* Run the application */
            c.run(v, dbManagement, xml);
        } catch (configException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
