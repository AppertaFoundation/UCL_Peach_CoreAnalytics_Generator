package com.ipponusa;

import static org.junit.Assert.fail;

import junit.framework.Test;

import junit.framework.TestCase;
import junit.framework.TestSuite;

@SuppressWarnings("unused")
public class SparkTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SparkTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static TestSuite suite()
    {
        return new TestSuite( SparkTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

	
}
