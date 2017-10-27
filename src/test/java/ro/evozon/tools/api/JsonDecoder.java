package ro.evozon.tools.api;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

public class JsonDecoder {
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String json, Class<T> beanClass) {
		JsonValue value = Json.createReader(new StringReader(json)).read();
		return (T) decode(value, beanClass);
	}

	private static Object decode(JsonValue jsonValue, Type targetType) {
		if (jsonValue.getValueType() == ValueType.NULL) {
			return null;
		} else if (jsonValue.getValueType() == ValueType.TRUE || jsonValue.getValueType() == ValueType.FALSE) {
			return decodeBoolean(jsonValue, targetType);
		} else if (jsonValue instanceof JsonNumber) {
			return decodeNumber((JsonNumber) jsonValue, targetType);
		} else if (jsonValue instanceof JsonString) {
			return decodeString((JsonString) jsonValue, targetType);
		} else if (jsonValue instanceof JsonArray) {
			return decodeArray((JsonArray) jsonValue, targetType);
		} else if (jsonValue instanceof JsonObject) {
			return decodeObject((JsonObject) jsonValue, targetType);
		} else {
			throw new UnsupportedOperationException("Unsupported json value: " + jsonValue);
		}
	}

	private static Object decodeBoolean(JsonValue jsonValue, Type targetType) {
		if (targetType == boolean.class || targetType == Boolean.class) {
			return Boolean.valueOf(jsonValue.toString());
		} else {
			throw new UnsupportedOperationException("Unsupported boolean type: " + targetType);
		}
	}

	private static Object decodeNumber(JsonNumber jsonNumber, Type targetType) {
		if (targetType == int.class || targetType == Integer.class) {
			return jsonNumber.intValue();
		} else if (targetType == long.class || targetType == Long.class) {
			return jsonNumber.longValue();
		} else {
			throw new UnsupportedOperationException("Unsupported number type: " + targetType);
		}
	}

	private static Object decodeString(JsonString jsonString, Type targetType) {
		if (targetType == String.class) {
			return jsonString.getString();
		} else if (targetType == Date.class) {
			try {
				return new SimpleDateFormat("MMM dd, yyyy H:mm:ss a", Locale.ENGLISH).parse(jsonString.getString()); // This
																														// is
																														// default
																														// Gson
																														// format.
																														// Alter
																														// if
																														// necessary.
			} catch (ParseException e) {
				throw new UnsupportedOperationException("Unsupported date format: " + jsonString.getString());
			}
		} else {
			throw new UnsupportedOperationException("Unsupported string type: " + targetType);
		}
	}

	private static Object decodeArray(JsonArray jsonArray, Type targetType) {
		Class<?> targetClass = (Class<?>) ((targetType instanceof ParameterizedType)
				? ((ParameterizedType) targetType).getRawType()
				: targetType);

		if (List.class.isAssignableFrom(targetClass)) {
			Class<?> elementClass = (Class<?>) ((ParameterizedType) targetType).getActualTypeArguments()[0];
			List<Object> list = new ArrayList<>();

			for (JsonValue item : jsonArray) {
				list.add(decode(item, elementClass));
			}

			return list;
		} else if (targetClass.isArray()) {
			Class<?> elementClass = targetClass.getComponentType();
			Object array = Array.newInstance(elementClass, jsonArray.size());

			for (int i = 0; i < jsonArray.size(); i++) {
				Array.set(array, i, decode(jsonArray.get(i), elementClass));
			}

			return array;
		} else {
			throw new UnsupportedOperationException("Unsupported array type: " + targetClass);
		}
	}

	private static Object decodeObject(JsonObject object, Type targetType) {
		Class<?> targetClass = (Class<?>) ((targetType instanceof ParameterizedType)
				? ((ParameterizedType) targetType).getRawType()
				: targetType);

		if (Map.class.isAssignableFrom(targetClass)) {
			Class<?> valueClass = (Class<?>) ((ParameterizedType) targetType).getActualTypeArguments()[1];
			Map<String, Object> map = new LinkedHashMap<>();

			for (Entry<String, JsonValue> entry : object.entrySet()) {
				map.put(entry.getKey(), decode(entry.getValue(), valueClass));
			}

			return map;
		} else
			try {
				Object bean = targetClass.newInstance();

				for (PropertyDescriptor property : Introspector.getBeanInfo(targetClass).getPropertyDescriptors()) {
					if (property.getWriteMethod() != null && object.containsKey(property.getName())) {
						property.getWriteMethod().invoke(bean, decode(object.get(property.getName()),
								property.getWriteMethod().getGenericParameterTypes()[0]));
					}
				}

				return bean;
			} catch (Exception e) {
				throw new UnsupportedOperationException("Unsupported object type: " + targetClass, e);
			}
	}

}
//Usage:
//
//User u = YourJsonUtil.fromJson(jsonString, User.class);
