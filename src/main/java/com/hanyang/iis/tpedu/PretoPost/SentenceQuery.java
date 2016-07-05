package com.hanyang.iis.tpedu.PretoPost;


import java.util.List;
import java.util.StringTokenizer;

public class SentenceQuery {

	public StringTokenizer st;
	public int NOs;
	
	SentenceQuery(){
		NOs = 0;
	}
	SentenceQuery(StringTokenizer st){
		this.st = st;
		NOs= 0;
	}
	
	public int SepSent(int SID,int Pnum,List<TargetNode> tree){
		int NOC = 0;
		int Children_order = 0;
		while(st.hasMoreTokens()){
			if(NOC != 0){
				NOC --;
				break;
			}
			
			String curToken = st.nextToken();
			NOs += 1;
			
			if(curToken.charAt(0)=='('){
				Children_order ++;
				curToken = curToken.substring(1);
				//jdb.InsertSentenceTree(SID, NOs, tmp, Pnum,Children_order);
				//System.out.println(NOs + tmp + Pnum);
				
				TargetNode tmp = new TargetNode(SID,NOs,curToken,Pnum,Children_order,-1);
				tree.add(tmp);
				tree.get(Pnum).addChildren(NOs);
				
				NOC = SepSent(SID,NOs,tree);
			}
			else if(curToken.charAt(curToken.length()-1)==')'){
				Children_order ++;
				NOC = 0;
				while(curToken.charAt(curToken.length()-1) ==')'){
					NOC++;
					curToken = curToken.substring(0, curToken.length()-1);
				}
				int i = curToken.length();
				//jdb.InsertSentenceTree(SID, NOs, tmp, Pnum,Children_order);
				//System.out.println(NOs + tmp +Pnum);
				TargetNode tmp = new TargetNode(SID,NOs,curToken,Pnum,Children_order,-1);
				tree.add(tmp);
				tree.get(Pnum).addChildren(NOs);
			}	
		}
		return NOC;
	}
}
