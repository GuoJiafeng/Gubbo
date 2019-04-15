package io.gjf.serializer;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

/**
 * Creat by GuoJF on MAC
 */
public class JDKSerializer implements Serializer {


    @Override
    public byte[] serialize(Object object) {
        return SerializationUtils.serialize((Serializable) object);
    }

    @Override
    public Object deserialize(byte[] values) {
        return SerializationUtils.deserialize(values);
    }
}
