package com.cz.spring_boot_rabbitmq.stream.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.function.context.catalog.FunctionTypeUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.util.MimeType;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MyApplicationJsonMessageMarshallingConverter extends MyMappingJackson2MessageConverter{


    private final Field headersField;

    private final Map<Type, JavaType> typeCache = new ConcurrentHashMap<>();

    public MyApplicationJsonMessageMarshallingConverter(@Nullable ObjectMapper objectMapper) {
        if (objectMapper != null) {
            this.setObjectMapper(objectMapper);
        }
        this.headersField = ReflectionUtils.findField(MessageHeaders.class, "headers");
        this.headersField.setAccessible(true);
    }

    @Override
    protected Object convertToInternal(Object payload, @Nullable MessageHeaders headers,
                                       @Nullable Object conversionHint) {
        if (payload instanceof byte[]) {
            return payload;
        }
        else if (payload instanceof String) {
            return ((String) payload).getBytes(StandardCharsets.UTF_8);
        }
        else {
            return super.convertToInternal(payload, headers, conversionHint);
        }
    }

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, @Nullable Object hint) {
        Object conversionHint = hint;
        Object result = null;
        if (conversionHint instanceof MethodParameter) {
            Class<?> conversionHintType = ((MethodParameter) conversionHint)
                    .getParameterType();
            if (Message.class.isAssignableFrom(conversionHintType)) {
                /*
                 * Ensures that super won't attempt to create Message as a result of
                 * conversion and stays at payload conversion only. The Message will
                 * eventually be created in
                 * MessageMethodArgumentResolver.resolveArgument(..)
                 */
                conversionHint = null;
            }
            else if (((MethodParameter) conversionHint)
                    .getGenericParameterType() instanceof ParameterizedType) {
                ParameterizedTypeReference<Object> forType = ParameterizedTypeReference
                        .forType(((MethodParameter) conversionHint)
                                .getGenericParameterType());
                result = convertParameterizedType(message, forType.getType());
            }
        }
        else if (conversionHint instanceof ParameterizedTypeReference) {
            result = convertParameterizedType(message, ((ParameterizedTypeReference<?>) conversionHint).getType());
        }
        else if (conversionHint instanceof ParameterizedType) {
            result = convertParameterizedType(message, (Type) conversionHint);
        }
        if (result == null) {
            if (message.getPayload() instanceof byte[]
                    && targetClass.isAssignableFrom(String.class)) {
                result = new String((byte[]) message.getPayload(),
                        StandardCharsets.UTF_8);
            }
            else {
                result = super.convertFromInternal(message, targetClass, conversionHint);
            }
        }

        return result;
    }

    private Object convertParameterizedType(Message<?> message, Type conversionHint) {
        ObjectMapper objectMapper = this.getObjectMapper();
        Object payload = message.getPayload();
        try {
            JavaType type = this.typeCache.get(conversionHint);
            if (type == null) {
                conversionHint = FunctionTypeUtils.isMessage(conversionHint)
                        ? FunctionTypeUtils.getImmediateGenericType(conversionHint, 0)
                        : conversionHint;
                type = objectMapper.getTypeFactory()
                        .constructType(conversionHint);
                this.typeCache.put(conversionHint, type);
            }
            if (payload instanceof byte[]) {
                return objectMapper.readValue((byte[]) payload, type);
            }
            else if (payload instanceof String) {
                return objectMapper.readValue((String) payload, type);
            }
            else {
                final JavaType typeToUse = type;
                if (payload instanceof Collection) {
                    Collection<?> collection = (Collection<?>) ((Collection<?>) payload).stream()
                            .map(value -> {
                                try {
                                    if (value instanceof byte[]) {
                                        return objectMapper.readValue((byte[]) value, typeToUse.getContentType());
                                    }
                                    else if (value instanceof String) {
                                        return objectMapper.readValue((String) value, typeToUse.getContentType());
                                    }
                                    else {
                                        // fall back to simple type-conversion
                                        // see https://github.com/spring-cloud/spring-cloud-stream/issues/1898
                                        return objectMapper.convertValue(value, typeToUse.getContentType());
                                    }
                                }
                                catch (Exception e) {
                                    logger.error("Failed to convert payload " + value, e);
                                }
                                return null;
                            }).collect(Collectors.toList());

                    return collection;
                }
                return null;
            }
        }
        catch (IOException e) {
            throw new MessageConversionException("Cannot parse payload ", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    protected MimeType getMimeType(@Nullable MessageHeaders headers) {
        Object contentType = headers.get(MessageHeaders.CONTENT_TYPE);
        if (contentType instanceof byte[]) {
            contentType = new String((byte[]) contentType, StandardCharsets.UTF_8);
            contentType = ((String) contentType).replace("\"", "");
            Map<String, Object> headersMap = (Map<String, Object>) ReflectionUtils.getField(this.headersField, headers);
            headersMap.put(MessageHeaders.CONTENT_TYPE, contentType);
        }
        return super.getMimeType(headers);
    }
}
