package com.anna.krivoruchko.bencode.parser;

import com.anna.krivoruchko.bencode.exception.BencodeParseException;
import com.anna.krivoruchko.bencode.exception.InvalidFormatException;
import com.anna.krivoruchko.bencode.type.BencodeList;
import com.anna.krivoruchko.bencode.type.BencodeType;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.anna.krivoruchko.bencode.util.StreamUtils.readByteAndMarkPosition;

public class ListParser extends OneCharPrefixParser {

    @Override
    public BencodeList parse(BufferedInputStream inputStream) throws IOException, BencodeParseException {
        readAndCheckFirstByte(inputStream);

        List<BencodeType> bencodeTypeList = new ArrayList<BencodeType>();
        int currentByte;
        while ((currentByte = readByteAndMarkPosition(inputStream)) > -1) {
            char currentChar = (char) currentByte;
            if (currentChar == getPostfix()){
                return new BencodeList(bencodeTypeList);
            } else {
                BencodeParser bencodeParser = ParserFactory.getResponsibleParserByFirstChar(currentChar);
                inputStream.reset();
                BencodeType bencodeType =  bencodeParser.parse(inputStream);
                bencodeTypeList.add(bencodeType);
            }
        }
        throw new InvalidFormatException();
    }

    @Override
    protected char getPrefix() {
        return BencodeList.PREFIX;
    }
}
