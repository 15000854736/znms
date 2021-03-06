package info.zznet.znms.web.module.systemLog.collector;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.base.entity.SystemLog;
import info.zznet.znms.base.util.DateUtil;
import info.zznet.znms.base.util.KafkaUtil;
import info.zznet.znms.web.WebRuntimeData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

@Sharable
public class NettyHandler extends
		SimpleChannelInboundHandler<DatagramPacket> {

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ZNMSLogger.error(cause);
		cause.printStackTrace();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ZNMSLogger.info("Netty service started, ready to receivce message");
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket obj)
			throws Exception {
		DatagramPacket dp = (DatagramPacket) obj;
		ByteBuf buf = (ByteBuf) dp.copy().content();
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		CollectorUtil.HexStringTobytes(CollectorUtil.byteArrayToHEXString(req));
		String str = new String(req, "UTF-8");
		ZNMSLogger.debug("data gram:" + str);
		String hostIp = dp.sender().getAddress().toString().replace("/", "");
		Integer pri = Integer.parseInt(StringUtils.substringBetween(str, "<", ">"));
		Facility facility = CollectorUtil.parseFacility(pri);
		Severity severity = CollectorUtil.parseSeverity(pri);
		String msg = StringUtils.substringAfterLast(str, ":");
		SystemLog syslog = convertToEntity(facility, severity, hostIp, msg);
		if(LogRuler.doFilter(syslog)) {
			// 入库
			LogPersister.instance.addLog(syslog);
			// 发送到kafka
			KafkaUtil.sendMessage(JSONObject.fromObject(syslog), SystemConstants.SYSTEM_LOG_TOPIC);
		}
	}
	
	private SystemLog convertToEntity(Facility facility, Severity severity, String hostIp, String msg){
		SystemLog syslog = new SystemLog();
		syslog.setFacilityId(facility.getCode());
		syslog.setFacilityName(facility.name());
		syslog.setPriorityId(severity.getCode());
		syslog.setPriorityName(severity.getText());
		Date now = new Date();
		syslog.setLogTime(new Date());
		syslog.setLogTimeStr(DateUtil.dateToStr(now, DateUtil.DF_yyyyMMddHHmmss));
		syslog.setMessage(msg);
		Host host = WebRuntimeData.instance.getHost(hostIp);
		if(host != null){
			syslog.setHost(host);
			syslog.setHostId(host.getId());
			syslog.setHostIp(hostIp);
			syslog.setHostName(host.getHostName());
		}
		return syslog;
	}

}
