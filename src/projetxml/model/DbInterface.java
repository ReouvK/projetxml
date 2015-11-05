/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetxml.model;

import projetxml.exception.SystemException;

/**
 *
 * @author Réouven KIDOUCHIM
 */

public interface DbInterface {
    
    /**
     *
     * @param db
     * @throws SystemException
     */
    void deleteDb(String db) throws SystemException;

    /**
     *
     * @param query
     * @return
     * @throws SystemException
     */
    String executeQuery(String query) throws SystemException;

    /**
     *
     * @return
     * @throws SystemException
     */
    String getDatabases() throws SystemException;

    /**
     *
     * @param collectionName
     * @return
     * @throws SystemException
     */
    String getElementsInCollection(String collectionName) throws SystemException;

    /**
     *
     * @param dbName
     * @return
     * @throws SystemException
     */
    String openDb(String dbName) throws SystemException;

    /**
     *
     * @return
     * @throws SystemException
     */
    String createDatabase() throws SystemException;

    /**
     *
     * @param file
     * @throws SystemException
     */
    void removeXML(String file) throws SystemException;

    /**
     *
     * @param path
     * @throws SystemException
     */
    void addXMLToDb(String path) throws SystemException;

    /**
     *
     * @throws SystemException
     */
    void refreshDb() throws SystemException;

    /**
     *
     * @return
     * @throws SystemException
     */
    String useDefaultDb() throws SystemException;
    
    /**
     *
     * @return
     */
    public String getPath();
    
}
