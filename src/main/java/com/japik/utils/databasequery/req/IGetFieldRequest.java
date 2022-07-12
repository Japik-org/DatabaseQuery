package com.japik.utils.databasequery.req;

import com.japik.utils.ConsumerThrow;

import java.rmi.RemoteException;

public interface IGetFieldRequest<T> {
    boolean isResolved() throws RemoteException;
    IGetFieldRequest<T> onResolve(ConsumerThrow<IGetFieldRequest<T>> consumer) throws RemoteException;
    IGetFieldRequest<T> onResolveOrNow(ConsumerThrow<IGetFieldRequest<T>> consumer) throws RemoteException, OnResolveQueryException;
    T getValue() throws RemoteException;
    T resolveAndGetValue() throws RemoteException, OnResolveQueryException, DatabaseQueryException, ObjectNotFoundException;
}
