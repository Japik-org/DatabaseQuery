package com.japik.utils.databasequery.req;

import java.rmi.RemoteException;

public interface IQueryMaker {
    /**
     * Request all data in one request
     * @throws ObjectNotFoundException
     * @throws DatabaseQueryException
     * @throws OnResolveQueryException
     */
    void queryGet() throws RemoteException, ObjectNotFoundException, DatabaseQueryException, OnResolveQueryException;

    /**
     * Save all changes in one request
     * @throws ObjectNotFoundException
     * @throws DatabaseQueryException
     * @throws OnUpdateQueryException
     */
    void queryUpdate() throws RemoteException, ObjectNotFoundException, DatabaseQueryException, OnUpdateQueryException;

    /**
     * Insert as new record
     * @throws ObjectAlreadyExistsException
     * @throws DatabaseQueryException
     */
    void queryInsert() throws RemoteException, ObjectAlreadyExistsException, DatabaseQueryException;
}
