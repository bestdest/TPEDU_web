package com.hanyang.iis.tpedu.TPEMatch;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PatternQuery {

	public StringTokenizer st;
	public int NumberOfPatterns;
	private List<PatternNode> Matchlist;

	PatternQuery() {
		NumberOfPatterns = 0;
	}

	public PatternQuery(StringTokenizer st) {
		this.st = st;
		NumberOfPatterns = 0;
		Matchlist = new ArrayList<PatternNode>();
		PatternNode junk = new PatternNode(-1, "", -1, -1, "", -1);
		Matchlist.add(junk);
	}
	


	
	public List<PatternNode> getMatchlist() {
		return Matchlist;
	}

	public void setMatchlist(List<PatternNode> matchlist) {
		Matchlist = matchlist;
	}

	public int SepPattern(List<PatternNode> tree, int PID) {
		int NOC = 0;
		int Children_order = 0;
		int UID = 0;
		while (st.hasMoreTokens()) {
			if (NOC != 0) {
				NOC--;
				break;
			}

			String CurToken = st.nextToken();
			String Notation = null;
			NumberOfPatterns += 1;

			UID = NumberOfPatterns;

			Children_order++;

			// Ʈ�� ������ !�� *�� ���� TPE ��Ұ� ������ insertTPE�� ó��

			if (CurToken.charAt(0) == '*') {
				// chk ������ ��ȣ ����
				// cchk ������ ��ȣ ����
				int chk = 0, cchk = 0;
				for (int i = 0; i < CurToken.length(); i++) {
					if (CurToken.charAt(i) == '<' || CurToken.charAt(i) == '{') {
						chk++;
					}
					if (CurToken.charAt(i) == '>' || CurToken.charAt(i) == '}') {
						cchk++;
					}
				}
				// 0�� �̻� �ݺ��ǹ��� *<������
				if (chk != 0) {

					NOC = InsertPTE(CurToken, tree, PID, Children_order);
					continue;

				}
				// �ݴ� ANYTREE >������
				else if (cchk != 0) {
					NOC = 0;
					int cpcnt = 0;
					List<String> close_parenthesis = new ArrayList<String>();
					while (CurToken.charAt(CurToken.length() - 1) == '>'
							|| CurToken.charAt(CurToken.length() - 1) == '}') {
						NOC++;
						cpcnt ++;
						close_parenthesis.add( CurToken.substring(CurToken.length()-1,CurToken.length()));
						CurToken = CurToken.substring(0, CurToken.length() - 1);
					}
					Notation = "*";
					// jdb.InsertPatternTree(SID, UID, CurToken,
					// PID,Children_order, Notation);

					PatternNode tmp = new PatternNode(UID, CurToken, PID, Children_order,
							Notation, -1);
					tree.add(tmp);
					Matchlist.add(tmp);
					tree.get(PID).Addchildren(UID);

					InsertAnytree(tree, UID);

					for(int i =0 ;i<cpcnt;i++){
						
						PatternNode CPNode = new PatternNode(-1,close_parenthesis.get(i),-1,-1,"",-1);
						Matchlist.add(CPNode);
					}
					
					
				}
				// ANYTREE
				else {
					Notation = "*";
					// jdb.InsertPatternTree(SID, UID, CurToken,
					// PID,Children_order, Notation);

					PatternNode tmp = new PatternNode(UID, CurToken, PID, Children_order,
							Notation, -1);
					tree.add(tmp);
					Matchlist.add(tmp);
					tree.get(PID).Addchildren(UID);

					InsertAnytree(tree, UID);

					// System.out.println(NumberOfPatterns + CurToken + PID);
				}

			}
			else if (CurToken.charAt(0) == '!') {
				NOC = InsertPTE(CurToken, tree, PID, Children_order);
				continue;
			}

			// ��ȣ ������ ���ο� Ʈ������
			// Ʈ���� ������ ��ȣ�� ����
			// ������ ��ȣ�� ���� ����-> �Լ� �� ��ŭ ���� == ������ ��ȣ�� ���� ��

			else if (CurToken.charAt(0) == '<') {
				Notation = "tree";
				String open_parenthesis = CurToken.substring(0,1);
				PatternNode OPNode = new PatternNode(-1,open_parenthesis,-1,-1,"",-1);
				Matchlist.add(OPNode);
				CurToken = CurToken.substring(1);
				// jdb.InsertPatternTree(SID, UID, CurToken, PID,
				// Children_order,Notation);
				
				
				PatternNode tmp = new PatternNode(UID, CurToken, PID, Children_order,
						Notation, -1);
				tree.add(tmp);
				Matchlist.add(tmp);
				tree.get(PID).Addchildren(UID);
				
				
				// System.out.println(NumberOfPatterns + CurToken + PID);e
				NOC = SepPattern(tree, UID);
				continue;
			}
			else if (CurToken.charAt(CurToken.length() - 1) == '>'||CurToken.charAt(CurToken.length() - 1) == '}') {
				
				
				
				
				NOC = 0;
				int cpcnt = 0;
				List<String> close_parenthesis = new ArrayList<String>();
				while (CurToken.charAt(CurToken.length() - 1) == '>'
						|| CurToken.charAt(CurToken.length() - 1) == '}') {
					NOC++;
					cpcnt ++;
					close_parenthesis.add( CurToken.substring(CurToken.length()-1,CurToken.length()));
					CurToken = CurToken.substring(0, CurToken.length() - 1);
				}
				Notation = "leaf";
				// jdb.InsertPatternTree(SID, UID, CurToken,
				// PID,Children_order, Notation);

				PatternNode tmp = new PatternNode(UID, CurToken, PID, Children_order,
						Notation, -1);
				tree.add(tmp);
				Matchlist.add(tmp);
				tree.get(PID).Addchildren(UID);

				for(int i =0 ;i<cpcnt;i++){
					
					PatternNode CPNode = new PatternNode(-1,close_parenthesis.get(i),-1,-1,"",-1);
					Matchlist.add(CPNode);
				}
				
				// System.out.println(NumberOfPatterns + CurToken +PID);
				continue;
			}
			else if (CurToken.charAt(0) == '{') {
				Notation = "cut";
				
				String open_parenthesis = CurToken.substring(0,1);
				PatternNode OPNode = new PatternNode(-1,open_parenthesis,-1,-1,"",-1);
				Matchlist.add(OPNode);

				CurToken = CurToken.substring(1);
				// jdb.InsertPatternTree(SID, UID, CurToken, PID,
				// Children_order,Notation);
				
				
				PatternNode tmp = new PatternNode(UID, CurToken, PID, Children_order,
						Notation, -1);
				tree.add(tmp);
				Matchlist.add(tmp);
				tree.get(PID).Addchildren(UID);
				
				// System.out.println(NumberOfPatterns + CurToken + PID);
				NOC = SepPattern(tree, UID);
				continue;
			} 
			else if (CurToken.charAt(CurToken.length() - 1) == '>'||CurToken.charAt(CurToken.length() - 1) == '}') {

				NOC = 0;
				while (CurToken.charAt(CurToken.length() - 1) == '>'||CurToken.charAt(CurToken.length() - 1) == '}') {
					NOC++;
					String close_parenthesis = CurToken.substring(CurToken.length()-1,CurToken.length());
					PatternNode CPNode = new PatternNode(-1,close_parenthesis,-1,-1,"",-1);
					Matchlist.add(CPNode);
					CurToken = CurToken.substring(0, CurToken.length() - 1);
				}
				Notation = "leaf";
				// jdb.InsertPatternTree(SID, UID, CurToken, PID,
				// Children_order,Notation);
				
				
				PatternNode tmp = new PatternNode(UID, CurToken, PID, Children_order,
						Notation, -1);
				tree.add(tmp);
				Matchlist.add(tmp);
				tree.get(PID).Addchildren(UID);
				
				
				// System.out.println(NumberOfPatterns + CurToken +PID);
				continue;
			}
			else {
				Notation = "leaf";
				// jdb.InsertPatternTree(SID, UID, CurToken, PID,
				// Children_order,Notation);
				
				
				PatternNode tmp = new PatternNode(UID, CurToken, PID, Children_order,
						Notation, -1);
				tree.add(tmp);
				Matchlist.add(tmp);
				tree.get(PID).Addchildren(UID);
				
				
				// System.out.println(NumberOfPatterns + CurToken +PID);
				continue;

			}
			
		}
		return NOC;
	}

	// �ִ�Ʈ�� ���� �κ� ó��

	public void InsertAnytree(List<PatternNode> tree, int PID) {
		int Children_order = 1;
		NumberOfPatterns++;
		int UID = NumberOfPatterns;
		String Notation = "anytree";
		String CurToken = "anytree";
		// jdb.InsertPatternTree(SID, UID, "ANYTREE", PID,
		// Children_order,Notation);
		
		PatternNode tmp = new PatternNode(UID, CurToken, PID, Children_order, Notation, -1);
		tree.add(tmp);
		Matchlist.add(tmp);
		tree.get(PID).Addchildren(UID);
		
		
	}

	// *!�� ���� ������ !*�� ���ٴ� ����

	public int InsertPTE(String CurToken, List<PatternNode> tree, int PID,
			int Children_Order) {
		int NOC = 0;
		String Notation = null;
		int chk = 0;
		int UID = 0;
		if (CurToken.charAt(0) == '*') {

			Notation = "*";
			CurToken = CurToken.substring(1);

			UID = NumberOfPatterns;

			// jdb.InsertPatternTree(SID, UID, "*", PID, Children_Order,
			// Notation);

			PatternNode tmp = new PatternNode(UID, "*", PID, Children_Order, Notation, -1);
			Matchlist.add(tmp);
			tree.add(tmp);
			tree.get(PID).Addchildren(UID);
			
			
			PID = UID;
			NumberOfPatterns++;
			Children_Order = 1;
			chk++;

		}

		if (CurToken.charAt(0) == '!') {
			Notation = "!";
			CurToken = CurToken.substring(1);

			if (chk != 0) {
				UID = NumberOfPatterns;
				// jdb.InsertPatternTree(SID, UID, "!", PID,
				// Children_Order,Notation);

				PatternNode tmp = new PatternNode(UID, "!", PID, Children_Order, Notation, -1);
				Matchlist.add(tmp);
				tree.add(tmp);
				tree.get(PID).Addchildren(UID);
				
				PID = UID;
				NumberOfPatterns++;
				Children_Order = 1;
			} else {
				UID = NumberOfPatterns;
				// jdb.InsertPatternTree(SID, UID, "!", PID,
				// Children_Order,Notation);

				PatternNode tmp = new PatternNode(UID, "!", PID, Children_Order, Notation, -1);
				Matchlist.add(tmp);
				tree.add(tmp);
				tree.get(PID).Addchildren(UID);
				
				PID = UID;
				NumberOfPatterns++;
				Children_Order = 1;
			}
		}

		// ��ȣ ���۵� �� - ���ο� subtree ����
		if (CurToken.charAt(0) == '<') {
			Notation = "tree";
			UID = NumberOfPatterns;

			CurToken = CurToken.substring(1);
			// jdb.InsertPatternTree(SID, UID, CurToken, PID,
			// Children_Order,Notation);
			PatternNode tmp = new PatternNode(UID, CurToken, PID, Children_Order, Notation,
					-1);
			Matchlist.add(tmp);
			tree.add(tmp);
			tree.get(PID).Addchildren(UID);
			

			PID++;

			// System.out.println(NumberOfPatterns + CurToken + PID);
		}
		if (CurToken.charAt(0) == '{') {
			Notation = "cut";
			UID = NumberOfPatterns;

			CurToken = CurToken.substring(1);
			// jdb.InsertPatternTree(SID, UID, CurToken, PID,
			// Children_Order,Notation);

			PatternNode tmp = new PatternNode(UID, CurToken, PID, Children_Order, Notation,
					-1);
			Matchlist.add(tmp);
			tree.add(tmp);
			tree.get(PID).Addchildren(UID);
			

			PID++;

			// System.out.println(NumberOfPatterns + CurToken + PID);
		}

		NOC = SepPattern(tree, PID);
		return NOC;
	}
}
