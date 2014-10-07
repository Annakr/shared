package com.anna.krivoruchko.bencode.parser;

import com.anna.krivoruchko.bencode.exception.InvalidFormatException;
import com.anna.krivoruchko.bencode.type.BencodeDictionary;
import com.anna.krivoruchko.bencode.type.BencodeInteger;
import com.anna.krivoruchko.bencode.type.BencodeString;
import com.anna.krivoruchko.bencode.type.BencodeType;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.anna.krivoruchko.bencode.util.StreamUtils.createInputStreamFrom;

public class DictionaryParserTest {

    private DictionaryParser parser = new DictionaryParser();

    @Test
    public void testParse() throws Exception {
        //GIVEN
        BufferedInputStream inputStream = createInputStreamFrom("d3:cati99e4:lamp5:shelle");

        //WHEN
        BencodeDictionary result = parser.parse(inputStream);

        //THEN
        Assert.assertNotNull(result);
        Map<BencodeString, BencodeType> resultDictionary = result.getDictionary();
        Assert.assertNotNull(resultDictionary);
        Assert.assertEquals(2, resultDictionary.size());
        Set<Map.Entry<BencodeString, BencodeType>> entries = resultDictionary.entrySet();
        Iterator<Map.Entry<BencodeString, BencodeType>> iterator = entries.iterator();
        Map.Entry<BencodeString, BencodeType> firstEntry = iterator.next();
        Assert.assertEquals("cat", firstEntry.getKey().getValue());
        Assert.assertEquals(99, ((BencodeInteger)firstEntry.getValue()).getValue());
        Map.Entry<BencodeString, BencodeType> secondEntry = iterator.next();
        Assert.assertEquals("lamp", secondEntry.getKey().getValue());
        Assert.assertEquals("shell", ((BencodeString)secondEntry.getValue()).getValue());
    }

    @Test(expected = InvalidFormatException.class)
    public void testParseNoPostfix() throws Exception {
        //GIVEN
        BufferedInputStream inputStream = createInputStreamFrom("d3:cati99e4:lamp5:shell");

        //WHEN
        parser.parse(inputStream);
    }

    @Test(expected = InvalidFormatException.class)
    public void testParseNoPrefix() throws Exception {
        //GIVEN
        BufferedInputStream inputStream = createInputStreamFrom("3:cati99e4:lamp5:shelle");

        //WHEN
        parser.parse(inputStream);
    }

    @Test(expected = InvalidFormatException.class)
    public void testParseNonStringKey() throws Exception {
        //GIVEN
        BufferedInputStream inputStream = createInputStreamFrom("di11ei99ee");

        //WHEN
        parser.parse(inputStream);
    }

}
