import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DynamicMemorySortingSearching {
	
	public static void main(String[] args) throws IOException {
		
		int no_Keyword = 0;
		//opening keyword file
		File inFile_keywords = new File("Files\\HW3-unsorted-keywords.txt");
		BufferedReader bf_inFile= new BufferedReader(new FileReader(inFile_keywords));
		int noline = 0;
		
		//counting number of lines
		while(bf_inFile.readLine()!=null) {
			noline++;
		}
		//close the keyword file
		bf_inFile.close();
		
		//string array for keywords
		String keywords[] = new String[noline];
		bf_inFile = new BufferedReader(new FileReader(inFile_keywords));
		
		int i = 0;
		String input;
		//reading file for storing data in keyword array
		while((input = bf_inFile.readLine())!=null)
			keywords[i++] = input;
		
		//Sort the keywords
		Sort(keywords,i);
		
		//close the input file
		bf_inFile.close();
		
		//opening .cpp file
		File inFile_cpp = new File("Files\\HW3-input-code.cpp");
		bf_inFile = new BufferedReader(new FileReader(inFile_cpp));
		
		//create output file
		File outFile = new File("Files\\HW3-output.txt");
		BufferedWriter br_output = new BufferedWriter(new FileWriter(outFile));
		
		noline = 0;
		String line;
		
		//reading .cpp file
		while((line = bf_inFile.readLine())!=null)
		{
			noline++;
			String res="";
			String word = "";
			
			for(int j=0;j<line.length();j++)
			{
				if(!Character.isLetter(line.charAt(j)) && word.length()>=0)
				{
					
				  //Ignore the comments
			      if(line.charAt(j)==('/') && line.charAt(j+1)==('/'))
					break;
			  
			      //Find the words in the keyword list through binary search
			      if(BinarySearch(keywords, word))
			      {
			    	  //If the word is found store its position in the line in String "res"
			    	  res +=(word+"("+(j-word.length())+") ");
				  	  no_Keyword++;
			       }
			      word="";
				}
				else if(Character.isLetter(line.charAt(j)))
					word+=line.charAt(j);
				
			}
			
			//When at least a single word is found write the line to output file
			if(res.length()!=0)
			{
				br_output.append("Line "+noline+": "+res);
				br_output.newLine();
			}
			
		}
		
		br_output.append("Number of keywords found = "+no_Keyword);
		br_output.newLine();
		bf_inFile.close();
		br_output.close();
		System.out.println("Number of keywords found = "+no_Keyword);
	}

	
	//Binary Search for searching keyword in file
	private static boolean BinarySearch(String[] keywords, String string) {
		int low =0, high = 83;
		while(low<=high)
		{
			int mid = low + (high-low)/2;
			if((keywords[mid].compareTo(string))==0)
				return true;
			else if((keywords[mid].compareTo(string))>0)
				high = mid-1;
			else
				low = mid+1;
		}	
		return false;
	}
	
	//Bubble Sorting of the keyword array
	private static void Sort(String[] keywords, int n) {
		
		for(int i=0;i<n;i++)
		{
			for(int j = i+1;j<n;j++)
				if((keywords[i].compareTo(keywords[j]))>0)
				{
					String temp = keywords[i];
					keywords[i] = keywords[j];
					keywords[j] = temp;
				}
		}
	}
}
