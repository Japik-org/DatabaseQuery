package com.japik.utils.databasequery.reqimpl;

import com.japik.utils.RunnableThrow;
import com.japik.utils.databasequery.req.IOperationRequest;
import com.japik.utils.databasequery.req.OnResolveQueryException;
import lombok.Getter;

public class OperationRequest implements IOperationRequest, ISetResolved<Boolean> {
    @Getter
    private boolean isResolved;

    private RunnableThrow onResolveRunnable = null;

    @Override
    public IOperationRequest onResolve(RunnableThrow runnable) {
        this.onResolveRunnable = runnable;
        return null;
    }

    @Override
    public IOperationRequest onResolveOrNow(RunnableThrow runnable) throws OnResolveQueryException {
        onResolveRunnable = runnable;

        if (isResolved) {
            try {
                onResolveRunnable.run();
                onResolveRunnable = null;

            } catch (OnResolveQueryException onResolveRequestException) {
                throw onResolveRequestException;

            } catch (Throwable throwable) {
                throw new OnResolveQueryException(throwable);
            }
        }

        return this;
    }

    @Override
    public void setResolved(Boolean resolved) throws OnResolveQueryException {
        isResolved = resolved;
        if (onResolveRunnable != null && resolved) {
            try {
                onResolveRunnable.run();
                onResolveRunnable = null;

            } catch (OnResolveQueryException onResolveRequestException) {
                throw onResolveRequestException;

            } catch (Throwable throwable) {
                throw new OnResolveQueryException(throwable);
            }
        }
    }
}
