package com.ilakkmanoharan.GalaxyUnitGuide;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadFileAndProcess {

	public static void main(String[] args) {
		
		//Please replace inputPath1 with the location where the project is saved in your machine
		String inputPath1 = "/Users/*********/Documents/WorkspaceGalaxyGuide/";
		String inputPath2 = "Problem3Java8/input/";
		String inputFileName = "input.txt";
		String inputFile = inputPath1 + inputPath2 + inputFileName;
		
		//Please replace outputPath1 with the location where the project is saved in your machine
		String outputPath1 = "/Users/********/Documents/WorkspaceGalaxyGuide/";
		String outputPath2 = "Problem3Java8/output/";
		String outputFileName = "output.txt";
		String outputFile = outputPath1 + outputPath2 + outputFileName;
		
		List<String> list = new ArrayList<String>();
		
		List<String>  masterSolutionList = new ArrayList<String>();
		masterSolutionList.add("Test Output:");

		try (Stream<String> stream = Files.lines(Paths.get(inputFile))) {
			
			list = stream.collect(Collectors.toList());
			List<String> errorList = new ArrayList<String>();
			String errorMessage = "I have no idea what you are talking about";
			
			for(String input: list){
				if(!(input.contains("is"))){
					errorList.add(errorMessage);
				}
			}
			
			Map<Boolean, List<String>> groups = 
				    list.stream().collect(Collectors.partitioningBy(s -> s.contains("how")));
				    List<List<String>> subSets = new ArrayList<List<String>>(groups.values());
				    List<String> lastPartition = subSets.get(1);
				    List<String> toFind = new ArrayList<>();
				    Pattern p1 = Pattern.compile("\\bis\\b(.*?)\\?");
					for(String text : lastPartition){
					Matcher m = p1.matcher(text);
					if ( m.find() ) {
					   String s = m.group(1).trim(); 
					   toFind.add(s);
					}
					}
					
		     List<String> diff = list.stream()
                          .filter(i -> !lastPartition.contains(i))
                          .collect (Collectors.toList());
				  
			 Map<String,String> map = diff.stream()
		               .map(s -> s.split("\\bis\\b"))
		               .collect(Collectors.toMap(a -> a[0], a -> a.length>1? a[1]: ""));
			 
			 GalaticConversions galaxyGuide = new GalaticConversions(map, toFind);
			 
			 List<String> solutions = galaxyGuide.prepareSolution(toFind);
			 
			 masterSolutionList = Stream.concat(solutions.stream(), errorList.stream()).collect(Collectors.toList());
			 
	 } catch (IOException e) {
		
		e.printStackTrace();
	}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

			for(String s : masterSolutionList){
            System.out.println(s);
			bw.write(s.trim());
			bw.newLine();
			}
			
		} catch (IOException e) {

			e.printStackTrace();

		}
		
}
		
	
}
	

