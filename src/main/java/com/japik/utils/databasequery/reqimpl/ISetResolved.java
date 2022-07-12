package com.japik.utils.databasequery.reqimpl;

import com.japik.utils.databasequery.req.OnResolveQueryException;

import java.rmi.RemoteException;

public interface ISetResolved<T> {
    void setResolved(T value) throws RemoteException, OnResolveQueryException;
}
