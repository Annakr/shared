package com.anna.krivoruchko.bencode;

import com.anna.krivoruchko.bencode.type.BencodeDictionary;
import com.anna.krivoruchko.bencode.type.BencodeInteger;
import com.anna.krivoruchko.bencode.type.BencodeList;
import com.anna.krivoruchko.bencode.type.BencodeString;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

public class EncoderTest {

    private Encoder encoder = new Encoder();

    @Test
    public void testEncodeOneRootValue() throws Exception {

        //GIVEN
        BencodeDictionary dictionary = new BencodeDictionary();
        dictionary.addValue("anna", new BencodeInteger(28));
        dictionary.addValue("max", new BencodeString("someVal"));
        BencodeList bencodeList = new BencodeList();
        bencodeList.addValue(new BencodeInteger(33));
        bencodeList.addValue(new BencodeString("55"));
        dictionary.addValue("berlin", bencodeList);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //WHEN
        encoder.encode(baos, dictionary);

        //THEN
        String encodedValue = baos.toString();
        Assert.assertNotNull(encodedValue);
        Assert.assertEquals("d4:annai28e6:berlinli33e2:55e3:max7:someVale", encodedValue);
    }

    @Test
    public void testEncodeMultipleRootValues() throws Exception {

        //GIVEN
        BencodeInteger bencodeInteger = new BencodeInteger(55);
        BencodeString bencodeString = new BencodeString("john");
        BencodeList bencodeList = new BencodeList();
        bencodeList.addValue(new BencodeInteger(3));
        bencodeList.addValue(new BencodeInteger(8));
        bencodeList.addValue(new BencodeInteger(9));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //WHEN
        encoder.encode(baos, bencodeInteger, bencodeString, bencodeList);

        //THEN
        String encodedValue = baos.toString();
        Assert.assertNotNull(encodedValue);
        Assert.assertEquals("i55e4:johnli3ei8ei9ee", encodedValue);
    }
}
