package kz.edu.nu.cs.se.hw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MyKeywordInContext implements KeywordInContext {
	ArrayList<Indexable> words = new ArrayList<Indexable>();
	ArrayList<String> lines = new ArrayList<String>();
	Scanner sc;
	String[] wordsToIgnore = { "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
			"yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
			"itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
			"these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
			"do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
			"of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
			"after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
			"further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
			"few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
			"too", "very", "s", "t", "can", "will", "just", "don", "should", "now" };

	public MyKeywordInContext(String name, String pathstring) throws FileNotFoundException {
		sc = new Scanner(new File(pathstring));
	}

	@Override
	public int find(String word) {
		int i = 0;
		for (Indexable el : words) {
			if (el.getEntry().equals(word.toLowerCase())) {
				return i;
			}
			i += 1;
		}
		return -1;
	}

	@Override
	public Indexable get(int i) {
		return words.get(i);
	}

	@Override
	public void txt2html(){

		String html = "<!DOCTYPE html>\r\n" + "<html><head><meta charset=\"UTF-8\"></head><body>\r\n" + "<div>";
		int lineNum = 1;
		for (String el : lines) {
			html = html + el + "<span id='line_%d'>&nbsp&nbsp[%d]</span><br>".formatted(lineNum, lineNum)+'\n';
			lineNum += 1;
		}
		html = html + "</div></body></html>";
		File file = new File("mytest.html");  
		//Creating the file with its html content
		try {
			if(file.createNewFile()) {
				FileWriter writer = new FileWriter(file);
				writer.write(html);
				writer.close();
				System.out.println("File created successfully!!");
			}
			else {
				System.out.println("File already exists!!");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void indexLines() {
		List<String> ignore = new ArrayList<>(Arrays.asList(wordsToIgnore));
		int linenum = 0;

		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.isEmpty())
				continue;
			lines.add(line);
			linenum += 1;
			String[] tokens = line.split("[ ,.:;]");
			for (String token : tokens) {
				token = token.toLowerCase();
				if (token.isEmpty())
					continue;
				// System.out.println(token);
				if (!(ignore.contains(token))) {
					MyIndexable indxbl = new MyIndexable(token, linenum);
					words.add(indxbl);
				}
			}
		}
		sc.close();

	}

	@Override
	public void writeIndexToFile() {
		String html_content = "<!DOCTYPE html>\r\n"
				+ "<html><head><meta charset=\'UTF-8\'></head><body><div style=\'text-align:center;line-height:1.6'>\n";
		for(Indexable keyword: words) {
			int line_num = keyword.getLineNumber();
			String sent[] = lines.get(line_num-1).split("[ ,.:;]");
			for(String word: sent) {
				if(word.equals(keyword.getEntry())) {
					html_content = html_content + "<a href='mytest.html#line_%d'> ".formatted(line_num) + word.toUpperCase() + " </a>";
				}
				else {
					html_content = html_content + word + ' ';
				}
			}
			html_content = html_content + " <br> \n";
			
		}
		html_content = html_content + " </div></body></html> ";
		
		File file = new File("mykwic-test.html");  
		//Creating the file with its html content
		try {
			if(file.createNewFile()) {
				FileWriter writer = new FileWriter(file);
				writer.write(html_content);
				writer.close();
				System.out.println("KWIC File created successfully!!");
			}
			else {
				System.out.println("KWIC File already exists!!");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
