package com.anna.krivoruchko.bencode.type;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BencodeList implements BencodeType {

    public static final char PREFIX = 'l';

    private List<BencodeType> list;

    public BencodeList() {
        list = new ArrayList<BencodeType>();
    }

    public BencodeList(List<BencodeType> list) {
        if (list == null) {
            throw new IllegalArgumentException();
        }
        this.list = list;
    }

    public void addValue(BencodeType value) {
        list.add(value);
    }

    public List<BencodeType> getList() {
        return list;
    }

    @Override
    public void encode(OutputStream os) throws IOException {
        os.write(PREFIX);
        for (BencodeType bencodeType : list) {
            bencodeType.encode(os);
        }
        os.write(COMMON_POSTFIX);
    }
}
