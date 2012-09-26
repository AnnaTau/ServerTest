/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.server;

import junit.framework.TestCase;

/**
 *
 * @author 1
 */
public class MySqlConnectTest extends TestCase {
    
    public MySqlConnectTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSomeMethod() {
        MySqlConnect connect = new MySqlConnect();
        connect.createIfNotExist("testing");
    }
}
