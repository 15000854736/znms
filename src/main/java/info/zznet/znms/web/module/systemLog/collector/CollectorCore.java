package info.zznet.znms.web.module.systemLog.collector;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.util.ConfigUtil;
import info.zznet.znms.web.util.PathUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;

import org.springframework.stereotype.Component;

@Component
public class CollectorCore {
	EventLoopGroup group;
	
	public void init() {
		if(group != null && !group.isShutdown()) {
			return;
		}
		WebRuntimeData webRuntimeData = WebRuntimeData.instance;
		if(!webRuntimeData.getSystemOptionBean().isUseStatus()){
			return;
		}
		new Thread() {
			public void run() {
				group = new NioEventLoopGroup();
				try {
					Bootstrap b = new Bootstrap();
					b.group(group).channel(NioDatagramChannel.class)
							.option(ChannelOption.SO_BROADCAST, true)
							.handler(new ChannelInitializer<DatagramChannel>() {
								@Override
								protected void initChannel(DatagramChannel ch)
										throws Exception {
									ch.pipeline().addLast(
											new NettyHandler());
								}
							});
					try {
						String HOST = ConfigUtil.getString("znms.ip");
						int PORT = 514;
						ZNMSLogger.info(" System log collector started---->IP:" + HOST
								+ " PORT:" + PORT);
						b.bind(HOST, PORT).sync().channel().closeFuture()
								.sync();
					} catch (InterruptedException e) {
						ZNMSLogger.error(e);
					}
				} finally {
					group.shutdownGracefully();
				}
			}
		}.start();
	}
	
	public void shutdown(){
		if(group != null) {
			group.shutdownGracefully();
			group = null;
			ZNMSLogger.info("System log collector shutdown");
		}
	}
}
