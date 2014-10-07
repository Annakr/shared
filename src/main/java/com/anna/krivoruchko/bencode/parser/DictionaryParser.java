package com.anna.krivoruchko.bencode.parser;

import com.anna.krivoruchko.bencode.exception.BencodeParseException;
import com.anna.krivoruchko.bencode.exception.InvalidFormatException;
import com.anna.krivoruchko.bencode.type.BencodeDictionary;
import com.anna.krivoruchko.bencode.type.BencodeString;
import com.anna.krivoruchko.bencode.type.BencodeType;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.TreeMap;

import static com.anna.krivoruchko.bencode.util.StreamUtils.readByteAndMarkPosition;
import static com.anna.krivoruchko.bencode.util.StreamUtils.readWithoutExtractionOfByte;

public class DictionaryParser extends OneCharPrefixParser {

    @Override
    protected char getPrefix() {
        return BencodeDictionary.PREFIX;
    }

    @Override
    public BencodeDictionary parse(BufferedInputStream inputStream) throws IOException, BencodeParseException {
        readAndCheckFirstByte(inputStream);

        TreeMap<BencodeString, BencodeType> bencodeValuesMap = new TreeMap<BencodeString, BencodeType>();
        int currentByte;
        while ((currentByte = readByteAndMarkPosition(inputStream)) > -1) {
            char currentChar = (char) currentByte;
            if (currentChar == getPostfix()){
                return new BencodeDictionary(bencodeValuesMap);
            } else {
                inputStream.reset();
                BencodeString key = ParserFactory.getStringParser().parse(inputStream);
                char prefix = (char) readWithoutExtractionOfByte(inputStream);
                BencodeParser bencodeParser = ParserFactory.getResponsibleParserByFirstChar(prefix);
                BencodeType value =  bencodeParser.parse(inputStream);
                bencodeValuesMap.put(key, value);
            }
        }
        throw new InvalidFormatException();
    }
}
