package com.albert;

import com.albert.utils.MyException;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    public static void main(String[] args) throws  MyException {
    	AppContext context = AppContext.getInstance();
    	context.init();
	}
}
