package com.japik.utils.databasequery.reqimpl;

public interface IReqResolver <T> {
    IReqResolver<T> resolve(T o) throws Throwable;
}
