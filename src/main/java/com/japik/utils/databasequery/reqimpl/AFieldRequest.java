package com.japik.utils.databasequery.reqimpl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AFieldRequest <T> {
    @Getter
    protected final String key;

    protected T value;

    public final T getValueRaw() {
        return value;
    }

    public final void setValueRaw(T value) {
        this.value = value;
    }
}
