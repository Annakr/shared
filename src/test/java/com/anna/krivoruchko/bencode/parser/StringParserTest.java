package com.anna.krivoruchko.bencode.parser;

import com.anna.krivoruchko.bencode.exception.InvalidFormatException;
import com.anna.krivoruchko.bencode.type.BencodeString;
import com.anna.krivoruchko.bencode.util.StreamUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;

public class StringParserTest {

    private StringParser parser = new StringParser();

    @Test
    public void testParse() throws Exception {
        //GIVEN
        BufferedInputStream inputStream = StreamUtils.createInputStreamFrom("5:Hello");

        //WHEN
        BencodeString result = parser.parse(inputStream);

        //THEN
        Assert.assertNotNull(result);
        Assert.assertEquals("Hello", result.getValue());
    }

    @Test(expected = InvalidFormatException.class)
    public void testParseLessCharsThenSpecified() throws Exception {
        //GIVEN
        BufferedInputStream inputStream = StreamUtils.createInputStreamFrom("5:Hell");

        //WHEN
        parser.parse(inputStream);
    }

    @Test(expected = InvalidFormatException.class)
    public void testParseNoPrefix() throws Exception {
        //GIVEN
        BufferedInputStream inputStream = StreamUtils.createInputStreamFrom(":Hello");

        //WHEN
        parser.parse(inputStream);
    }

    @Test(expected = InvalidFormatException.class)
    public void testParseNonDigitPrefix() throws Exception {
        //GIVEN
        BufferedInputStream inputStream = StreamUtils.createInputStreamFrom("q:Hello");

        //WHEN
        parser.parse(inputStream);
    }
}
