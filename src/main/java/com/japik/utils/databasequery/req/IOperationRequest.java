package com.japik.utils.databasequery.req;

import com.japik.utils.RunnableThrow;

import java.rmi.RemoteException;

public interface IOperationRequest {
    boolean isResolved() throws RemoteException;
    IOperationRequest onResolve(RunnableThrow runnable) throws RemoteException;
    IOperationRequest onResolveOrNow(RunnableThrow runnable) throws RemoteException, OnResolveQueryException;
}
