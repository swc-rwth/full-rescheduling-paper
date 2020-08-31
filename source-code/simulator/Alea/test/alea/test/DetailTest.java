/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alea.test;

import java.util.Arrays;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import xklusac.environment.ExperimentSetup;

/**
 *
 * @author sophoan
 */
@RunWith(Parameterized.class)
public class DetailTest {
    
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            {"10_40.swf.machines"},
            {"11_40.swf.machines"},
            {"12_40.swf.machines"},
            {"13_40.swf.machines"},
            {"14_40.swf.machines"},
            {"15_40.swf.machines"},
            {"16_40.swf.machines"},
            {"17_40.swf.machines"},
            {"18_40.swf.machines"},
            {"19_40.swf.machines"},
            {"20_40.swf.machines"},
            {"21_40.swf.machines"},
            {"22_40.swf.machines"},
            {"23_40.swf.machines"},
            {"24_40.swf.machines"},
            {"25_40.swf.machines"},
            {"26_40.swf.machines"},
            {"27_40.swf.machines"},
            {"28_40.swf.machines"},
            {"29_40.swf.machines"},
            {"30_40.swf.machines"},
            {"31_40.swf.machines"},
            {"32_40.swf.machines"},
            {"33_40.swf.machines"},
            {"34_40.swf.machines"},
            {"35_40.swf.machines"},
            {"36_40.swf.machines"},
            {"37_40.swf.machines"},
            {"38_40.swf.machines"},
            {"39_40.swf.machines"},
            {"40_40.swf.machines"},
            {"41_40.swf.machines"},
            {"42_40.swf.machines"},
            {"43_40.swf.machines"},
            {"44_40.swf.machines"},
            {"45_40.swf.machines"},
        });
    }
    
    private String configFile = "configuration.properties";
    private String machineFile;
    
    public DetailTest(String machineFile) {
        this.machineFile = machineFile;
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

    @Test
    public void TestData() throws Exception{
        ExperimentSetup.main(new String[] {
            configFile,
            machineFile,
            "-t",
            "300"
        });
    }
    
    
}
