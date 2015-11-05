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

/**
 *
 * @author Réouven KIDOUCHIM
 */
public class DownloadRSS extends DownloadAbs {
    
    String data;
    
    /**
     *
     * @throws IOException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public DownloadRSS() throws IOException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{        
        
        this.data = this.downloadFile("https://kat.cr/tv/?rss=xml");    
        
    }
    
    /**
     * Create a new XML file about RSS
     * @throws IOException
     */
    public void createXML() throws IOException{
        File f = new File("kat.xml");
        f.createNewFile();
        
        FileWriter fw = new FileWriter(f);  
        BufferedWriter output = new BufferedWriter(fw);
        output.write(this.data);
        output.flush();
        output.close();
    }
}
