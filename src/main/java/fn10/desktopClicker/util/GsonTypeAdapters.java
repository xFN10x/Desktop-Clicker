package fn10.desktopClicker.util;

import java.awt.Point;
import java.lang.reflect.Type;
import java.time.Instant;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonTypeAdapters {

    public static class InstantTypeAdapter implements JsonSerializer<Instant>, JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return Instant.ofEpochMilli(json.getAsLong());
        }

        @Override
        public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
            typeOfSrc = long.class;
            return context.serialize(src.toEpochMilli());
        }

    }

    public static class PointTypeAdapter implements JsonSerializer<Point>, JsonDeserializer<Point> {

        @Override
        public Point deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            String string = json.getAsString();
            if (string.startsWith("java.awt.Point[")) {
                string = string.replace("java.awt.Point[x=", "").replace("]", "").replace("y=", "");
                String[] split = string.split(",");
                int x = Integer.valueOf(split[0]);
                int y = Integer.valueOf(split[1]);
                return new Point(x, y);
            } else {
                String[] split = string.split(",");
                int x = Integer.valueOf(split[0]);
                int y = Integer.valueOf(split[1]);
                return new Point(x, y);
            }
        }

        @Override
        public JsonElement serialize(Point src, Type typeOfSrc, JsonSerializationContext context) {
            typeOfSrc = String.class;
            System.out.println(String.valueOf(src.getX()) + "," + String.valueOf(src.getY()));
            return context.serialize(String.valueOf(src.getX()) + "," + String.valueOf(src.getY()), typeOfSrc);
        }

    }

}
