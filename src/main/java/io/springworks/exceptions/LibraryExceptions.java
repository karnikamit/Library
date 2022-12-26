package io.springworks.exceptions;

public class LibraryExceptions extends Exception{

	private static final long serialVersionUID = 1L;

	public LibraryExceptions(String errorMessage) {
		super(errorMessage);
	}
}
