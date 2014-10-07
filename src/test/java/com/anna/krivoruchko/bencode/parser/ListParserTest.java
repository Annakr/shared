package com.anna.krivoruchko.bencode.parser;

import com.anna.krivoruchko.bencode.exception.InvalidFormatException;
import com.anna.krivoruchko.bencode.type.BencodeInteger;
import com.anna.krivoruchko.bencode.type.BencodeList;
import com.anna.krivoruchko.bencode.type.BencodeString;
import com.anna.krivoruchko.bencode.type.BencodeType;
import com.anna.krivoruchko.bencode.util.StreamUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.util.List;

public class ListParserTest {

    private ListParser parser = new ListParser();

    @Test
    public void testParse() throws Exception {
        //GIVEN
        BufferedInputStream inputStream = StreamUtils.createInputStreamFrom("li88e5:applee");

        //WHEN
        BencodeList result = parser.parse(inputStream);

        //THEN
        Assert.assertNotNull(result);
        List<BencodeType> resultList = result.getList();
        Assert.assertNotNull(resultList);
        Assert.assertEquals(2, resultList.size());
        BencodeType firstValue = resultList.get(0);
        Assert.assertTrue(firstValue instanceof BencodeInteger);
        Assert.assertEquals(88, ((BencodeInteger)firstValue).getValue());
        BencodeType secondValue = resultList.get(1);
        Assert.assertTrue(secondValue instanceof BencodeString);
        Assert.assertEquals("apple", ((BencodeString)secondValue).getValue());
    }

    @Test(expected = InvalidFormatException.class)
    public void testParseNoPostfix() throws Exception {
        //GIVEN
        BufferedInputStream inputStream = StreamUtils.createInputStreamFrom("li88e5:apple");

        //WHEN
        parser.parse(inputStream);
    }

    @Test(expected = InvalidFormatException.class)
    public void testParseNoPrefix() throws Exception {
        //GIVEN
        BufferedInputStream inputStream = StreamUtils.createInputStreamFrom("i88e5:applee");

        //WHEN
        parser.parse(inputStream);
    }
}
