package info.zznet.znms.base.rrd.ds.impl;

import info.zznet.znms.base.rrd.ds.RrdDataChannel;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component("testChannel")
public class TestChannel implements RrdDataChannel{

	@Override
	public double[] getData(String dataId) {
		return new double[]{2d, 4d};
	}

}
