/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da_project;

import java.util.HashSet;

/**
 *
 * @author ayush
 */
public class StopWords {
    HashSet<String> stopWords = new HashSet<>();
    public StopWords() {
        stopWords.add("I"); 
        stopWords.add("a");
        stopWords.add("about"); 
        stopWords.add("an");
        stopWords.add("are"); 
        stopWords.add("as");
        stopWords.add("at"); 
        stopWords.add("be");
        stopWords.add("by"); 
        stopWords.add("com");
        stopWords.add("de"); 
        stopWords.add("en");
        stopWords.add("for"); 
        stopWords.add("from");
        stopWords.add("how"); 
        stopWords.add("in");
        stopWords.add("is"); 
        stopWords.add("it");
        stopWords.add("la"); 
        stopWords.add("of");
        stopWords.add("on"); 
        stopWords.add("or");
        stopWords.add("that"); 
        stopWords.add("the");
        stopWords.add("this"); 
        stopWords.add("to");
        stopWords.add("was"); 
        stopWords.add("what");
        stopWords.add("when"); 
        stopWords.add("where");
        stopWords.add("who"); 
        stopWords.add("will");
        stopWords.add("with"); 
        stopWords.add("and");
        stopWords.add("the"); 
        stopWords.add("www");
    }
    
    boolean isStopWord(String word) {
        return stopWords.contains(word);
    }
}
