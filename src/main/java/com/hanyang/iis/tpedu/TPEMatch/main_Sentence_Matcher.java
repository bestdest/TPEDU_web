package com.hanyang.iis.tpedu.TPEMatch;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.hanyang.iis.tpedu.PretoPost.SentenParser;
import com.hanyang.iis.tpedu.PretoPost.TargetNode;
import com.hanyang.iis.tpedu.dao.PatternDAO;
import com.hanyang.iis.tpedu.dto.PatternDTO;

public class main_Sentence_Matcher {

	public static void main(String[] args) {
		String Sentence = "Tell me how to make cookies.";

		PatternDAO Patterndao = new PatternDAO();
		SentenParser SentenP = new SentenParser();

		List<TargetNode> TargetTree = SentenP.GetSentencePostTree(Sentence);

		List<PatternDTO> Patternlist = Patterndao.GetPatternList();
		List<PatternDTO> MatchedPatternList = new ArrayList<PatternDTO>();

		for (int i = 0; i < Patternlist.size(); i++) {
			/* tree build -> match -> Loop */
			List<PatternNode> curPatternPostTree = null;
			curPatternPostTree = Build_Pattern_PostTree(Patternlist.get(i)
					.getPattern());
			if (curPatternPostTree == null) {
				System.out.println("CurPattern fail!");
				return;
			}
			if (matching(TargetTree, curPatternPostTree)) {
				MatchedPatternList.add(Patternlist.get(i));
				System.out.println(Patternlist.get(i).getId());
			}
		}
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

	private void ResultPrint(List<Pair> Result, List<TargetNode> TargetTree) {
		List<Integer> Resulta = new ArrayList<Integer>();
		List<Integer> Resultb = new ArrayList<Integer>();

		if (Result.size() != 0) {
			for (int i = 0; i < Result.size(); i++) {
				Resulta.add(Result.get(i).P);
				Resultb.add(Result.get(i).T);
			}
		}
	}
}