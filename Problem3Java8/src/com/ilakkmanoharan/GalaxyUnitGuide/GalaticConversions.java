/* Copyright (C) 2018 Ilakkuvaselvi Manoharan - All Rights Reserved
 * 
 */


package com.ilakkmanoharan.GalaxyUnitGuide;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GalaticConversions {
	
	    private Map<String,String> given = new HashMap<String, String>();
	    
	    private List<String> galaticNamesList = new ArrayList<String>();
		
		private List<String> toFind = new ArrayList<String>();
		
		private Map<String,Double> metalCredits = new HashMap<String, Double>();
		
		private List<String> metalList = new ArrayList<String>(Arrays.asList("Gold", "Silver", "Iron"));
		
		
		public GalaticConversions(Map<String, String> given, List<String> toFind) {
			 
			 this.given = given;
				
			 this.toFind = toFind;
				
			 setup();
			
		}
		 
		
		public GalaticConversions() {
			
		}

        
		public void setup(){
			 
			  Map<String,String> galUnits = new HashMap<String, String>();
				
			  Map<String,String> galAndMetalUnits = new HashMap<String, String>();
				
			  given.forEach((key, value) -> {
					
					 if(key.split("\\s+").length == 1){
					    	
					    	galUnits.put(key.trim(), value.trim());
					    	
					  }else{
					    	
					    	galAndMetalUnits.put(key.trim(), value.trim());
					    	
					   }
					    
				});
				 
				List<Symbols> symbolsList = Arrays.asList(Symbols.values());
				 
				for(Symbols sym: symbolsList){  
				 
					   galUnits.forEach((key, value) -> {
						 
					   if(IsEqualStringandEnum(value,sym)){
					    	
					    	sym.setGalaticName(key);
					    	
					    }
					    
				 });
				 
				 }
				 
				 this.galaticNamesList = getGalaticNamesList();
				 
				 processGivenGalMetalUnits(galUnits, galAndMetalUnits);
				

		    }
			

		       private void processGivenGalMetalUnits(Map<String, String> mapb, Map<String, String> mapa) {
		    	  
		    	        mapa.entrySet().stream().
		            
		    	        map(entry -> Arrays.stream((entry.getKey().concat(" " + entry.getValue())).split(" ")).
		            
		    	        map(s -> mapb.getOrDefault(s.trim(), s.trim())).
		            
		    	        collect(Collectors.joining(" "))
		            
		    	    	).forEach(s -> {
		                
		    	    			String[] sarr = s.split(" ");
		                
		    	    			processMetalSymbolAndCreditPart(sarr[0] + sarr[1], sarr[2], sarr[3]);
		            
		    	    	});
			
		      }

              
		      private void processMetalSymbolAndCreditPart(String symbolPart, String metalPart, String rhscreditPart) {
		    	  
				      double symbolCredit = processRomanNumeral(symbolPart);
				
				      double rhsDouble = Double.parseDouble(rhscreditPart.replaceAll("[^0-9]", ""));
			      
	                  double metalCredit = rhsDouble/symbolCredit;
	              
	                  this.metalCredits.put(metalPart.trim(), metalCredit);
	               
			  }
            
			
			  public double processRomanNumeral(String symbolPart) {
				
				      beforeAfterProcessRomanNumeral();
					
					  double totalCredits = 0.0;
					
					  Queue<Character> queue =new LinkedList<Character>();
					
					  char[] symbolChars = symbolPart.toCharArray();
					
					  for(char c : symbolChars){
						
						  queue.add(c);
						
			           }
					
					   while(!queue.isEmpty()) {
						
					       char curr = queue.poll();  
						
						   char next = '0';
						
						   Symbols currSymbol = Symbols.fromString(curr);
						
						   if(!queue.isEmpty()) {
						
							    next = queue.peek();
						
						     } else {
							
							    next = 'Z';
						     }
						
						     Symbols nextSymbol = Symbols.fromString(next);
					
						     if(currSymbol == nextSymbol){
				        		
							         currSymbol.setSuccessiveRepeat(currSymbol.getSuccessiveRepeat() + 1);
			       		 
			       	          }
						
						      if(!isValidSeq(currSymbol, nextSymbol)){
							
							          return 0.0;
							
						       } else if(isValidSeq(currSymbol, nextSymbol)){
							
							          if(currSymbol.getCredit() >= nextSymbol.getCredit()) {
								
								               totalCredits = totalCredits + currSymbol.getCredit();
								
							           }else if(currSymbol.getCredit() < nextSymbol.getCredit()) {
								
								               totalCredits = totalCredits + (nextSymbol.getCredit() - currSymbol.getCredit());
								
								               queue.poll();
								
							           }
							
					            }
						
						
			              } //end of while loop
					
					return totalCredits;
			   }

			   
			  private void beforeAfterProcessRomanNumeral() {
				   
					for(Symbols symbol: Symbols.values()) {
					   
						if(symbol.getSuccessiveRepeat() > 0) {
						   
							symbol.setSuccessiveRepeat(0);
					   
						}
				   
					}
				
			   }


			  private static boolean isValidSeq(Symbols curr, Symbols next) {
					
					return ((curr.checkRepeatability(next)) && ((curr.getCredit() >= next.getCredit()) || (curr.isValidSubtraction(next))));
				
			   }

				
			  private boolean isValidSeq(String input) {
					
					beforeAfterProcessRomanNumeral();
					
					if(processRomanNumeral(input) == 0) {
						
						return false;
					}
					
					return true;
				}

				
				public List<String> prepareSolution(List<String> toFind2) {
					
					List<String> solutionList = new ArrayList<String>();
					
					List<String> items = new ArrayList<String>();
					 
					double credits = 0;
					 
					for(String item: toFind){
						 
						 items = 
								  
								 Pattern.compile("\\s+")
								 
								 .splitAsStream(item)
								 
								 .collect(Collectors.toList());
						
						 credits = calculateTotalCredits(items);
						 
						 String solutionStr = item + " is ";
						 
						 if(credits > 0) {
						 
						     int creditInt = (int) credits;
						 
						     solutionStr = solutionStr + creditInt + " Credits";
						 
						 } else {
							 
							 solutionStr = solutionStr + " an invalid galatic unit";
						 }
						 
						 solutionList.add(solutionStr);
						 
					 }
					 
					return solutionList;
					
				}
				
				
				private boolean isGalUnitsOnly(List<String> list3){
					
					return (!(list3.stream().anyMatch(metalList::contains)));
					
				}

				
				public double calculateTotalCredits(List<String> items) {
					
					 double credit = 0;
						
	                 if(isGalUnitsOnly(items)){
		
                                   String numeralString = "";
		
		                           for(String s: items){
		                        	   
			                                  String numeralVal = (Symbols.fromGalaticNames(s)).toString();
			                                  
			                                  numeralString = (numeralString.trim() + numeralVal.trim()).trim();
		
		                           }
		
		                           credit = processRomanNumeral(numeralString);
		
	                  }else if(hasMetalsAndNumerals(items)){
			
		                           List<String> numeralpart = getNumeralPart(items);
		                           
		                           double numeralCredit = 0;
		                           
		                           if(numeralpart.size() > 0) {
        
                                   numeralCredit = processNumeralPart(numeralpart);
                                   
		                           }
        
                                   List<String> lhsMetalsOnly = getMetalPart(items);
		 
		                           String lhsMetal = "";
		 
		                           if(lhsMetalsOnly.size() == 1){
			 
			                             lhsMetal = lhsMetalsOnly.get(0);
			 
		                            }
		 
		                           double mCredit = 0;
		 
		                           if(metalCredits.size() > 0){
		 
		                                 mCredit = this.metalCredits.get(lhsMetal.trim());
		 
		                           }
        
                                   if((numeralpart.size() > 0) && (numeralCredit > 0)) {
		                              credit = numeralCredit * mCredit;
                                   } else if((numeralpart.size() == 0) && (numeralCredit <= 0)){
                                	   credit = mCredit;	   
                                   } else if((numeralpart.size() > 0) && (numeralCredit <= 0)){
                                	   credit = 0;	   
                                   }
        
                        }
	
	                               return credit;
				
				}
				
				
				private List<String> getMetalPart(List<String> items) {
					
					List<String> metalPart = new ArrayList<String>();
					
					for(String s: items){
						
					if(isMetalListContains(s)){
						
						metalPart.add(s);
						
					}
						
					}
					
					return metalPart;
				}

				
				public boolean isMetalListContains(String name) {
					
					for(String str: metalList) {
					    
						if(str.trim().equalsIgnoreCase(name.trim()))
					       
							return true;
					
					}
					
					return false;
				}

				
				private double processNumeralPart(List<String> numeralpart) {
					
				   	 String numeralString = "";
						
					 for(String s: numeralpart){
							
							String numeralVal = (Symbols.fromGalaticNames(s)).toString();
							
							numeralString = (numeralString.trim() + numeralVal.trim()).trim();
							
					 }
						
						   double credit = processRomanNumeral(numeralString);
					
			               return credit;
				
				}

				
				private List<String> getNumeralPart(List<String> unitList) {
					
					    List<String> numeralPart = new ArrayList<String>();
					
					    for(String s: unitList){
						
						     if(isGalListContains(s)){
						
						           numeralPart.add(s);
						
					          }
						
					     }
					
					return numeralPart;
				}

				
				private boolean isGalListContains(String name) {
					 
					    for(String str: galaticNamesList) {
					    
						        if(str.trim().equals(name.trim()))
					       
							           return true;
					
					             }
					
					     return false;
					
				}

				
				private boolean hasMetalsAndNumerals(List<String> unitList) {
					
				     return (unitList.stream().anyMatch(metalList::contains));
				
				}

				
				private boolean IsEqualStringandEnum (String str,Symbols sym){
						
			          if (str.trim().equals(sym.toString().trim())){
			                    
			              return true;
			            
			          }
			            
			          else
			          
			          {
			                
			              return false;
			            
			           }	
			            
			           }
		             
			
			     private List<String> getGalaticNamesList(){
				
				       List<String> galaticNamesList = new ArrayList<String>();
				
				       List<Symbols> symbolsList = Arrays.asList(Symbols.values());
				
				           for(Symbols s: symbolsList){
					
					               if(s.getGalaticName() != null){
					
					                    galaticNamesList.add(s.getGalaticName());
					
					                }
				             
				            }
				
				       return galaticNamesList;
			         
			      }
			
			
}
