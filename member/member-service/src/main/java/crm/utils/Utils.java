package crm.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.type.CollectionType;

import lombok.experimental.UtilityClass;

/**
 * Utility methods.
 */
@UtilityClass
public class Utils {
    private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("GMT");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final ObjectMapper SERIALIZE_MAPPER = new ObjectMapper();
    
    static {
        SERIALIZE_MAPPER.setDefaultTyping(
                new StdTypeResolverBuilder()
                        .init(JsonTypeInfo.Id.CLASS, null)
                        .inclusion(JsonTypeInfo.As.PROPERTY));
    }

    static {
        MAPPER.setTimeZone(DEFAULT_TIMEZONE);
        MAPPER.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Returns json string for an object.
     *
     * @param param object
     */
    @SuppressWarnings("rawtypes")
    public static String jsonString(Object param) {
        StringBuilder builder = new StringBuilder();
        try {
            if (param instanceof Collection && ((Collection)param).size() > 100) {
                param = "collectionSize: " + ((Collection)param).size();
            } else if (param instanceof Map && ((Map)param).size() > 100) {
                param = "mapSize: " + ((Map)param).size();
            }
            builder.append(param == null ? "null" : MAPPER.writeValueAsString(param));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static RuntimeException propagate(Throwable e) {
        if (e instanceof RuntimeException) {
            throw (RuntimeException) e;
        }
        throw new RuntimeException(e);
    }

    public static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }

    public static <T> T deserialize(InputStream inputStream, Class<T> tClass) throws IOException {
        return SERIALIZE_MAPPER.readValue(inputStream, tClass);
    }

    public static <T> List<T> deserializeList(
        InputStream inputStream, Class<T> tClass) throws IOException {
        CollectionType collectionType = SERIALIZE_MAPPER.getTypeFactory().constructCollectionType(
            List.class, tClass);
        return SERIALIZE_MAPPER.readValue(inputStream, collectionType);
    }

    public static <T> void serialize(T value, String fileName) {
        File newFile = new File(fileName);
        try {
            if (newFile.createNewFile()) {
                FileOutputStream oos = new FileOutputStream(newFile);
                oos.write(SERIALIZE_MAPPER.writeValueAsBytes(value));
                oos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
