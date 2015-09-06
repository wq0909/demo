package com.netease.yixin.publicaccount.datang.util;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {

	private static Logger logger = LoggerFactory.getLogger(JsonConverter.class);

	public static <T> T mapToObject(Class<T> tClass, String responseBody) throws IOException, JsonParseException,
			JsonMappingException {
		if (logger.isDebugEnabled()) {
			logger.debug("get response :" + responseBody);
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.readValue(responseBody, tClass);
	}

	public static <T> List<T> mapToList(final Class<T> tClass, String responseBody) throws IOException,
			JsonParseException, JsonMappingException {
		if (logger.isDebugEnabled()) {
			logger.debug("get response :" + responseBody);
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.readValue(responseBody, new TypeReference<List<T>>() {
			@Override
			public Type getType() {
				return new ParameterizedType() {
					@Override
					public Type[] getActualTypeArguments() {
						return new Type[] { tClass };
					}

					@Override
					public Type getRawType() {
						return List.class;
					}

					@Override
					public Type getOwnerType() {
						return null;
					}
				};
			}
		});
	}

}
