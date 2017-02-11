package org.cartilage.bermuda.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles connection requests
 *
 * @author Cartilage
 */
public class ConnectionHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		//TODO: Handle the message
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
		throw new Exception("An error occured while handling a message", cause);
	}
}
