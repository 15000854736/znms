package info.zznet.znms.softdog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateValueProcessor implements JsonValueProcessor{

	
	 private String format ="yyyy-MM-dd HH:mm:ss";  
     
	    public JsonDateValueProcessor() {  
	        super();  
	    }  
	      
	    public JsonDateValueProcessor(String format) {  
	        super();  
	        this.format = format;  
	    }  
	  
	
		@Override
		public Object processArrayValue(Object object, JsonConfig arg1) {
			return process("",object);
		}
	
		@Override
		public Object processObjectValue(String fieldName, Object value, JsonConfig arg2) {
			return process(fieldName,value);
		}
	
		private Object process(String fieldName,Object value){
			if(fieldName.equals("isEnable")){
				if((Integer)value==1){
				   return "启用";
				}else{
				  return "禁用";
				}
			}
			
			
			if(fieldName.equals("innerVlan")){
				if(value==null){
				   return "";
				}else{
				  return value;
				}
			}
			
			
			if(fieldName.equals("outerVlan")){
				if(value==null){
				   return "";
				}else{
				  return value;
				}
			}
			
	        if(value instanceof Date){    
	            SimpleDateFormat sdf = new SimpleDateFormat(format);    
	            return sdf.format(value);  
	        }    
	        return value == null ? "" : value.toString();    
	    }  
	 
}
