package io.gjf.protocol;

import io.gjf.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Create by GuoJF on 2019/4/15
 */
public class ObjectDecoder extends MessageToMessageDecoder<ByteBuf> {

    private Serializer serializer;


    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {


        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);

        Object obj = serializer.deserialize(bytes);

        out.add(obj);


    }


    public ObjectDecoder(Serializer serializer) {
        this.serializer = serializer;


    }
}
