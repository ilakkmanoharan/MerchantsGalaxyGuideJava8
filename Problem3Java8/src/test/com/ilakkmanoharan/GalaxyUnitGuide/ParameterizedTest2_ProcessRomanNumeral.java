/* Copyright (C) 2017 Ilakkuvaselvi Manoharan - All Rights Reserved
 * 
 */


package test.com.ilakkmanoharan.GalaxyUnitGuide;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.*;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.ilakkmanoharan.GalaxyUnitGuide.GalaticConversions;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(value = Parameterized.class)
public class ParameterizedTest2_ProcessRomanNumeral {
	
    private static GalaticConversions galaxyGuide;
	
	@Before
    public void init(){
		galaxyGuide = new GalaticConversions();
	}
	
    @Parameter(value = 0)
    public String galaticUnit;

    @Parameter(value = 1)
    public double expected;

    @Parameters(name = "{index}: processRomanNumeral({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"II", 2},
                {"MM", 2000},
                {"MCMXLIV", 1944},
                {"IV", 4},
                {"CM", 900},
                {"III", 3},
                {"MCMIII", 1903},
                {"MMM", 3000}
        });
    }
    
     @Test
     public void test_processRomanNumeral() {
    	 assertThat(galaxyGuide.processRomanNumeral(galaticUnit), is(expected));
     }
     

}
