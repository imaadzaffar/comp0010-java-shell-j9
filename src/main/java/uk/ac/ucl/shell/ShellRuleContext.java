package uk.ac.ucl.shell;

import java.io.InputStream;

import org.antlr.v4.runtime.ParserRuleContext;

public class ShellRuleContext extends ParserRuleContext {
    public InputStream inputStream;
    public String test;

    public ShellRuleContext(ParserRuleContext parent, int invokingStateNumber) {
		super(parent, invokingStateNumber);
	}
}
