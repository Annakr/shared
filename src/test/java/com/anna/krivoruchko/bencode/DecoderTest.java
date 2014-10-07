package com.anna.krivoruchko.bencode;

import com.anna.krivoruchko.bencode.exception.InvalidFormatException;
import com.anna.krivoruchko.bencode.type.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static com.anna.krivoruchko.bencode.util.StreamUtils.createInputStreamFrom;

public class DecoderTest {

    private Decoder decoder = new Decoder();

    @Test
    public void testDecodeOneRootValue() throws Exception {
        //GIVEN
        InputStream inputStream = createInputStreamFrom("ld5:abcdei888e2:zxli1ei2eee4:dfghe");

        //WHEN
        List<BencodeType> values = decoder.decode(inputStream);

        //THEN
        Assert.assertNotNull(values);
        Assert.assertEquals(1, values.size());

        BencodeType rootValue = values.get(0);
        checkListTypeAndSize(rootValue, 2);
        BencodeList list = (BencodeList) rootValue;

        BencodeType firstValue = list.getList().get(0);
        checkDictionaryTypeAndSize(firstValue, 2);

        BencodeDictionary dictionary = (BencodeDictionary) firstValue;

        BencodeType abcdeEntryValue = dictionary.getDictionary().get(new BencodeString("abcde"));
        checkIntegerValue(abcdeEntryValue, 888);

        BencodeType zxEntryValue = getValueByKey(dictionary, "zx");
        checkListTypeAndSize(zxEntryValue, 2);
        BencodeList internalList = (BencodeList) zxEntryValue;

        BencodeType firstValueInList = internalList.getList().get(0);
        checkIntegerValue(firstValueInList, 1);

        BencodeType secondValueInList = internalList.getList().get(1);
        checkIntegerValue(secondValueInList, 2);

        BencodeType secondValue = list.getList().get(1);
        checkStringValue(secondValue, "dfgh");
    }

    @Test
    public void testDecodeMultipleRootValues() throws Exception {
        //GIVEN
        InputStream inputStream = createInputStreamFrom("i7e6:qwertyli1ei3ee");

        //WHEN
        List<BencodeType> values = decoder.decode(inputStream);

        //THEN
        Assert.assertNotNull(values);
        Assert.assertEquals(3, values.size());

        BencodeType firstValue = values.get(0);
        checkIntegerValue(firstValue, 7);

        BencodeType secondValue = values.get(1);
        checkStringValue(secondValue, "qwerty");

        BencodeType thirdValue = values.get(2);
        checkListTypeAndSize(thirdValue, 2);
        BencodeList listValue = (BencodeList) thirdValue;

        BencodeType firstValueInList = listValue.getList().get(0);
        checkIntegerValue(firstValueInList, 1);

        BencodeType secondValueInList = listValue.getList().get(1);
        checkIntegerValue(secondValueInList, 3);
    }

    @Test(expected = InvalidFormatException.class)
    public void testDecodeMultipleRootValuesRedundantChar() throws Exception {
        //GIVEN
        InputStream inputStream = createInputStreamFrom("i7e6:qwertyli1ei3eee");

        //WHEN
        decoder.decode(inputStream);
    }

    private void checkIntegerValue(BencodeType valueToCheck, int expectedValue) {
        Assert.assertNotNull(valueToCheck);
        Assert.assertTrue(valueToCheck instanceof BencodeInteger);
        Assert.assertEquals(expectedValue, ((BencodeInteger) valueToCheck).getValue());
    }

    private void checkStringValue(BencodeType valueToCheck, String expectedValue) {
        Assert.assertNotNull(valueToCheck);
        Assert.assertTrue(valueToCheck instanceof BencodeString);
        Assert.assertEquals(expectedValue, ((BencodeString) valueToCheck).getValue());
    }

    private void checkListTypeAndSize(BencodeType valueToCheck, int expectedSize) {
        Assert.assertNotNull(valueToCheck);
        Assert.assertTrue(valueToCheck instanceof BencodeList);
        List<BencodeType> valuesInList = ((BencodeList) valueToCheck).getList();
        Assert.assertNotNull(valuesInList);
        Assert.assertEquals(expectedSize, valuesInList.size());
    }

    private void checkDictionaryTypeAndSize(BencodeType valueToCheck, int expectedSize) {
        Assert.assertNotNull(valueToCheck);
        Assert.assertTrue(valueToCheck instanceof BencodeDictionary);
        Map<BencodeString, BencodeType> valuesInDictionary = ((BencodeDictionary) valueToCheck).getDictionary();
        Assert.assertNotNull(valuesInDictionary);
        Assert.assertEquals(expectedSize, valuesInDictionary.size());
    }

    private BencodeType getValueByKey(BencodeDictionary dictionary, String key) {
        return dictionary.getDictionary().get(new BencodeString(key));
    }
}
