/**
 * 
 */
package github.gusvmx.string.test;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import github.gusvmx.string.BraceValidator;
import github.gusvmx.string.SimpleBraceValidator;

/**
 * @author gus
 *
 */
public class BracesTest {

	private BraceValidator validator;
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Before
	public final void setup() {
		validator = new SimpleBraceValidator();
	}
	
	@Test
	public final void parenthesisOpenClose() {
		String input = "()";
		validator.check(input);
	}
	
	@Test
	public final void bracketsOpenClose() {
		String input = "[]";
		validator.check(input);
	}
	
	@Test
	public final void curlyBracesOpenClose() {
		String input = "{}";
		validator.check(input);
	}
	
	@Test
	public final void combinedBraces() {
		String input = "(({})[])";
		validator.check(input);
	}
	
	@Test
	public final void invalidCombinedBraces() {
		String input = "({)}";
		expectedException.expect(RuntimeException.class);
		validator.check(input);
	}
	
	@Test
	public final void noOpeningBraceAtTheStart() {
		String input = "}";
		expectedException.expect(RuntimeException.class);
		validator.check(input);
	}
	
}
