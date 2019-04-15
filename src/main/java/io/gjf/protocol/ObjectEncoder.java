package io.gjf.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;


import java.util.List;

/**
 * Create by GuoJF on 2019/4/15
 */
public class ObjectEncoder extends MessageToMessageDecoder<Object> {

    private Serializer serializer;

    public ObjectEncoder(Serializer serializer) {
        this.serializer = serializer;
    }

    protected void decode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {

        byte[] bytes = serializer.serialize(msg);

        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(bytes);
        out.add(buf);


    }
}
