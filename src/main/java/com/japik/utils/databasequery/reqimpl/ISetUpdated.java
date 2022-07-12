package com.japik.utils.databasequery.reqimpl;

import com.japik.utils.databasequery.req.OnUpdateQueryException;

public interface ISetUpdated {
    void setUpdated(boolean updated) throws OnUpdateQueryException;
}
