package com.japik.utils.databasequery.reqimpl;

import com.japik.utils.ConsumerThrow;
import com.japik.utils.databasequery.req.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.rmi.RemoteException;

@RequiredArgsConstructor
public final class FieldRequest <T> implements IFieldRequest<T>,
        ISetResolved<T>, ISetUpdated {

    private final IFieldRequestCallback callback;
    @Getter
    @NotNull
    private final String key;

    @Getter
    private T value = null;
    @Getter
    private boolean isUpdated = false;
    @Getter
    private boolean isResolved = false;
    @Nullable
    private ConsumerThrow<IGetFieldRequest<T>> onResolveConsumer = null;
    @Nullable
    private ConsumerThrow<ISetFieldRequest<T>> onUpdateConsumer = null;

    @Override
    public void setUpdated(boolean updated) throws OnUpdateQueryException {
        isUpdated = true;
        if (onUpdateConsumer != null) {
            try {
                onUpdateConsumer.accept(this);
                onUpdateConsumer = null;

            } catch (OnUpdateQueryException onUpdateRequestException) {
                throw onUpdateRequestException;

            } catch (Throwable throwable) {
                throw new OnUpdateQueryException(throwable);
            }
        }
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

    @Override
    public T resolveAndGetValue() throws RemoteException, OnResolveQueryException, DatabaseQueryException, ObjectNotFoundException {
        callback.queryGet();
        return value;
    }

    @Override
    public ISetFieldRequest<T> setValue(T value) {
        this.value = value;
        callback.registerUpdate(this);
        return this;
    }

    @Override
    public ISetFieldRequest<T> setValueAndUpdate(T value) throws RemoteException, OnUpdateQueryException, DatabaseQueryException, ObjectNotFoundException {
        setValue(value);
        callback.queryUpdate();
        return this;
    }

    @Override
    public IGetFieldRequest<T> onResolve(ConsumerThrow<IGetFieldRequest<T>> consumer) {
        this.onResolveConsumer = consumer;
        return this;
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
    public ISetFieldRequest<T> onUpdate(ConsumerThrow<ISetFieldRequest<T>> consumer) {
        this.onUpdateConsumer = consumer;
        return this;
    }

    @Override
    public ISetFieldRequest<T> onUpdateOrNow(ConsumerThrow<ISetFieldRequest<T>> consumer) throws OnUpdateQueryException {
        this.onUpdateConsumer = consumer;

        if (isUpdated) {
            try {
                onUpdateConsumer.accept(this);
                this.onUpdateConsumer = null;

            } catch (OnUpdateQueryException onUpdateRequestException) {
                throw onUpdateRequestException;

            } catch (Throwable throwable) {
                throw new OnUpdateQueryException(throwable);
            }
        }

        return this;
    }
}
