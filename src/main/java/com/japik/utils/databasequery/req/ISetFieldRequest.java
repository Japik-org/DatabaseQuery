package com.japik.utils.databasequery.req;

import com.japik.utils.ConsumerThrow;

import java.rmi.RemoteException;

public interface ISetFieldRequest<T> {
    ISetFieldRequest<T> onUpdate(ConsumerThrow<ISetFieldRequest<T>> consumer) throws RemoteException;
    ISetFieldRequest<T> onUpdateOrNow(ConsumerThrow<ISetFieldRequest<T>> consumer) throws RemoteException, OnUpdateQueryException;
    ISetFieldRequest<T> setValue(T value) throws RemoteException;
    ISetFieldRequest<T> setValueAndUpdate(T value) throws RemoteException, OnUpdateQueryException, DatabaseQueryException, ObjectNotFoundException;
    boolean isUpdated() throws RemoteException;
}
