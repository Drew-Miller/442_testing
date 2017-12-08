package Utilities;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;

public class WordNet {
	
	IDictionary dict;
	
	public  WordNet () {
		try {
			this.Initialize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void Initialize () throws IOException {
	
		// construct the URL to the Wordnet dictionary directory
		String wnhome = System.getenv("WNHOME");
		String path = wnhome + File.separator + "dict";
		URL url = new URL("file", null, path);
		
		// construct the dictionary object and open it
		dict = new Dictionary(url);
		dict.open();
		
		// look up first sense of the word "dog"
		IIndexWord idxWord = dict.getIndexWord("test", POS.NOUN);
		IWordID wordID = idxWord.getWordIDs().get(0);
		IWord word = dict.getWord(wordID);
		System.out.println("Id = " + wordID);
		System.out.println("Lemma = " + word.getLemma());
		System.out.println("Gloss = " + word.getSynset().getGloss());
	
	}
	
	public boolean isWord(String s) {
		if(s.length() == 1) {
			return true;
		}
		if (dict.getIndexWord(s, POS.ADJECTIVE) != null) { return true; }
		if (dict.getIndexWord(s, POS.ADVERB) != null) { return true; }
		if (dict.getIndexWord(s, POS.NOUN) != null) { return true; }
		if (dict.getIndexWord(s, POS.VERB) != null) { return true; }
		// check to see if stems are words
		WordnetStemmer wn = new WordnetStemmer(dict);
		List<String> strings = null;
		strings = wn.findStems(s, null);
		for (int i = 0; i < strings.size(); i++) {
			if (dict.getIndexWord(strings.get(i), POS.ADJECTIVE) != null) { return true; }
			if (dict.getIndexWord(strings.get(i), POS.ADVERB) != null) { return true; }
			if (dict.getIndexWord(strings.get(i), POS.NOUN) != null) { return true; }
			if (dict.getIndexWord(strings.get(i), POS.VERB) != null) { return true; }
		}
 		return false;
	}
	
	public boolean isNoun(String s) {
		if (dict.getIndexWord(s, POS.NOUN) != null) { return true; }
		WordnetStemmer wn = new WordnetStemmer(dict);
		List<String> strings = null;
		strings = wn.findStems(s, POS.NOUN);
		for (int i = 0; i < strings.size(); i++) {
			System.out.println("NOUN = " + strings.get(i));
			if (dict.getIndexWord(strings.get(i), POS.NOUN) != null) { return true; }
		}
		return false;
	}
	
	public boolean isVerb(String s) {
		if (dict.getIndexWord(s, POS.VERB) != null) { return true; }
		WordnetStemmer wn = new WordnetStemmer(dict);
		List<String> strings = null;
		strings = wn.findStems(s, POS.VERB);
		for (int i = 0; i < strings.size(); i++) {
			System.out.println("VERB = " + strings.get(i));
			if (dict.getIndexWord(strings.get(i), POS.VERB) != null) { return true; }
		}
		return false;
	}
	
	public boolean isSameWord (String s1, String s2) {
		WordnetStemmer stem = new WordnetStemmer(dict);
		List<String> strings1 = stem.findStems(s1, null);
		List<String> strings2 = stem.findStems(s2, null);
		for(String str1 : strings1) {
			for(String str2 : strings2) {
				if(str1.equals(str2)) {
					return true;
				}
			}
		}
		return false;
	}
	
}