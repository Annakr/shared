package com.anna.krivoruchko.bencode;


import com.anna.krivoruchko.bencode.type.BencodeType;

import java.io.IOException;
import java.io.OutputStream;

public class Encoder {

    public void encode(OutputStream os, BencodeType... values) throws IOException {
        if (values != null) {
            for (BencodeType value : values) {
                value.encode(os);
            }
        }
    }

}
