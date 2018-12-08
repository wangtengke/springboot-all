package com.springboot.rpcserver.config.nettycoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.List;

/**
 * @program: springboot-all
 * @description: 传出对象解码
 * @author: wangtengke
 * @create: 2018-12-07
 **/
public class ObjectDecoder<T> extends ByteToMessageDecoder {

    private Class<T> genericClass;

    public ObjectDecoder(Class<T> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] data = new byte[in.readableBytes()];
        in.readBytes(data);
        T job = genericClass.newInstance();
        Schema<T> schema = RuntimeSchema.getSchema(genericClass);
        ProtostuffIOUtil.mergeFrom(data, job, schema);
        out.add(job);
    }
}
