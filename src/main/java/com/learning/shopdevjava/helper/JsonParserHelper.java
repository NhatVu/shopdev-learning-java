package com.learning.shopdevjava.helper;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.shopdevjava.dto.Car;

import java.io.IOException;

public class JsonParserHelper {

    /*
    Reference:
    1. https://stackoverflow.com/questions/24835431/use-jackson-to-stream-parse-an-array-of-json-objects
    2. https://jenkov.com/tutorials/java-json/jackson-jsonparser.html
     */
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String carJson =
                "[" +
                        "  { \"brand\" : \"Mercedes\", \"doors\" : 5 }," +
                        "  { \"brand\" : \"a\", \"doors\" : 6 }," +
                        "  { \"brand\" : \"b\", \"doors\" : 7 }" +
                        "]";

        JsonFactory factory = new JsonFactory();
        JsonParser  parser  = factory.createParser(carJson);

        JsonToken jsonToken = parser.nextToken();
        // the first token is supposed to be the start of array '['
        if (!JsonToken.START_ARRAY.equals(jsonToken)) {
            // return or throw exception
            throw new RuntimeException("must be list object");
        }
        while(true){
            jsonToken = parser.nextToken();
            if(!JsonToken.START_OBJECT.equals(jsonToken)){
                break;
            }

            if (jsonToken == null) {
                break;
            }

            if(JsonToken.START_OBJECT.equals(jsonToken)){

                Car car = mapper.readValue(parser, Car.class);
                System.out.println("car: " + car);
            }

        }

    }
}
