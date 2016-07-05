package com.hanyang.iis.tpedu.PretoPost;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.hanyang.iis.tpedu.Prun.LeafChanger;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class SentenParser {

	public String GetParseTree(String Sentence) {
		Sentence = Sentence.replace(".", "");

		LexicalizedParser lp = LexicalizedParser.loadModel(
				"edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz",
				"-maxLength", "80", "-retainTmpSubcategories");
		TreebankLanguagePack tlp = new PennTreebankLanguagePack();
		// Uncomment the following line to obtain original Stanford Dependencies
		// tlp.setGenerateOriginalDependencies(true);
		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		Parser parser = new Parser();

		Tree tree = parser.parse(Sentence);

		String Query = "" + tree;

		return Query;
	}

	public List<TargetNode> GetSentencePostTree(String senten) {
		int SID = 1;

		senten = senten.replace(".", "");

		List<TargetNode> pre_tree = new ArrayList<TargetNode>();

		TargetNode junk = new TargetNode(-1, -1, "", -1, -1, 0);

		pre_tree.add(junk);

		// System.out.println(rs.getString(3));
		/*
		 * String Query = rs.getString(3); String sent = rs.getString(2);
		 */
		String Query = GetParseTree(senten);

		StringTokenizer stkn = new StringTokenizer(Query);

		StringBuffer tmpstr = new StringBuffer();

		while (stkn.hasMoreTokens()) {
			tmpstr.append(stkn.nextToken());
			if (stkn.hasMoreTokens()) {
				tmpstr.append(" ");
			}
		}

		Query = tmpstr.toString();

		// System.out.println(Query);

		SID = 1;

		for (int i = 1; i < Query.length(); i++) {
			if (Query.charAt(i - 1) == ')' && Query.charAt(i) == '(') {
				Query = Query.substring(0, i) + " "
						+ Query.substring(i, Query.length());
			}
		}

		StringTokenizer st = new StringTokenizer(Query, " ");

		SentenceQuery sentree = new SentenceQuery(st);

		sentree.SepSent(SID, 0, pre_tree);

		pre_tree.get(1).PostOrderTrvs(SID, pre_tree, 0);

		List<TargetNode> post_tree = new ArrayList<TargetNode>();
		post_tree.add(junk);
		pre_tree.get(1).BuildPostOrderTree(SID, pre_tree, senten, Query,
				post_tree);

		for (int i = 1; i < post_tree.size(); i++) {
			post_tree.get(post_tree.get(i).getPID()).addChildren(i);
		}

		return post_tree;

	}

	public List<TargetNode> GetParseTreePostTree(String senten,String ParseTree) {
		int SID = 1;

		senten = senten.replace(".", "");

		List<TargetNode> pre_tree = new ArrayList<TargetNode>();

		TargetNode junk = new TargetNode(-1, -1, "", -1, -1, 0);

		pre_tree.add(junk);

		// System.out.println(rs.getString(3));
		/*
		 * String Query = rs.getString(3); String sent = rs.getString(2);
		 */
		LeafChanger LC = new LeafChanger();
		String Query = LC.RemoveDot(ParseTree);

		StringTokenizer stkn = new StringTokenizer(Query);

		StringBuffer tmpstr = new StringBuffer();

		// System.out.println(Query);

		SID = 1;

		for (int i = 1; i < Query.length(); i++) {
			if (Query.charAt(i - 1) == ')' && Query.charAt(i) == '(') {
				Query = Query.substring(0, i) + " "
						+ Query.substring(i, Query.length());
			}
		}

		StringTokenizer st = new StringTokenizer(Query, " ");

		SentenceQuery sentree = new SentenceQuery(st);

		sentree.SepSent(SID, 0, pre_tree);

		pre_tree.get(1).PostOrderTrvs(SID, pre_tree, 0);

		List<TargetNode> post_tree = new ArrayList<TargetNode>();
		post_tree.add(junk);
		pre_tree.get(1).BuildPostOrderTree(SID, pre_tree, senten, Query,
				post_tree);

		for (int i = 1; i < post_tree.size(); i++) {
			post_tree.get(post_tree.get(i).getPID()).addChildren(i);
		}

		return post_tree;

	}
}
