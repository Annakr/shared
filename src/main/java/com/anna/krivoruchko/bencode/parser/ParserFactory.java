package com.anna.krivoruchko.bencode.parser;

import com.anna.krivoruchko.bencode.exception.InvalidFormatException;

import java.util.Arrays;
import java.util.List;

public class ParserFactory {

    private static final StringParser STRING_PARSER = new StringParser();

    private static final List<BencodeParser> AVAILABLE_PARSERS = Arrays.asList(
            new ListParser(),
            new DictionaryParser(),
            new IntegerParser(),
            STRING_PARSER
    );

    public static StringParser getStringParser() {
        return STRING_PARSER;
    }

    public static BencodeParser getResponsibleParserByFirstChar(char firstChar) throws InvalidFormatException {
        for (BencodeParser parser : AVAILABLE_PARSERS) {
            if(parser.isResponsible(firstChar)){
                return parser;
            }
        }
        throw new InvalidFormatException();
    }

}
