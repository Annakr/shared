package com.anna.krivoruchko.bencode.type;

import java.io.IOException;
import java.io.OutputStream;

public class BencodeInteger implements BencodeType {

    public static final char PREFIX = 'i';

    private int value;

    public BencodeInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void encode(OutputStream os) throws IOException {
        os.write(PREFIX);
        os.write(Integer.toString(value).getBytes());
        os.write(COMMON_POSTFIX);
    }
}
