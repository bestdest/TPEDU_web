package com.hanyang.iis.tpedu.TPEMatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hanyang.iis.tpedu.PretoPost.TargetNode;

public class Matcher {
	public HashMap<Integer, HashMap<Integer, List<Pair>>> B;
	public HashMap<List<Integer>, HashMap<List<Integer>, List<Pair>>> R;

	public Matcher() {
		B = new HashMap<Integer, HashMap<Integer,List<Pair>>>();
		R = new HashMap<List<Integer>, HashMap<List<Integer>,List<Pair>>>();
	}
	
	public void init_B(int i){
		HashMap<Integer, List<Pair>> tmp = new HashMap<Integer, List<Pair>>();
		B.put(i, tmp);
	}
	
	public void init_B(int i,int j){
		Pair init_p = new Pair();
		init_p.setfails();
		
		List<Pair> P = new ArrayList<Pair>();
		P.add(init_p);
		
		B.get(i).put(j, P);
	}
	
	public void init_R(List<Integer> x){
		HashMap<List<Integer>,List<Pair>> tmp = new HashMap<List<Integer>, List<Pair>>();
		R.put(x, tmp);
	}
	
	public void init_R(List<Integer> x,List<Integer> y){
		
		Pair init_r = new Pair();
		init_r.setnil();
		
		List<Pair> P = new ArrayList<Pair>();
		P.add(init_r);
		
		R.get(x).put(y, P);
	}
	
	public void Insert_B(int i, int j, List<Pair> Pairset){
		HashMap<Integer,List<Pair>> tmp = new HashMap<Integer, List<Pair>>();
		tmp.put(j, Pairset);
		B.get(i).put(j, Pairset);
	}
	public void Insert_R(List<Integer> x,List<Integer> y,List<Pair> Pairset){
		HashMap<List<Integer>,List<Pair>> tmp = new HashMap<List<Integer>, List<Pair>>();
		tmp.put(y, Pairset);
		R.get(x).put(y, Pairset);
	}
	public int Head(List<Integer> Seq) {
		return Seq.get(0);
	}
	public List<Integer> Tail(List<Integer> Seq){
		return Seq.subList(1, Seq.size());
	}
	
	public List<Integer> Children(PatternNode PNode) {
		return PNode.getChildren();
	}

	public int Child(PatternNode PNode) {
		return PNode.getChildren().get(0);
	}
	public List<Integer> Children(TargetNode TNode) {
		return TNode.getChildren();
	}

	public int Child(TargetNode TNode) {
		return TNode.getChildren().get(0);
	}

	public List<Pair> treePatternMatch(List<PatternNode> P,List<TargetNode> T){
		
		for(int i = 1 ; i<P.size();i++){
			init_B(i);
			if(P.get(i).getNotation().equals("*")){
				continue;
			}
			for(int j=1;j<T.size();j++){
				init_B(i, j);
				switch (P.get(i).getNotation()) {
				case "anytree":
					List<Pair> Pairset = new ArrayList<Pair>();
					Insert_B(i, j, Pairset);
					break;
				case "!":
					if(B.get(Child(P.get(i))).get(j).size() != 0){
						//B[c(i),j]�� 0��°�ε����� fail ���� �־����
						if(B.get(Child(P.get(i))).get(j).get(0).Isfails == 1){
							List<Pair> Pairset1 = new ArrayList<Pair>();
							Insert_B(i, j, Pairset1);
						}
					}
					break;
				case "tree":
					if(T.get(j).getUSTRING().matches(P.get(i).getUSTRING())){
						Insert_B(i, j, match(Children(P.get(i)), Children(T.get(j)),false,P,T));
					}
					break;
				case "cut":
					if(T.get(j).getUSTRING().matches(P.get(i).getUSTRING())){
						Insert_B(i, j, match(Children(P.get(i)), Children(T.get(j)),true,P,T));
					}
					break;
				case "leaf":
					//j �� �����϶�
					if(P.get(i).getUSTRING().contains("/")){
						//sep pattern
						
					}
					else if(T.get(j).getChildren().size() == 0
					&& T.get(j).getUSTRING().matches(P.get(i).getUSTRING())){
						List<Pair> Pairset2 = new ArrayList<Pair>();
						Insert_B(i,j,Pairset2);
					}
					break;
				default:
					break;
				}
			}
		}
		//���Ǻ� ����	
		
		List<Pair> Result = new ArrayList<Pair>();
		
		//System.out.println(T.get(1).getSID() + "  Result");
		
		if(B.size() != 0 && B.get(B.size()).size() != 0){
			
			//find all not fails T
			
			for(int i = B.get(B.size()).size();i>1;i--){
				if(B.get(B.size()).get(i).size()!=0){
					if(B.get(B.size()).get(i).get(0).Isfails != 1){
						PrintResult(B.size(), i,Result);
						break;
					}
				}
			}
		}
		//System.out.println();
		return Result;
	}
	
	public void PrintResult(int i,int j,List<Pair> Result){
		if(B.get(i).get(j).size() == 0){
			Pair tmp = new Pair(i, j);
			Result.add(tmp);
			//System.out.println("B["+(i-1)+","+(j-1)+"]");
		}else{
			Pair tmp = new Pair(i, j);
			Result.add(tmp);
			//System.out.println("B["+(i-1)+","+(j-1)+"]");
			for(int k = B.get(i).get(j).size()-1;k>=0;k--){
				PrintResult(B.get(i).get(j).get(k).P, B.get(i).get(j).get(k).T,Result);
			}
		}
	}
	
	public List<Pair> match(List<Integer> x,List<Integer> y,boolean cut,List<PatternNode> P,List<TargetNode> T){
		/*
		 * case 1 : x�� �ְ� y�� ���°��
		 * 		get(x)������ ���� ������ �� null�� �ƴϸ� x�� �ִٴ� �Ű�
		 * 		�׷��� y�� Ȯ��  �غ��ߴ볪....
		 * 		get(Y)�ؼ� ���� �� null�� ������
		 * 		���� ���� y init(x,y)���ְ� ����
		 * 
		 * case 2 : x,y �Ѵ� �ִ°��
		 * 		get(x) ���� �� �� y�� ���� ������
		 * 		�ߴ��� �׳� ��
		 * 
		 * case 3 : x,y�Ѵ� ���°��
		 * 		get(x)���� �� ������ �� ����� �Ѵ�
		 * 		init(x),init(x,y) �Ѵ� �ϸ� ��
		 */
 		int chk = 0;
		
		//nil check
		if(R.get(x) == null){
			init_R(x);
			init_R(x,y);
			R.get(x).get(y).get(0).setfails();
		}
		//x �� ������
		else{
			if(R.get(x).get(y) == null){
				init_R(x,y);
				R.get(x).get(y).get(0).setfails();
			}
			else{
				return(R.get(x).get(y));
			}
		}
		
		//if(x=e)
		if(x.size() == 0){
			chk = 1;
			//then if(y=e) R[x,y] <- 0
			if(y.size() == 0){
				List<Pair> Pairset = new ArrayList<Pair>();
				Insert_R(x, y, Pairset);
			}
		}
		
		//else if (head(x) = "*"
		else if(chk != 1 && P.get(Head(x)).getUSTRING().equals("*")){
			chk = 1;
			
			List<Pair> L = new ArrayList<Pair>();
			
			
			//if l <- match(tail(x),y,cut) != fails
			//then R[x,y] <- L
			L = match(Tail(x),y,cut,P,T);
			
			int NOTFAIL = 0;
			
			//�������϶�  not fail
			if(L.size() == 0){
				Insert_R(x, y, L);
				NOTFAIL ++;
			}
			//�������� �ƴϸ� not fail Ȯ��
			else if(L.size() != 0){
				if(L.get(0).Isfails != 1){
					Insert_R(x, y, L);
					NOTFAIL ++;
				}
			}
			
			//else if y != e and B[child(head(x),head(y)] != fails
			
			if (NOTFAIL == 0 && y.size() != 0){
				//B[child(head(x),head(y)] != fails
				//�������̸� not fail
				if(B.get(Child(P.get(Head(x)))).get(Head(y)).size() == 0){
					//then if(L<-match(x,tail(y),cut != fails)
					//then R[x,y]<-child(head(x)),head(y) U L
					L = match(x,Tail(y),cut,P,T);
					//�������̸� not fail
					if(L.size() == 0){
						Pair a = new Pair(Child(P.get(Head(x))),Head(y));
						Insert_R(x,y,Union(L,a));
					}
					//�������� �ƴϸ� fail chk
					else if(L.get(0).Isfails != 1){
						Pair a = new Pair(Child(P.get(Head(x))),Head(y));
						Insert_R(x,y,Union(L,a));
					}
					else{
						if( cut == true && x.size() != 0 && y.size() != 0 && T.get(Head(y)).getChildren().size() != 0 ){
							//if ((L <- match(x,cons(children(head(y)),tail(y)),cut)) != fails) then R[x,y]<-L
							L = new ArrayList<Pair>();
							L = match(x, Cons(Children(T.get(Head(y))),Tail(y)), cut, P, T);

							if(L.size() != 0){
								if(L.get(0).Isfails != 1){
									Insert_R(x,y,L);
								}
							}
							//�������̸� not null
							else{
								Insert_R(x,y,L);
							}
						}
					}
				}
				//�������� �ƴϸ� fail check
				else{
					if(B.get(Child(P.get(Head(x)))).get(Head(y)).get(0).Isfails != 1){
						L = match(x,Tail(y),cut,P,T);
						//if(L<-match(x,tail(y),cut != fails)
						//then R[x,y]<-child(head(x)),head(y) U L
						//�������̸� not fail
						if(L.size() == 0){
							Pair a = new Pair(Child(P.get(Head(x))),Head(y));
							Insert_R(x,y,Union(L,a));
						}
						//�������� �ƴϸ� fail chk
						else if(L.get(0).Isfails != 1){
							Pair a = new Pair(Child(P.get(Head(x))),Head(y));
							Insert_R(x,y,Union(L,a));
						}
						else{
							//cut node
							if( cut == true && x.size() != 0 && y.size() != 0 && T.get(Head(y)).getChildren().size() != 0 ){
								//if ((L <- match(x,cons(children(head(y)),tail(y)),cut)) != fails) then R[x,y]<-L
								L = new ArrayList<Pair>();
								L = match(x, Cons(Children(T.get(Head(y))),Tail(y)), cut, P, T);

								if(L.size() != 0){
									if(L.get(0).Isfails != 1){
										Insert_R(x,y,L);
									}
								}
								//�������̸� not null
								else{
									Insert_R(x,y,L);
								}
							}
						}
						
					}
				}
			}
		}
		
		/*
		 * y �� �������� �ƴҶ�
		 * B�� �������̰ų� fails�� �ƴϸ� ����
		 * match�� �������̰ų� fail�� �ƴϸ� ���Ͽ�
		 * 4������찡 ����
		 */
		
		//else if y!=e and B[head(x),head(y) != fail
		else if(y.size() != 0 ){
			if(B.get(Head(x)).size() != 0){
				if(B.get(Head(x)).get(Head(y)).size() != 0){
					//fail�� �ƴϸ�
					if(B.get(Head(x)).get(Head(y)).get(0).Isfails != 1){

						chk = 1;
						List<Pair> L = new ArrayList<Pair>();
						L = match(Tail(x), Tail(y), cut, P, T);
						//if(L<- match(tail(x),tail(y),cut) != fail)
						//then R[x,y] <- {<head(x),head(y)>} U L
						if(L.size() != 0){
							if(L.get(0).Isfails != 1){
								Pair a = new Pair(Head(x),Head(y));
								Insert_R(x,y,Union(L, a));
							}
						}
						//L�� ������ == not fail
						else{
							Pair a = new Pair(Head(x),Head(y));
							Insert_R(x,y,Union(L, a));
						}
					}
				}
				//�������̸�  == not fail
				else{
					chk = 1;
					List<Pair> L = new ArrayList<Pair>();
					L = match(Tail(x), Tail(y), cut, P, T);
					//if(L<- match(tail(x),tail(y),cut) != fail)
					if(L.size() != 0){
						if(L.get(0).Isfails != 1){
							Pair a = new Pair(Head(x),Head(y));
							Insert_R(x,y,Union(L, a));
						}
					}
					//L�� ������ == not fail
					else{
						Pair a = new Pair(Head(x),Head(y));
						Insert_R(x,y,Union(L, a));
					}
				}
			}
		}
		
		//cut node
		//else if (cut = true and x!= e and y!=e and head(y) is not leaf)
		if( cut == true && x.size() != 0 && y.size() != 0 && T.get(Head(y)).getChildren().size() != 0 && chk != 1){
			//if ((L <- match(x,cons(children(head(y)),tail(y)),cut)) != fails) then R[x,y]<-L
			List<Pair> L = new ArrayList<Pair>();
			L = match(x, Cons(Children(T.get(Head(y))),Tail(y)), cut, P, T);

			if(L.size() != 0){
				if(L.get(0).Isfails != 1){
					Insert_R(x,y,L);
				}
			}
			//�������̸� not null
			else{
				Insert_R(x,y,L);
			}
		}
		
		return R.get(x).get(y);
	}
	
	public List<Integer> Cons(List<Integer> x,List<Integer> y){
		List<Integer> tmp = new ArrayList<Integer>();
		
		tmp.addAll(x);
		tmp.addAll(y);
		
		return tmp;
	}
	
	public List<Pair> Union(List<Pair> L,Pair a){
		
		List<Pair> tmp = new ArrayList<Pair>();
		
		tmp.addAll(L);
		
		for(int i = 0;i<tmp.size();i++){
			if(tmp.get(i).equals(a)){
				return tmp;
			}
		}
		
		tmp.add(a);
		return tmp;
	}
}
