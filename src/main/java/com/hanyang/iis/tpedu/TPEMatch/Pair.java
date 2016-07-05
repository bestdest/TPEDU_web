package com.hanyang.iis.tpedu.TPEMatch;


public class Pair {
	int Isnil;
	int Isfails;
	int P;
	int T;

	Pair(){
		
	}
	
	public int getIsnil() {
		return Isnil;
	}

	public void setIsnil(int isnil) {
		Isnil = isnil;
	}

	public int getIsfails() {
		return Isfails;
	}

	public void setIsfails(int isfails) {
		Isfails = isfails;
	}

	public int getP() {
		return P;
	}

	public void setP(int p) {
		P = p;
	}

	public int getT() {
		return T;
	}

	public void setT(int t) {
		T = t;
	}

	
	public void setfails(){
		Isnil = 0;
		Isfails = 1;
	}
	
	public void setnil(){
		Isnil = 1;
		Isfails = 0;
	}
	

	Pair(int p, int t) {
		setIsfails(0);
		setIsnil(0);
		setP(p);
		setT(t);
	}
}