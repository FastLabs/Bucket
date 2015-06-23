package stream.json;


import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.SimpleType;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;


public class JSonStreamerTest {

    class XCodec extends ObjectCodec {

        @Override
        public <T> T readValue(JsonParser jp, Class<T> valueType) throws IOException, JsonProcessingException {
            return null;
        }

        @Override
        public <T> T readValue(JsonParser jp, TypeReference<?> valueTypeRef) throws IOException, JsonProcessingException {
            return null;
        }

        @Override
        public <T> T readValue(JsonParser jp, ResolvedType valueType) throws IOException, JsonProcessingException {
            return null;
        }

        @Override
        public <T> Iterator<T> readValues(JsonParser jp, Class<T> valueType) throws IOException, JsonProcessingException {
            return null;
        }

        @Override
        public <T> Iterator<T> readValues(JsonParser jp, TypeReference<?> valueTypeRef) throws IOException, JsonProcessingException {
            return null;
        }

        @Override
        public <T> Iterator<T> readValues(JsonParser jp, ResolvedType valueType) throws IOException, JsonProcessingException {
            return null;
        }

        @Override
        public void writeValue(JsonGenerator jgen, Object value) throws IOException, JsonProcessingException {

        }

        @Override
        public <T extends TreeNode> T readTree(JsonParser jp) throws IOException, JsonProcessingException {
            return null;
        }

        @Override
        public void writeTree(JsonGenerator jg, TreeNode tree) throws IOException, JsonProcessingException {

        }

        @Override
        public TreeNode createObjectNode() {
            return null;
        }

        @Override
        public TreeNode createArrayNode() {
            return null;
        }

        @Override
        public JsonParser treeAsTokens(TreeNode n) {
            return null;
        }

        @Override
        public <T> T treeToValue(TreeNode n, Class<T> valueType) throws JsonProcessingException {
            return null;
        }
    }

    @Test
    public void test() throws IOException {
        JsonParser x = new JsonFactory().setCodec(new XCodec()).createParser("[{\"name\" : \"oleg\"}]");
        JsonToken t = x.nextToken();
        while (t != null) {
            System.out.println(t);
            t = x.nextToken();
        }
    }

    @Test
    public void testObjectMapper() throws IOException {
        ObjectMapper x = new ObjectMapper();
        HashMap<String, String> w = x.readValue("{\"name\" : \"oleg\", \"fam\" : \"b\"}", MapType.construct(HashMap.class, SimpleType.construct(String.class), SimpleType.construct(String.class)));
        System.out.println(w.get("name"));
        System.out.println(w.get("fam"));
    }
}
