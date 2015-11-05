/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetxml.model;

import projetxml.exception.SystemException;
import java.io.File;
import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.*;

/**
 *
 * @author Réouven KIDOUCHIM
 */
public class DbManagement implements DbInterface {
    
    private String dbName;
    private String path;
    private Context context;

    /**
     * Construtor
     *
     * @param dbName name of the database
     * @param path path or url to the database
     */
    public DbManagement(String dbName, String path) {
        //creating database
        context = new Context();
        this.dbName = dbName;
        this.path = path;
    }    

    /**
     * create a database based on the path and with name given in the
     * constructor
     *
     * @return
     * @throws SystemException
     */
    @Override
    public String createDatabase() throws SystemException {
        try {
//            new CreateDB(dbName, path).execute(context);
            /*create a database*/
            new CreateDB(dbName).execute(context);
            //System.out.println(dbName);
            return dbName;
        } catch (BaseXException ex) {
            throw new SystemException("Error on opening Database");
        }
    }

    /**
     * add the xml files of the basic path and delete the old files
     *
     * @throws SystemException
     */
    @Override
    public void refreshDb() throws SystemException {
        addXMLToDb(path);
        removeFiles(path);
    }

    /**
     * add xml file to the database
     *
     * @param path path to the xml
     * @throws SystemException
     */
    @Override
    public void addXMLToDb(String path) throws SystemException {
        try {
            /* add all the xml files that are in the path*/
            new Add("", path).execute(context);
            new Optimize().execute(context);
        } catch (BaseXException ex) {            
            //System.out.println(ex);
//            throw new SystemException("Error on adding files");
        }
    }

    /**
     * remove a xml file from the database
     *
     * @param file file name
     * @throws SystemException
     */
    @Override
    public void removeXML(String file) throws SystemException {
        try {
            new Delete(file).execute(context);
        } catch (BaseXException ex) {
            throw new SystemException("Error on removing a file");
        }
    }

    /**
     * list all the databases
     *
     * @return @throws SystemException
     */
    @Override
    public String getDatabases() throws SystemException {
        try {
            return new List().execute(context);
        } catch (BaseXException ex) {
            throw new SystemException("Error on getting List");
        }
    }

    /**
     * get all the files in a collection
     *
     * @param collectionName
     * @return list of the files
     * @throws SystemException
     */
    @Override
    public String getElementsInCollection(String collectionName) throws SystemException {
        try {
            return new XQuery(
                    "for $doc in collection('" + collectionName + "')"
                    + "return <doc path='{ base-uri($doc) }'/>"
            ).execute(context);
        } catch (BaseXException ex) {
            throw new SystemException("Query Exception");
        }
    }

    /**
     * open a database to prepare a query
     *
     * @param dbName nameof the database
     * @return the nameof the database if opening succeed
     * @throws SystemException
     */
    @Override
    public String openDb(String dbName) throws SystemException {
        try {
            new Open(dbName).execute(context);
            return dbName;
        } catch (BaseXException ex) {
            throw new SystemException("Error on opening DB");
        }
    }

    /**
     * execute the Query
     *
     * @param query query in Xquery language
     * @return resuts
     * @throws SystemException
     */
    @Override
    public String executeQuery(String query) throws SystemException {
        try {
            return new XQuery(query).execute(context);
        } catch (BaseXException ex) {
            throw new SystemException("Query Exception");
        }
    }

    /**
     * delete the specified database or if param==null, the database given in
     * the constructor
     *
     * @param db databaseName
     * @throws SystemException
     */
    @Override
    public void deleteDb(String db) throws SystemException {
        try {
            if (db.isEmpty()) {
                db = dbName;
            }
            new DropDB(db).execute(context);
        } catch (BaseXException ex) {
            throw new SystemException("Error on deleting database");
        }
    }

    /**
     * will select the default database, if it does not exists, create it
     *
     * @return database name
     * @throws SystemException
     */
    @Override
    public String useDefaultDb() throws SystemException {
        try {
            return openDb(dbName);
        } catch (SystemException ex) {
            return createDatabase();
        }
    }

    private void removeFiles(String path) {
        File f = new File(path);
        if (f.exists()) {
            File[] files = f.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    removeFiles(f + "\\" + file);
                }
                file.delete();
            }
        }
    }
    
    /**
     *
     * @return
     */
    public String getPath(){
        return this.path;
    }    
    
}
