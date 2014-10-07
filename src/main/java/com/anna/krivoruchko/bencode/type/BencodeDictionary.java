package com.anna.krivoruchko.bencode.type;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


public class BencodeDictionary implements BencodeType {

    public static final char PREFIX = 'd';

    private Map<BencodeString, BencodeType> dictionary;

    public BencodeDictionary() {
        dictionary = new TreeMap<BencodeString, BencodeType>();
    }

    public BencodeDictionary(SortedMap<BencodeString, BencodeType> dictionary) {
        if (dictionary == null) {
            throw new IllegalArgumentException();
        }
        this.dictionary = dictionary;
    }

    public Map<BencodeString, BencodeType> getDictionary() {
        return dictionary;
    }

    public void addValue(String key, BencodeType value) {
        dictionary.put(new BencodeString(key), value);
    }

    @Override
    public void encode(OutputStream os) throws IOException {
        os.write(PREFIX);
        for (Map.Entry<BencodeString, BencodeType> entry : dictionary.entrySet()) {
            entry.getKey().encode(os);
            entry.getValue().encode(os);
        }
        os.write(COMMON_POSTFIX);
    }
}
