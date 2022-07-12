package com.japik.utils.databasequery.reqimpl;

import com.japik.utils.ConsumerThrow;
import com.japik.utils.databasequery.req.DatabaseQueryException;
import com.japik.utils.databasequery.req.ISetFieldRequest;
import com.japik.utils.databasequery.req.ObjectNotFoundException;
import com.japik.utils.databasequery.req.OnUpdateQueryException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.rmi.RemoteException;

@RequiredArgsConstructor
public final class SetFieldRequest<T> implements ISetFieldRequest<T>, ISetUpdated {
    private final IFieldRequestCallback callback;
    @Getter
    @NotNull
    private final String key;
    @Getter
    @Nullable
    private T value = null;
    @Getter
    private boolean isUpdated = false;
    @Nullable
    private ConsumerThrow<ISetFieldRequest<T>> onUpdateConsumer = null;

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
}
