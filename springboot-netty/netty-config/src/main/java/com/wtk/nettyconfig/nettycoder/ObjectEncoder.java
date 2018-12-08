package com.wtk.nettyconfig.nettycoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;

/**
 * @program: springboot-all
 * @description: 传输对象编码
 * @author: wangtengke
 * @create: 2018-12-07
 **/

public class ObjectEncoder<T> extends MessageToByteEncoder<T> {
    private Class<T> genericClass;
    public ObjectEncoder(Class<T> genericClass) {
        super(genericClass);
        this.genericClass = genericClass;
    }


    @Override
    protected void encode(ChannelHandlerContext ctx, T msg, ByteBuf out) throws Exception {
        Schema<T> schema =  RuntimeSchema.createFrom(genericClass);
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] data = ProtostuffIOUtil.toByteArray(msg, schema, buffer);
        out.writeBytes(data);
    }
}
