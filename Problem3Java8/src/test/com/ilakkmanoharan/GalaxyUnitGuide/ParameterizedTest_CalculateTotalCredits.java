/* Copyright (C) 2017 Ilakkuvaselvi Manoharan - All Rights Reserved
 * 
 */

package test.com.ilakkmanoharan.GalaxyUnitGuide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.*;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.ilakkmanoharan.GalaxyUnitGuide.GalaticConversions;
import static org.junit.Assert.assertEquals;


@RunWith(value = Parameterized.class)
public class ParameterizedTest_CalculateTotalCredits {
	
	@Before
    public void init(){
        
        Map<String,String> given = new HashMap<String,String>();
		
		given.put("glob", "I");
		given.put("pish", "X");
		given.put("prok", "V");
		given.put("tegj", "L");
		given.put("glob glob Silver", "34");
		given.put("glob prok Gold", "57800");
		given.put("pish pish Iron", "3910");
		
		List<String> toFind = new ArrayList<String>();
		
		toFind.add("pish tegj glob glob");
		toFind.add("glob prok Silver");
		toFind.add("glob prok Gold");
		toFind.add("glob prok Iron");
		
		galaxyGuide = new GalaticConversions(given, toFind);
		galaxyGuide.prepareSolution(toFind);
		 
    }

	private List<String> galaticunit;
	private double credit;
	private static GalaticConversions galaxyGuide;
	
	// Inject via constructor
    public ParameterizedTest_CalculateTotalCredits(List<String> galaticunit, double credit) {
        this.galaticunit = galaticunit;
        this.credit = credit;
    }
    
     // name attribute is optional, provide an unique name for test
 	// multiple parameters, uses Collection<Object[]>
     @Parameters(name = "{index}: testcalculateTotalCredits({0}) = {1}")
     public static Collection<Object[]> data() {
         return Arrays.asList(new Object[][]{
                 {new ArrayList<String>(Arrays.asList("pish", "tegj", "glob", "glob")), 42},
                 {new ArrayList<String>(Arrays.asList("pish", "tegj" )), 40},
                 {new ArrayList<String>(Arrays.asList("pish", "tegj", "glob")), 41},
                 {new ArrayList<String>(Arrays.asList("glob", "glob")), 2},
                 {new ArrayList<String>(Arrays.asList("glob", "prok", "Silver")), 68},
                 {new ArrayList<String>(Arrays.asList("glob", "prok", "Gold")), 57800},
                 {new ArrayList<String>(Arrays.asList("glob", "prok", "Iron")), 782}
          });
     }
     
     @Test
     public void test_calculateTotalCredits() {
    	 assertEquals(galaxyGuide.calculateTotalCredits(galaticunit), credit, 0.02);
     }
     

}
