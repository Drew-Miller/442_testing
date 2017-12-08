package Wordnet;

import java.io.IOException;
import java.io.File;
import java.net.*;
import java.util.*;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;

public class Wordnet {
	// dictionary object for accessing wordnet
	private IDictionary _dict;

	private final String _env = "WNHOME";
	private final String _dir = "dict";
	private final String _protocol = "file";
	
	public Wordnet() throws IOException {			
		_dict = new Dictionary(new URL(_protocol, null, createDictPath(System.getenv(_env))));
		_dict.open();
	}
	
	// accepts a string path to the dictionary
	// LINUX/UNIX and WINDOWS compatible
	public String createDictPath(String wnhome) {	
		if(wnhome != null) {
			// set the delimiter to a windows/unix file seperator
			char delim = '/';
			// if the path is a windows path, use the delimiter
			if(wnhome.contains("\\")) {
				delim = '\\';
			}
			
			// ensure there is a delimiter already at the end of the path 
			// before adding the dictionary directory
			if(wnhome.charAt(wnhome.length() - 1) != delim) {
				wnhome = wnhome + delim;
			}
				
			String dictPath = wnhome + _dir;
			
			// if a real file exists at the dictionary location			
			if(PathExists(dictPath)) {
				return dictPath;
			}
		}
		
		return null;
	}
	
	// checks if the path is part of the file system
	public boolean PathExists(String path) {
		return new File(path).exists();
	}
	
	public IDictionary dict() {
		return _dict;
	}
	
	// returns if the word is within the database
	public boolean isWord(String w) {
		if(w != null) {
			if(_dict.getIndexWord(w, POS.ADJECTIVE) != null) { return true; }
			else if(_dict.getIndexWord(w, POS.ADVERB) != null) { return true; }
			else if(_dict.getIndexWord(w, POS.NOUN) != null) { return true; }
			else if(_dict.getIndexWord(w, POS.VERB) != null) { return true; }
		}
		
		return false;
	}
	
	public boolean isNoun(String w) {
		if(w != null) {
			return _dict.getIndexWord(w, POS.NOUN) != null;
		}
		
		return false;
	}
	
	public IIndexWord getNounIndex(String w) {
		if(isNoun(w)) {
			return _dict.getIndexWord(w, POS.NOUN);
		}
		
		return null;
	}
	
	public List<IWord> getWordsFromSynset(ISynsetID synsetID){
		return _dict.getSynset(synsetID).getWords();
	}

	public List<ISynsetID> getHypernymSynsetIDs(IWord w){
		return w.getSynset().getRelatedSynsets(Pointer.HYPERNYM);
	}
	
	// checks if a word is contained within a synsetID
	public boolean inSynset(ISynsetID sIdx, IIndexWord w) {
		// find the words contaiend within the synset to 
		// determine if W is one of those words.
		List<IWord> words = _dict.getSynset(sIdx).getWords();
		
		for(IWord word : words) {
			// If the lemma values of w and one of the words match,
			// we have a match in the synset and we can return true
			if(word.getLemma().equals(w.getLemma())) {
				return true;
			}
		}
		// if we are here, then the word did not match
		// return false
		return false;
	}
	
	// accepts a string 'c' for the class and a string 's' for the super
	// and determines if s is a hypernym of c
	// s -> canine
	// c -> dog
	public boolean isHypernym(String className, String superName) {				
		if(isNoun(className) && isNoun(superName)) {			
			// get the indexes for c and s
			// useful for super s.t. we can get the lemma match
			IIndexWord classIndex = getNounIndex(className);
			IIndexWord superIndex = getNounIndex(superName);
			
			// For the class,
			// find all of synyonyms for that word
			// and then all of the hypernyms of the synynoms
			for(IWordID wId : classIndex.getWordIDs()) {
				List<ISynsetID> hypernymSIDs = getHypernymSynsetIDs(_dict.getWord(wId));
				
				// If the super class IS a hypernym, it will be located within
				// one of the hypernym sets, or higher.
				// If it matches, we have a hypernym, otherwise recursively call this function
				// to get higher levels of hypernyms.
				for(ISynsetID hypernymSID : hypernymSIDs) {
					// for each of the hypernym synsets, get the words in that synset
					if(inSynset(hypernymSID, superIndex)) {
						return true;
					}
					// if the super is not in that direct hypnym set, 
					// lets check ONE higher set of hypernyms
					// NOTE: I originally tried recursively doing all
					// but some hypernyms were calling each other and
					// I was getting a stack overflow. I figured two levels
					// would be sufficient enough
					else {
						// get all of the words in the hypernym synset we are currently in
						// we need these words to find the hyper-hypernym synsets to check
						// if the class is two layers up
						for(IWord hypernym : getWordsFromSynset(hypernymSID)) {
							List<ISynsetID> hyperHypernymSIDs = getHypernymSynsetIDs(hypernym);
							
							// same thing as before
							// We have the super word to check if it is a hypernym
							// check all of the synsets from the words that were originally just hypernyms
							// if the super is in one of those sets, then we have a hypernym
							for(ISynsetID hyperHypernymSID : hyperHypernymSIDs) {
								if(inSynset(hyperHypernymSID, superIndex))
								{
									return true;
								}
							}
						}
					}
				}
			}
		}
			
		// if neither name is a noun or not in the dict,
		// then don't worry about trying to determine if
		// it is a hypernym
		return false;
	}
	
	// Tests to see if a hypernym relation is missing
	public boolean hypernymMissing(String c ,String s) {
		// If we have two strings that are nouns,
		// check if there is a hypernym relation
		if(isWord(c) && isWord(s)) {
			if(isNoun(c) && isNoun(s)) {
				// return if the two nouns are not hypernyms
				 return !isHypernym(c, s);
			}
		}
		
		return false;
	}
}

