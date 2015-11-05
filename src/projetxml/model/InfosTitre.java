/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetxml.model;

import java.util.regex.*;

/**
 *
 * @author Réouven KIDOUCHIM
 */
public class InfosTitre {
    
    String titre;
    Pattern p;    
    
    /**
     *
     * @param titre
     */
    public InfosTitre(String titre){
        this.titre = titre;        
        
    }
    
    /**
     *
     * @return
     */
    public String getEpisode(){
        this.p = Pattern.compile("((S|s)([0-9][0-9])(E|e)([0-9][0-9]))|(E|e)pisode\\s([0-9][0-9])?");
        Matcher m = p.matcher(this.titre);
        
        if(m.find()){
            if(m.group(5) == null)
                return m.group(7);
            if(m.group(7) == null)
                return m.group(5);
        }
        return "N/A";
    }
    
    /**
     *
     * @return
     */
    public String getSaison(){
        this.p = Pattern.compile("((S|s)([0-9][0-9])(E|e)([0-9][0-9]))|(S|s)eason\\s([0-9]([0-9]?))?");
        Matcher m = p.matcher(this.titre);
        
        if(m.find()){
            if(m.group(3) == null)
                return m.group(7);
            if(m.group(7) == null)
                return m.group(3);
        }
        return "N/A";
    }
    
    /**
     *
     * @return
     */
    public String getQualite(){
        this.p = Pattern.compile("([0-9]?[0-9][0-9][0-9])p|(H|h)(D|d)(T|t)(V|v)((R|r)(I|i)(P|p))?");
        Matcher m = p.matcher(this.titre);
        
        if(m.find())
            return m.group(0);        
        return "N/A";
    }
    
}
