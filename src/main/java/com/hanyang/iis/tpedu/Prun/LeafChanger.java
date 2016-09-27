package com.hanyang.iis.tpedu.Prun;
import java.util.List;
import java.util.StringTokenizer;

public class LeafChanger {
	
	public String RemoveDot(String ParseTree){
		String Pattern = "";
		
		StringTokenizer stkn = new StringTokenizer(ParseTree);

		boolean wasdot = false;
		
		while (stkn.hasMoreTokens()) {
			String curtkn = stkn.nextToken();
			// if sentence has ./?/! erase it
			
			if(wasdot){
				wasdot = false;
				String tmp = curtkn;
				int number_brak = 0;
				
				Pattern = Pattern.substring(0,Pattern.length()-1);
				
				while(tmp.charAt(tmp.length()-1) == ')'){
					number_brak ++;
					tmp = tmp.substring(0, tmp.length()-1);
				}
				for(int i = 0;i<number_brak-1;i++){
					Pattern += ")";
				}
				
				Pattern += " ";
				
				continue;
			}
			if(curtkn.equals("(.")){
				wasdot = true;
				continue;
			}

			Pattern += curtkn + " ";
		}
		
		return Pattern.substring(0, Pattern.length() - 1);
	}

	public String ChangeLeaf(String ParseTree) {
		String Pattern = "";

		StringTokenizer stkn = new StringTokenizer(ParseTree);

		boolean wasdot = false;
		
		while (stkn.hasMoreTokens()) {
	         String curtkn = stkn.nextToken();
	         // if sentence has ./?/! erase it
	         
	         if(wasdot){
	            wasdot = false;
	            String tmp = curtkn;
	            int number_brak = 0;
	            
	            Pattern = Pattern.substring(0,Pattern.length()-1);
	            
	            while(tmp.charAt(tmp.length() - 1) == ')'){
	               number_brak ++;
	               tmp = tmp.substring(0, tmp.length()-1);
	            }
	            for(int i = 0;i<number_brak-1;i++){
	               Pattern += ")";
	            }
	            
	            Pattern += " ";
	            
	            continue;
	         }
	         
	         if(curtkn.equals("(.")){
	            wasdot = true;
	            continue;
	         }
	         
	         // if last char == ), then leaf
	         if (curtkn.charAt(curtkn.length() - 1) == ')') {
	            String Leafwords = curtkn;

	            while (Leafwords.charAt(Leafwords.length() - 1) == ')') {
	               Leafwords = Leafwords.substring(0, Leafwords.length() - 1);
	            }

	            curtkn = curtkn.replace(Leafwords, ".+");
	         }

	         Pattern += curtkn + " ";
	      }
		return Pattern.substring(0, Pattern.length() - 1);
	}
	
	public int Count_ADVP(String ParseTree){
		int num_ADVP = 0;
		
		StringTokenizer stkn = new StringTokenizer(ParseTree);
		
		while(stkn.hasMoreTokens()){
			String curtkn = stkn.nextToken();
			
			if(curtkn.contains("ADVP")){
				num_ADVP++;
			}
		}
		
		return num_ADVP;
	}
	
	public int Count_ADJP(String ParseTree){
		int num_ADJP = 0;
		
		StringTokenizer stkn = new StringTokenizer(ParseTree);
		
		while(stkn.hasMoreTokens()){
			String curtkn = stkn.nextToken();
			
			if(curtkn.contains("ADJP")){
				num_ADJP++;
			}
		}
		
		return num_ADJP;
	}
	
	public int Count_Modifier(String ParseTree){
		int num_Modifier = 0;
		
		StringTokenizer stkn = new StringTokenizer(ParseTree);
		
		while(stkn.hasMoreTokens()){
			String curtkn = stkn.nextToken();
			
			if(curtkn.contains("RBS") || curtkn.contains("RBR")|| curtkn.contains("RB")|| curtkn.contains("JJS")|| curtkn.contains("JJR")|| curtkn.contains("JJ")|| curtkn.contains("WP")|| curtkn.contains("WP$")|| curtkn.contains("PRP$")|| curtkn.contains("VBN")|| curtkn.contains("VBG")|| curtkn.contains("TO")|| curtkn.contains("IN")|| curtkn.contains("DT")|| curtkn.contains("CD")){
				num_Modifier++;
			}
		}
		
		return num_Modifier;
	}
	
	public int Count_SBAR(String ParseTree){
		int num_SBAR=0;
		StringTokenizer stkn = new StringTokenizer(ParseTree);
		
		while(stkn.hasMoreTokens()){
			String curtkn = stkn.nextToken();
			
			if(curtkn.contains("SBAR")){
				num_SBAR++;
			}
		}
		return num_SBAR;
	}
	
	public int Count_CC(String ParseTree){
		int num_CC=0;
		StringTokenizer stkn = new StringTokenizer(ParseTree);
		
		while(stkn.hasMoreTokens()){
			String curtkn = stkn.nextToken();
			
			if(curtkn.contains("CC")){
				num_CC++;
			}
		}
		return num_CC;
	}
	
	
}