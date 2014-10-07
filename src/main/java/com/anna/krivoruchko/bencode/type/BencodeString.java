package com.anna.krivoruchko.bencode.type;


import java.io.IOException;
import java.io.OutputStream;

public class BencodeString implements BencodeType, Comparable<BencodeString> {

    public static final char LENGTH_SEPARATOR = ':';

    private String value;

    public BencodeString(String value) {
        if(value == null) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int compareTo(BencodeString anotherString) {
        return value.compareTo(anotherString.getValue());
    }

    @Override
    public void encode(OutputStream os) throws IOException {
        int lengthOfValue = value.length();
        os.write(Integer.toString(lengthOfValue).getBytes());
        os.write(LENGTH_SEPARATOR);
        os.write(value.getBytes());
    }
}
