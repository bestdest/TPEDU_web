package com.hanyang.iis.tpedu.SentenceTBL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.hanyang.iis.tpedu.PretoPost.SentenParser;
import com.hanyang.iis.tpedu.PretoPost.TargetNode;
import com.hanyang.iis.tpedu.TPEMatch.Matcher;
import com.hanyang.iis.tpedu.TPEMatch.Pair;
import com.hanyang.iis.tpedu.TPEMatch.PatternNode;
import com.hanyang.iis.tpedu.TPEMatch.PatternQuery;
import com.hanyang.iis.tpedu.TPEMatch.QueryChk;


public class TypeClassifier {
	
	
	public int GetType(String ParseTree) {
		
		int type = 1;
		
		boolean iscompound = false;
		boolean iscomplex = false;

		SentenParser SentenP = new SentenParser();

		List<TargetNode> TargetTree = SentenP.GetParseTreePostTree("", ParseTree);
		String CompoundPattern = "{S * <S *> * <CC .+> * <S *> *}";
		String ComplexPattern = "{S * {SBAR *} *}";
		
		List<PatternNode> CompoundPostTree = Build_Pattern_PostTree(CompoundPattern);
		List<PatternNode> ComplexPostTree = Build_Pattern_PostTree(ComplexPattern);
		
		iscompound = matching(TargetTree,CompoundPostTree);
		iscomplex = matching(TargetTree,ComplexPostTree);
		
		if(iscompound && iscomplex){
			type = 4;
		}else if(iscompound){
			type = 2;
		}else if(iscomplex){
			type = 3;
		}else{
			type = 1;
		}
		
		
		return type;
	}
	private static List<PatternNode> Build_Pattern_PostTree(String Pattern) {
		String Query = "";

		int SID = 0;
		QueryChk QC = new QueryChk(Query);

		Query = QC.RemoveRoot(Pattern);

		Query = QC.ChangeBrakey(Query);

		SID++;

		// Pattern Tree making
		// from here
		List<PatternNode> tree = new ArrayList<PatternNode>();

		PatternNode junk = new PatternNode(-1, "", -1, -1, "", -1);

		tree.add(junk);

		StringTokenizer st = new StringTokenizer(Query, " ");

		PatternQuery PQuery = new PatternQuery(st);

		PQuery.SepPattern(tree, 0);

		tree.get(1).PostOrderTrvs(SID, tree, 0);

		List<PatternNode> MatchList = PQuery.getMatchlist();

		for (int i = 1; i < tree.size(); i++) {
			for (int j = 1; j < MatchList.size(); j++) {
				if (tree.get(i).getUID() == MatchList.get(j).getUID()) {
					MatchList.set(j, tree.get(i));
					break;
				}
			}
		}

		List<PatternNode> PatternTree_Post = new ArrayList<PatternNode>();

		PatternTree_Post.add(junk);

		tree.get(1).PostOrderInsert(SID, PatternTree_Post, tree);

		// find parent
		for (int i = 1; i < PatternTree_Post.size(); i++) {
			PatternTree_Post.get(PatternTree_Post.get(i).getPID()).Addchildren(
					i);
		}

		// PatternTree making end

		return PatternTree_Post;
	}

	private static boolean matching(List<TargetNode> TargetPostTree,
			List<PatternNode> PatternTree_Post) {

		List<TargetNode> TargetTree = TargetPostTree;

		Matcher matcher = new Matcher();

		List<Pair> Result = new ArrayList<Pair>();

		Result = matcher.treePatternMatch(PatternTree_Post, TargetTree);

		// ResultPrint(Result, TargetTree);

		if (Result.size() != 0) {
			return true;
		}

		else {
			return false;
		}

	}
}
