package com.albert.utils;

import java.io.Serializable;

public class MyException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8222214751802750947L;

	public MyException() {
		super();
	}

	public MyException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public MyException(String arg0) {
		super(arg0);
	}

	public MyException(Throwable arg0) {
		super(arg0);
	}

}