package info.zznet.znms.base.util;

import java.io.IOException;
import java.io.InputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class XstreamUtil {
	public static <T> T Xml2Bean(InputStream input, Class<T> clazz)  
            throws IOException {  
  
        XStream xstream = new XStream(new DomDriver()){
        	@Override
        	protected MapperWrapper wrapMapper(MapperWrapper next){
        		return new MapperWrapper(next){
        			@Override
        			public boolean shouldSerializeMember(Class definedIn, String fieldName){
        				if(definedIn == Object.class){
        					try {
        						return this.realClass(fieldName) != null;
        					} catch (Exception e){
        						return false;
        					}
        				} else {
        					return super.shouldSerializeMember(definedIn, fieldName);
        				}
        			}
        		};
        	}
        };  
  
        xstream.processAnnotations(clazz);  
  
        return (T) xstream.fromXML(input);  
    }  
	
	public static <T> T Xml2Bean(String text, Class<T> clazz){
		XStream xstream = new XStream(new DomDriver()){
        	@Override
        	protected MapperWrapper wrapMapper(MapperWrapper next){
        		return new MapperWrapper(next){
        			@Override
        			public boolean shouldSerializeMember(Class definedIn, String fieldName){
        				if(definedIn == Object.class){
        					try {
        						return this.realClass(fieldName) != null;
        					} catch (Exception e){
        						return false;
        					}
        				} else {
        					return super.shouldSerializeMember(definedIn, fieldName);
        				}
        			}
        		};
        	}
		};  
		  
        xstream.processAnnotations(clazz);  
        return (T)xstream.fromXML(text);
	}
}
