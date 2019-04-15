package io.gjf.serializer;

/**
 * Create by GuoJF on 2019/4/15
 */
public interface Serializer {

    byte[] serialize(Object object);

    Object deserialize(byte[] values);



}
