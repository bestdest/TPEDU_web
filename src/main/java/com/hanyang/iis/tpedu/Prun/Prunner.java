package com.hanyang.iis.tpedu.Prun;

import java.util.List;
import java.util.StringTokenizer;

import com.hanyang.iis.tpedu.PretoPost.TargetNode;


public class Prunner {

	public String Prunning(String ParseTree) {
		String PrunedTree = ParseTree;

		PrunedTree = Prun_NN(PrunedTree);
		PrunedTree = Prun_CD(PrunedTree);
		PrunedTree = Prun_DT(PrunedTree);
		PrunedTree = Prun_VBZ(PrunedTree);

		return PrunedTree;
	}

	public String Prun_Leafs(String ParseTree) {
		String PrunedTree = "";
		
		
		StringTokenizer stkn = new StringTokenizer(ParseTree);

		String beforetkn = "";
		// if curtkn is leaf, erase before and insert *
		while (stkn.hasMoreTokens()) {
			String curtkn = stkn.nextToken();

			// if leaf erase before, make cur *
			if (curtkn.contains(")")) {
				int num_brak = 0;
				while (curtkn.contains(")")) {
					curtkn = curtkn.substring(0, curtkn.length() - 1);
					num_brak++;
				}
				String anytree = "*";
				for (int i = 0; i < num_brak - 1; i++) {
					anytree += ")";
				}
				curtkn = anytree;
			}
			// nonleaf insert
			else {
				if(PrunedTree.length()>2 && PrunedTree.charAt(PrunedTree.length()-2) == '*' && beforetkn.contains("*")){
					PrunedTree = PrunedTree.substring(0, PrunedTree.length()-2) + beforetkn + " ";  
				}else{
					PrunedTree += beforetkn + " ";
				}
				
			}
			// if last insert
			if (!stkn.hasMoreTokens()) {
				
				if(PrunedTree.length()>2 && PrunedTree.charAt(PrunedTree.length()-2) == '*' && curtkn.contains("*")){
					PrunedTree = PrunedTree.substring(0, PrunedTree.length()-2) + curtkn;  
				}else{
					PrunedTree += curtkn;
				}
				
				break;
			}
			beforetkn = curtkn;
		}

		return PrunedTree.substring(1);
	}

	public String DepthPrun(String PrunedTree, List<TargetNode> pre_tree,
			int Depth, int CurDep, int Pre_id) {

		if (CurDep < Depth) {

			CurDep += 1;

			PrunedTree = PrunedTree += "(" + pre_tree.get(Pre_id).getUSTRING()
					+ " ";

			for (int i = 0; i < pre_tree.get(Pre_id).getChildren().size(); i++) {
				PrunedTree = DepthPrun(PrunedTree, pre_tree, Depth, CurDep,
						pre_tree.get(Pre_id).getChildren().get(i));
			}
			if (CurDep == Depth) {
				PrunedTree = PrunedTree + "*) ";

			} else {
				PrunedTree = PrunedTree + ") ";
			}
		}

		return PrunedTree;
	}

	private String Prun_NN(String ParseTree) {
		String PrunedTree = "";

		StringTokenizer stkn = new StringTokenizer(ParseTree);

		while (stkn.hasMoreTokens()) {
			String curtkn = stkn.nextToken();

			if (curtkn.contains("NNPS")) {
				curtkn = curtkn.replace("NNPS", "NN.*");
			} else if (curtkn.contains("NNP")) {
				curtkn = curtkn.replace("NNP", "NN.*");
			} else if (curtkn.contains("NNS")) {
				curtkn = curtkn.replace("NNS", "NN.*");
			} else if (curtkn.contains("NN") && !curtkn.contains("NN.*")) {
				curtkn = curtkn.replace("NN", "NN.*");
			}

			PrunedTree += curtkn + " ";
		}

		return PrunedTree.substring(0, PrunedTree.length() - 1);
	}

	private String Prun_CD(String ParseTree) {
		String PrunedTree = "";

		StringTokenizer stkn = new StringTokenizer(ParseTree);

		while (stkn.hasMoreTokens()) {
			String curtkn = stkn.nextToken();

			if (curtkn.contains("(CD")) {
				curtkn = curtkn.replace("(CD", "*(CD");
			}

			PrunedTree += curtkn + " ";
		}

		return PrunedTree.substring(0, PrunedTree.length() - 1);
	}

	private String Prun_DT(String ParseTree) {
		String PrunedTree = "";

		StringTokenizer stkn = new StringTokenizer(ParseTree);

		while (stkn.hasMoreTokens()) {
			String curtkn = stkn.nextToken();

			if (curtkn.contains("(DT")) {
				curtkn = curtkn.replace("(DT", "*(DT");
			}

			PrunedTree += curtkn + " ";
		}

		return PrunedTree.substring(0, PrunedTree.length() - 1);
	}

	private String Prun_VBZ(String ParseTree) {
		String PrunedTree = "";

		StringTokenizer stkn = new StringTokenizer(ParseTree);

		while (stkn.hasMoreTokens()) {
			String curtkn = stkn.nextToken();

			if (curtkn.contains("VBZ")) {
				curtkn = curtkn.replace("VBZ", "(VBZ|VB|VBP)");
			} else if (curtkn.contains("VBP")) {
				curtkn = curtkn.replace("VBP", "(VBZ|VB|VBP)");
			}

			PrunedTree += curtkn + " ";
		}

		return PrunedTree.substring(0, PrunedTree.length() - 1);
	}
}
