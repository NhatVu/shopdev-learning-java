package com.learning.shopdevjava.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Locale;

public class MultiDateDeserializer extends LocalDateTimeDeserializer {

    public MultiDateDeserializer() {
        this(null);
    }

    public MultiDateDeserializer(DateTimeFormatter formatter) {
        super(formatter);
    }

    private static final long serialVersionUID = 1L;

    private static final String[] DATE_FORMATS = new String[] {"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd'Z'HH:mm:ss", "MM/dd/yyyy HH:mm:ss" };

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        final String date = node.textValue();

        for (String DATE_FORMAT : DATE_FORMATS) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.ROOT);
            try {
                return LocalDateTime.parse(date, formatter);
            } catch (DateTimeParseException e) {
            }
        }

        throw new JsonParseException(p,
                    "Unparseable date: \"" + date + "\". Supported formats: " + Arrays.toString(DATE_FORMATS));

    }
}
