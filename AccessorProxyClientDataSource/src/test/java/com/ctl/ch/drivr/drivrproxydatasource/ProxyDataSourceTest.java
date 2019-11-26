package com.ctl.ch.drivr.drivrproxydatasource;

import java.util.HashMap;

import com.ctl.ch.common.coreutilities.FormattedString;
import com.ctl.ch.common.legacylogger.LegacyLog;
import com.ctl.ch.drivr.dataaccess.DataAccessException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ProxyDataSourceTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ProxyDataSourceTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ProxyDataSourceTest.class );
    }

    /**
     * Tests the proxy data source by invoking the TechonsBusinessRatesAccess accessor on 484
     * @throws DataAccessException
     */
    public void testProxyDataSource() throws DataAccessException {
    	ProxyDataSource source = new ProxyDataSource();
    	HashMap input = new HashMap(); 
    	input.put("AreaCode", "515");
    	HashMap out = source.performAction("TechonsBusinessRatesAccess", "GetTechonsBusinessRates", input, new LegacyLog(), "14.09.00.01");
    	System.out.println(FormattedString.get(out));
    }
   
   
}
