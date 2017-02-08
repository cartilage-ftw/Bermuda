package org.cartilage.bermuda;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.cartilage.bermuda.net.ConnectionHandler;

import java.util.logging.Logger;

/**
 * Initiates the server
 * @author Cartilage
 */
public class Server {

	/**
	 * The logger for the server logs
	 */
	private final Logger logger = Logger.getLogger(Server.class.getName());

	/**
	 * The main method that starts the program
	 * @param args	The command-line arguments
	 */
	public static void main(String[] args) {
		new Server().start();
	}

	/**
	 * Consturcts this class
	 */
	public Server() {
		logger.info("Starting Bermuda server...");
	}

	/**
	 * Initiatlizes the server
	 */
	public void start() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							socketChannel.pipeline().addLast(new ConnectionHandler());
						}
					});
			ChannelFuture future = bootstrap.bind(8000).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
