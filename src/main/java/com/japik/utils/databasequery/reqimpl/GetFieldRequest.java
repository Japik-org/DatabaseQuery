package com.japik.utils.databasequery.reqimpl;

import com.japik.utils.ConsumerThrow;
import com.japik.utils.databasequery.req.DatabaseQueryException;
import com.japik.utils.databasequery.req.IGetFieldRequest;
import com.japik.utils.databasequery.req.ObjectNotFoundException;
import com.japik.utils.databasequery.req.OnResolveQueryException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.rmi.RemoteException;

@RequiredArgsConstructor
public final class GetFieldRequest<T> implements IGetFieldRequest<T>, ISetResolved<T> {
    private final IFieldRequestCallback callback;
    @Getter
    @NotNull
    private final String key;
    @Getter
    @Nullable
    private T value = null;
    @Getter
    private boolean isResolved = false;
    @Nullable
    private ConsumerThrow<IGetFieldRequest<T>> onResolveConsumer = null;

    @Override
    public IGetFieldRequest<T> onResolve(ConsumerThrow<IGetFieldRequest<T>> consumer) {
        this.onResolveConsumer = consumer;
        return this;
    }

    @Override
    public T resolveAndGetValue() throws RemoteException, OnResolveQueryException, DatabaseQueryException, ObjectNotFoundException {
        callback.queryGet();
        return value;
    }

    @Override
    public IGetFieldRequest<T> onResolveOrNow(ConsumerThrow<IGetFieldRequest<T>> consumer) throws OnResolveQueryException {
        this.onResolveConsumer = consumer;

        if (isResolved) {
            try {
                onResolveConsumer.accept(this);
                this.onResolveConsumer = null;

            } catch (OnResolveQueryException onResolveRequestException) {
                throw onResolveRequestException;

            } catch (Throwable throwable) {
                throw new OnResolveQueryException(throwable);
            }
        }

        return this;
    }

    @Override
    public void setResolved(T value) throws OnResolveQueryException {
        this.value = value;
        isResolved = true;
        if (onResolveConsumer != null) {
            try {
                onResolveConsumer.accept(this);
                onResolveConsumer = null;

            } catch (OnResolveQueryException onResolveRequestException) {
                throw onResolveRequestException;

            } catch (Throwable throwable) {
                throw new OnResolveQueryException(throwable);
            }
        }
    }
}
