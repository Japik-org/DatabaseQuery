package com.japik.utils.databasequery.reqimpl;

import com.japik.utils.ConsumerThrow;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class ReqResolver <T> implements IReqResolver<T> {
    @NotNull
    private final IReqResolver<T> anotherResolver;
    private final ConsumerThrow<T> consumer;

    @Override
    public IReqResolver<T> resolve(T o) throws Throwable {
        consumer.accept(o);
        return anotherResolver.resolve(o);
    }
}
