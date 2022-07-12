package com.japik.utils.databasequery.reqimpl;

import com.japik.utils.databasequery.req.IQueryMaker;

public interface IFieldRequestCallback extends IQueryMaker {
    <T> void registerUpdate(SetFieldRequest<T> request);
    <T> void registerUpdate(FieldRequest<T> request);
}
