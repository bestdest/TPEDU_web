package com.hanyang.iis.tpedu.TPEMatch;

import java.util.StringTokenizer;

public class QueryChk {
	private String Query;

	QueryChk() {

	}

	public QueryChk(String Query) {
		this.Query = Query;
	}
	
	public String ChangeBrakey(String Query){
		String Query2 ="";
		
		Query2 = Query.replace('(', '<');
		Query2 = Query2.replace(')', '>');
		
		if(Query2.charAt(0) == '<' && Query2.charAt(Query2.length()-1) == '>'){
			Query2 = "{" + Query2.substring(1, Query2.length()-1) + "}";	
		}
		return Query2;
	}
	
	public String RemoveRoot(String Query){
		String Query2 = "";
		
		StringTokenizer stkn = new StringTokenizer(Query);
		
		boolean WasRoot = false;
		
		while(stkn.hasMoreTokens()){
			String curtkn = stkn.nextToken();
			if(curtkn.contains("ROOT")){
				WasRoot = true;
				continue;
			}
			
			
			Query2 += curtkn + " ";
		}
		
		if(WasRoot){
			Query2 = Query2.substring(0, Query2.length()-1);
		}
		
		return Query2.substring(0, Query2.length()-1);
	}

	public String toQuery() {

		String OriQuery = this.Query;
		String Query=  OriQuery;
		

		if(Query.charAt(0) != '{' && Query.charAt(0) != '<'){
			Query = "{.+ "; 
			StringTokenizer stkn = new StringTokenizer(OriQuery);
			while(stkn.hasMoreTokens()){
				Query += "* <.+ "+stkn.nextToken() +"> ";
			}
			Query +=" *}";
		}

		StringTokenizer stkn = new StringTokenizer(Query);


		StringBuffer NQuery = new StringBuffer();
		
		while(stkn.hasMoreTokens()){

			NQuery.append(stkn.nextToken() + " ");
		}
		 
		Query = NQuery.toString().substring(0,NQuery.toString().length() - 1);
		
		Query = Query.replace("><", "> <");
		Query = Query.replace(">{", "> {");
		Query = Query.replace("}{", "} {");
		Query = Query.replace("}<", "} <");
		Query = Query.replace("* >", "*>");
		Query = Query.replace("* }", "*}");
		Query = Query.replace("> >", ">>");
		Query = Query.replace("> }", ">}");
		Query = Query.replace("} >", "}>");
		Query = Query.replace("} }", "}}");
		Query = Query.replace("< ", "<");
		Query = Query.replace("{ ", "{");
		
		System.out.println(Query);

		return Query;
	}

	public boolean Isnull() {
		if (Query.length() != 0) {
			return false;
		}
		return true;
	}

	public boolean IsCorrect() {

		int treeparent = 0;
		int cutparent = 0;
		
		for (int i = 0; i < Query.length(); i++) {
			if (Query.charAt(i) == '<') {
				treeparent++;
			}
			if (Query.charAt(i) == '>') {
				treeparent--;
			}
			if (Query.charAt(i) == '{') {
				cutparent++;
			}
			if (Query.charAt(i) == '}') {
				cutparent--;
			}
		}
		if(treeparent != 0 || cutparent != 0){
			return false;
		}

		return true;
	}

}
