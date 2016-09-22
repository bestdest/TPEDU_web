package com.hanyang.iis.tpedu.FeatureExtraction;


import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.hanyang.iis.tpedu.PretoPost.Parser;
import com.hanyang.iis.tpedu.PretoPost.SentenceQuery;
import com.hanyang.iis.tpedu.PretoPost.TargetNode;
import com.hanyang.iis.tpedu.Prun.LeafChanger;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class SPResult {
	private List<TypedDependency> tdl;
	private String ParseTree;
	
	
	
	public SPResult(String sentence){
		LexicalizedParser lp = LexicalizedParser.loadModel(
				"edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz",
				"-maxLength", "80", "-retainTmpSubcategories");
		TreebankLanguagePack tlp = new PennTreebankLanguagePack();
		// Uncomment the following line to obtain original Stanford Dependencies
		// tlp.setGenerateOriginalDependencies(true);
		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		String str = sentence;
		
		StringTokenizer stken = new StringTokenizer(str);
		
		Parser parser = new Parser();
		
		List<String> SList = new ArrayList<String>();
		while(stken.hasMoreTokens()){
			SList.add(stken.nextToken());
		}
		String[] sent = (String[]) SList.toArray(new String[SList.size()]);
		Tree parse = lp.apply(Sentence.toWordList(sent));
		GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
		List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
		
		TypedDependency tmp = tdl.get(0);
		
		this.tdl = tdl;
		this.ParseTree = parser.parse(str).toString();
	}



	public List<TypedDependency> getTdl() {
		return tdl;
	}



	public void setTdl(List<TypedDependency> tdl) {
		this.tdl = tdl;
	}



	public String getParseTree() {
		return ParseTree;
	}



	public void setParseTree(String parseTree) {
		ParseTree = parseTree;
	}
	
}

