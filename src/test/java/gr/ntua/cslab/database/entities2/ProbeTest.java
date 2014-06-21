/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.ntua.cslab.database.entities2;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cmantas
 */
public class ProbeTest {
    
    public ProbeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of fromMap method, of class Probes.
     */
    @Test
    public void test_02_maping() {
        System.out.println("fromMap");
        Probe instance = new Probe("my_probe", "dummy content of details");
        Probe pr2 = new Probe(instance.toMap());
        pr2.fromMap(pr2.toMap());
        System.out.println(pr2);
        
    }
    
    @Test
    public void test_02_toJSON(){
        Probe instance = new Probe("my_probe", "dummy content of details");
        System.out.println(instance.toJSONObject().toString(3));
    }
    
    @Test
    public void test_04_store(){
        Probe instance = new Probe("my_probe", "dummy content of details");
        try {
            System.out.println("storing");
            instance.store();
        } catch (DBException ex) {
            ex.printStackTrace();
            fail("failed to store "+instance);
        }
    }

    
}
