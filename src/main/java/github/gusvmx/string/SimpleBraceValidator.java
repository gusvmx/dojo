/**
 * 
 */
package github.gusvmx.string;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * @author gus
 *
 */
public class SimpleBraceValidator implements BraceValidator {

	private Map<Character, Character> bracePairs;
	private Set<Character> openBraces;
	
	public SimpleBraceValidator() {
		this.bracePairs = new HashMap<>();
		this.openBraces = new HashSet<>();
		openBraces.add('(');
		openBraces.add('[');
		openBraces.add('{');
		
		bracePairs.put('(', ')');
		bracePairs.put('[', ']');
		bracePairs.put('{', '}');
	}
	
	@Override
	public void check(final String input) {
		Deque<Character> stack = new LinkedList<>();
		for (int i = 0; i < input.length(); i++) {
			char currentChar = input.charAt(i);
			if (openBraces.contains(currentChar)) {
				stack.push(currentChar);
			} else {
				//TODO: Manejar la excepcion lanzada cuando la pila este vacia.
				char openingBrace = stack.pop();
				char closingBrace = bracePairs.get(openingBrace);
				
				if (currentChar != closingBrace) {
					throw new RuntimeException("Invalid brace '" + currentChar + "' at position " + i + 1);
				}
			}
		}
	}

}
