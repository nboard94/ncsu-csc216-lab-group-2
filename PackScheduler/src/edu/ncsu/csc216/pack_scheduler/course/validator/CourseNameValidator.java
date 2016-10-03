package edu.ncsu.csc216.pack_scheduler.course.validator;

public class CourseNameValidator {
	
	private boolean validEndState;
	private int letterCount;
	private int digitCount;
	
	private LetterState letterState = new LetterState();
	private SuffixState suffixState = new SuffixState();
	private InitialState initialState = new InitialState();
	private NumberState numberState = new NumberState();
	private State currentState;
	
	public boolean isValid(String text) throws InvalidTransitionException {
		letterCount = 0;
		digitCount = 0;
		validEndState = false;
		currentState = initialState;
		
		int i = 0;
		while(i < text.length()) {
			char character = text.charAt(i++);
			if (Character.isLetter(character)) {
				currentState.onLetter();
			} else if (Character.isDigit(character)) {
				currentState.onDigit();
			} else {
				currentState.onOther();
			}
		}
		
		return validEndState;
	}
	
	
	private abstract class State {
		
		public abstract void onLetter() throws InvalidTransitionException;
		
		public abstract void onDigit() throws InvalidTransitionException;
		
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
	}
	
	private class LetterState extends State {
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (letterCount < 4) {
				letterCount++;
				currentState = letterState;
			} else if (letterCount == 4) {
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}
		}
		
		@Override
		public void onDigit() {
			currentState = numberState;
			digitCount++;			
		}
	}
	
	private class SuffixState extends State {
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
		}
		
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
		}
	}
	
	private class InitialState extends State {
		@Override
		public void onLetter() {
			letterCount++;
			currentState = letterState;
		}
		
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");
		}
	}
	
	private class NumberState extends State {
		
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (digitCount == 3) {
				currentState = suffixState;
			} else {
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}
		}
		
		@Override
		public void onDigit() throws InvalidTransitionException {
			if (digitCount < 3) {
				digitCount++;
				currentState = numberState;
				if (digitCount == 3) {
					validEndState = true;
				}
			} else if (digitCount == 3) {
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
		}
	}
}
