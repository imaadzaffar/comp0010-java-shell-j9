// Generated from uk\ac\u005Cucl\shell\ShellGrammar.g4 by ANTLR 4.7
package uk.ac.ucl.shell;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ShellGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ShellGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ShellGrammarParser#shell}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShell(ShellGrammarParser.ShellContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShellGrammarParser#sequence}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSequence(ShellGrammarParser.SequenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShellGrammarParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand(ShellGrammarParser.CommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShellGrammarParser#pipe}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPipe(ShellGrammarParser.PipeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShellGrammarParser#call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(ShellGrammarParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShellGrammarParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(ShellGrammarParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShellGrammarParser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(ShellGrammarParser.ArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShellGrammarParser#redirection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRedirection(ShellGrammarParser.RedirectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShellGrammarParser#quoted}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuoted(ShellGrammarParser.QuotedContext ctx);
}