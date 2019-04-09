package com.sise.idea.client;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.remoting.exchange.Request;
import com.alibaba.dubbo.remoting.transport.netty.NettyClient;
import com.sise.idea.channel.NettyChannel;
import com.sise.idea.common.SysException;
import com.sise.idea.em.ExceptionEnums;
import com.sise.idea.handler.SendReceiveHandler;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

import java.util.concurrent.TimeUnit;

/**
 * 简单化之后的netty客户端
 *
 * @author idea
 * @data 2019/4/7
 */
public class NettySimpleClient extends NettyTransportClient {

    private NettySimpleClient nettySimpleClient;

    public NettySimpleClient(URL url) {
        super(url, new SendReceiveHandler());
    }


    public void doConnect() {
        ChannelFuture future = bootstrap.connect(getConnectAddress());
        boolean ret = future.awaitUninterruptibly(timeout, TimeUnit.MILLISECONDS);
        if (ret && future.isSuccess()) {
            Channel newChannel = future.getChannel();
            newChannel.setInterestOps(Channel.OP_READ_WRITE);
            NettySimpleClient.this.channel = future.getChannel();
        } else {
            throw new SysException(ExceptionEnums.SERVER_CONNECT_FAIL);
        }
    }

    public void send(Request req) throws RemotingException {

        NettyChannel ch = NettyChannel.getOrAddChannel(this.channel, url, handler);

        ch.send(req);

    }

}
