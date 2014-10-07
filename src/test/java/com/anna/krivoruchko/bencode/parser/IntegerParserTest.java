package com.anna.krivoruchko.bencode.parser;

import com.anna.krivoruchko.bencode.exception.InvalidFormatException;
import com.anna.krivoruchko.bencode.type.BencodeInteger;
import com.anna.krivoruchko.bencode.util.StreamUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;


public class IntegerParserTest {

    private IntegerParser parser = new IntegerParser();

    @Test
    public void testParse() throws Exception {
        //GIVEN
        BufferedInputStream inputStream = StreamUtils.createInputStreamFrom("i77e");

        //WHEN
        BencodeInteger result = parser.parse(inputStream);

        //THEN
        Assert.assertNotNull(result);
        Assert.assertEquals(77, result.getValue());

    }

    @Test(expected = InvalidFormatException.class)
    public void testParseNoPostfix() throws Exception {
        //GIVEN
        BufferedInputStream inputStream = StreamUtils.createInputStreamFrom("i77");

        //WHEN
        parser.parse(inputStream);
    }

    @Test(expected = InvalidFormatException.class)
    public void testParseNoPrefix() throws Exception {
        //GIVEN
        BufferedInputStream inputStream = StreamUtils.createInputStreamFrom("77e");

        //WHEN
        parser.parse(inputStream);
    }
}
