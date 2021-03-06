package info.zznet.znms.base.constants;

public enum HostWeight {
	EXPORT(1, 1),
	CORE(2, 1),
	WIRELESS_CONTROLLER(3, 1),
	INTERFACE(4, 1),
	CLUSTER(5, 1),
	ETC(6, 1);
	
	int type;
	int weight;
	
	HostWeight(int type, int weight){
		this.type = type;
		this.weight = weight;
	}
	
	public static int getWeight(int type){
		for(HostWeight hw : HostWeight.values()){
			if(type==hw.type){
				return hw.weight;
			}
		}
		return 1;
	}
}
