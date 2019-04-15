package io.gjf.protocol;

import io.gjf.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;


import java.util.List;

/**
 * Create by GuoJF on 2019/4/15
 */
public class ObjectEncoder extends MessageToMessageEncoder<Object> {

    private Serializer serializer;

    public ObjectEncoder(Serializer serializer) {
        this.serializer = serializer;
    }


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, List<Object> list) throws Exception {
        byte[] bytes = serializer.serialize(o);

        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(bytes);
        list.add(buf);
    }
}
