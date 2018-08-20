package com.appleframework.monitor.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
/**
 * json工具类
 */
public class JsonUtils {

	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	private static ObjectMapper mapper = new ObjectMapper();

	static{
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		//mapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		SimpleModule byteModule = new SimpleModule();
		byteModule.addSerializer(byte[].class, new ByteSerializer());
		byteModule.addDeserializer(byte[].class, new ByteDeserializer());
		mapper.registerModule(byteModule);
		SimpleModule intModule = new SimpleModule();
		intModule.addDeserializer(int[].class, new IntDeserializer());
		mapper.registerModule(intModule);
	}


	/**
	 * Object可以是POJO，也可以是Collection或数组。 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".
	 */
	public static String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			logger.warn("write to json string error:" + object, e);
			return null;
		}
	}

	/**
	 * 反序列化POJO或简单Collection如List<String>.
	 * 
	 * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String, JavaType)
	 * 
	 * @see #fromJson(String, JavaType)
	 */
	public static <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}
	
	public static <T> T fromJson(String jsonString,TypeReference<T> jsonTypeReference) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		try {
			return mapper.readValue(jsonString, jsonTypeReference);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}
	
	public static Map<String,Object> fromJson(String jsonString){
		JavaType map = buildMapType(HashMap.class,String.class,Object.class);
		return fromJson(jsonString,map);
	}
	
	public static Map<String,String> mapStrFromJson(String jsonString){
		JavaType map = buildMapType(HashMap.class,String.class,String.class);
		return fromJson(jsonString,map);
	}

	public static TypeReference<Map<String,Object>> newMapTypeReference(){
		return new TypeReference<Map<String,Object>>(){};
	}
	/**
	 * 反序列化复杂Collection如List<Bean>, contructCollectionType()或contructMapType()构造类型, 然后调用本函数.
	 * 
	 * @see #createCollectionType(Class, Class...)
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String jsonString, JavaType javaType) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		try {
			return (T) mapper.readValue(jsonString, javaType);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * 构造Collection类型.
	 */
	@SuppressWarnings("rawtypes")
	public  static JavaType buildCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
		return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
	}

	/**
	 * 构造Map类型.
	 */
	@SuppressWarnings("rawtypes")
	public static JavaType buildMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
		return mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
	}

	/**
	 * 輸出JSONP格式數據.
	 */
	public static String toJsonP(String functionName, Object object) {
		return toJson(new JSONPObject(functionName, object));
	}

	/**
	 * 設定是否使用Enum的toString函數來讀寫Enum, 為False時時使用Enum的name()函數來讀寫Enum, 默認為False. 注意本函數一定要在Mapper創建後, 所有的讀寫動作之前調用.
	 */
	public void enableEnumUseToString() {
		mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
	}
	
	public static class ByteDeserializer extends JsonDeserializer<byte[]>{
		
		protected final String _parseString(JsonParser jp, DeserializationContext ctxt) throws IOException
	    {
	        JsonToken t = jp.getCurrentToken();
	        if (t == JsonToken.VALUE_STRING) {
	            return jp.getText();
	        }
	        
	        // Issue#381
	        if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
	            jp.nextToken();
	            final String parsed = _parseString(jp, ctxt);
	            if (jp.nextToken() != JsonToken.END_ARRAY) {
	                throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, 
	                        "Attempted to unwrap single value array for single 'String' value but there was more than a single value in the array");
	            }            
	            return parsed;            
	        }
	        String value = jp.getValueAsString();
	        if (value != null) {
	            return value;
	        }
	        throw ctxt.mappingException(String.class, jp.getCurrentToken());
	    }
		
		
		@Override
		public byte[] deserialize(JsonParser jp, DeserializationContext ctxt)throws IOException, JsonProcessingException {
			if (jp.isExpectedStartArrayToken()) {
				final ObjectBuffer buffer = ctxt.leaseObjectBuffer();
		        Object[] chunk = buffer.resetAndStart();
		        int ix = 0;
		        try {
		            while (true) {
		                String value = jp.nextTextValue();
		                if (value == null) {
		                    JsonToken t = jp.getCurrentToken();
		                    if (t == JsonToken.END_ARRAY) {
		                        break;
		                    }
		                    if (t != JsonToken.VALUE_NULL) {
		                        value = _parseString(jp, ctxt);
		                    }
		                }
		                if (ix >= chunk.length) {
		                    chunk = buffer.appendCompletedChunk(chunk);
		                    ix = 0;
		                }
		                chunk[ix++] = value;
		            }
		        } catch (Exception e) {
		            throw JsonMappingException.wrapWithPath(e, chunk, buffer.bufferedSize() + ix);
		        }
		        String[] bytes =  buffer.completeAndClearBuffer(chunk, ix, String.class);
			    ctxt.returnObjectBuffer(buffer);
			    return StringUtil.parseStrByte(StringUtil.StringToByteStr(bytes));
			}else{
				String content = jp.getText() ;
				if(StringUtils.isNotBlank(content)){
					if(StringUtil.isArrayStr(content)){
						return StringUtil.parseStrByte(content);
					}else{
						return StringUtil.parseStrByte("["+content+"]");
					}
				}
			}
			return null;
		}
	}
	
	public static class ByteSerializer extends JsonSerializer<byte[]>{
		@Override
		public void serialize(byte[] value, JsonGenerator gen,SerializerProvider serializers) throws IOException,JsonProcessingException {
			if(value==null){
				gen.writeString("");  
			}else{
				gen.writeString(StringUtil.byteToStr(value));  
			}
		}
	}
	
	public static class IntDeserializer extends JsonDeserializer<int[]>{
		
		protected final String _parseString(JsonParser jp, DeserializationContext ctxt) throws IOException
	    {
	        JsonToken t = jp.getCurrentToken();
	        if (t == JsonToken.VALUE_STRING) {
	            return jp.getText();
	        }
	        
	        // Issue#381
	        if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
	            jp.nextToken();
	            final String parsed = _parseString(jp, ctxt);
	            if (jp.nextToken() != JsonToken.END_ARRAY) {
	                throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, 
	                        "Attempted to unwrap single value array for single 'String' value but there was more than a single value in the array");
	            }            
	            return parsed;            
	        }
	        String value = jp.getValueAsString();
	        if (value != null) {
	            return value;
	        }
	        throw ctxt.mappingException(String.class, jp.getCurrentToken());
	    }
		
		@Override
		public int[] deserialize(JsonParser jp, DeserializationContext ctxt)throws IOException, JsonProcessingException {
			if (jp.isExpectedStartArrayToken()) {
				final ObjectBuffer buffer = ctxt.leaseObjectBuffer();
		        Object[] chunk = buffer.resetAndStart();
		        int ix = 0;
		        try {
		            while (true) {
		                String value = jp.nextTextValue();
		                if (value == null) {
		                    JsonToken t = jp.getCurrentToken();
		                    if (t == JsonToken.END_ARRAY) {
		                        break;
		                    }
		                    if (t != JsonToken.VALUE_NULL) {
		                        value = _parseString(jp, ctxt);
		                    }
		                }
		                if (ix >= chunk.length) {
		                    chunk = buffer.appendCompletedChunk(chunk);
		                    ix = 0;
		                }
		                chunk[ix++] = value;
		            }
		        } catch (Exception e) {
		            throw JsonMappingException.wrapWithPath(e, chunk, buffer.bufferedSize() + ix);
		        }
		        String[] bytes =  buffer.completeAndClearBuffer(chunk, ix, String.class);
			    ctxt.returnObjectBuffer(buffer);
			    return StringUtil.parseStrInt(StringUtil.StringToIntStr(bytes));
			}else{
				String content = jp.getText() ;
				if(StringUtils.isNotBlank(content)){
					if(StringUtil.isArrayStr(content)){
						return StringUtil.parseStrInt(content);
					}else{
						return StringUtil.parseStrInt("["+content+"]");
					}
				}
			}
			return null;
		}
	}
	
	public static class IntSerializer extends JsonSerializer<int[]>{
		@Override
		public void serialize(int[] value, JsonGenerator gen,SerializerProvider serializers) throws IOException,JsonProcessingException {
			if(value==null){
				gen.writeString("");  
			}else{
				gen.writeString(StringUtil.intToStr(value));  
			}
		}
	}
	
}

