package com.anna.krivoruchko.bencode;


import com.anna.krivoruchko.bencode.exception.BencodeParseException;
import com.anna.krivoruchko.bencode.parser.BencodeParser;
import com.anna.krivoruchko.bencode.parser.ParserFactory;
import com.anna.krivoruchko.bencode.type.BencodeType;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.anna.krivoruchko.bencode.util.StreamUtils.readWithoutExtractionOfByte;

public class Decoder {

    public List<BencodeType> decode(InputStream inputStream) throws IOException, BencodeParseException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        List<BencodeType> parsedValues = new ArrayList<BencodeType>();
        int currentByte;
        while ((currentByte = readWithoutExtractionOfByte(bufferedInputStream)) > -1) {
            BencodeParser parser = ParserFactory.getResponsibleParserByFirstChar((char) currentByte);
            BencodeType value = parser.parse(bufferedInputStream);
            parsedValues.add(value);
        }
        return parsedValues;
    }

}
