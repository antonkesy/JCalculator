package com.antonkesy.jcalculator.parser;

import com.antonkesy.jcalculator.parser.ast_nodes.Node;
import com.antonkesy.jcalculator.parser.exception.MissingTokenException;

public interface IParser {
    Node parse() throws MissingTokenException;
}
