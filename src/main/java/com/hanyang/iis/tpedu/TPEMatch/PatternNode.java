package com.hanyang.iis.tpedu.TPEMatch;

import java.util.ArrayList;
import java.util.List;

public class PatternNode {
	private int UID;
	private String USTRING;
	private int PID;
	private int Children_Order;
	private String Notation;
	private int Post_ID;
	private int height;

	private ArrayList<Integer> Children;

	PatternNode() {
		this.Children = new ArrayList<Integer>();
	}

	public PatternNode(int UID, String USTRING, int PID, int CO, String Notation, int Post_ID) {

		setUID(UID);
		setUSTRING(USTRING);
		setPID(PID);
		setChildren_Order(CO);
		setNotation(Notation);
		setPost_ID(Post_ID);
		setHeight(-1);
		this.Children = new ArrayList<Integer>();
	}

	PatternNode(int UID, String USTRING, int PID, int CO, String Notation,
			int Post_ID, int Height) {

		setUID(UID);
		setUSTRING(USTRING);
		setPID(PID);
		setChildren_Order(CO);
		setNotation(Notation);
		setPost_ID(Post_ID);
		setHeight(Height);
		this.Children = new ArrayList<Integer>();
	}

	public int getUID() {
		return UID;
	}

	public void setUID(int uID) {
		UID = uID;
	}

	public String getUSTRING() {
		return USTRING;
	}

	public void setUSTRING(String uSTRING) {
		USTRING = uSTRING;
	}

	public int getPID() {
		return PID;
	}

	public void setPID(int pID) {
		PID = pID;
	}

	public int getChildren_Order() {
		return Children_Order;
	}

	public void setChildren_Order(int children_Order) {
		Children_Order = children_Order;
	}

	public String getNotation() {
		return Notation;
	}

	public void setNotation(String notation) {
		Notation = notation;
	}

	public int getPost_ID() {
		return Post_ID;
	}

	public void setPost_ID(int post_ID) {
		Post_ID = post_ID;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ArrayList<Integer> getChildren() {
		return Children;
	}

	public void setChildren(ArrayList<Integer> children) {
		Children = children;
	}

	public void Addchildren(Integer child) {
		Children.add(child);
	}

	public int PostOrderTrvs(int SID, List<PatternNode> tree, int NumOfNodes) {

		int Post_ID = -1;

		int cur_height = -1;

		int Max_Children_height = -1;

		for (int i = 0; i < Children.size(); i++) {
			int Cur_Child_Post_ID = tree.get(Children.get(i)).PostOrderTrvs(SID, tree, NumOfNodes);
			NumOfNodes = Cur_Child_Post_ID;
			if (Max_Children_height < tree.get(Children.get(i)).getHeight()) {
				Max_Children_height = tree.get(Children.get(i)).getHeight();
			}
		}

		if (Children.size() == 0) {
			cur_height = 1;
		} else {
			if (getUSTRING().equals("!") || getUSTRING().equals("*")) {
				cur_height = Max_Children_height;
			} else {
				cur_height = Max_Children_height + 1;
			}
		}

		setHeight(cur_height);

		Post_ID = NumOfNodes + 1;

		setPost_ID(Post_ID);

		// PID Update

		for (int i = 0; i < Children.size(); i++) {
			tree.get(Children.get(i)).setPID(Post_ID);
		}

		return Post_ID;
	}

	public void PostOrderInsert(int SID, List<PatternNode> PostTree, List<PatternNode> tree) {

		for (int i = 0; i < Children.size(); i++) {
			tree.get(Children.get(i)).PostOrderInsert(SID, PostTree, tree);
		}
		
		PatternNode node = new PatternNode(getUID(),getUSTRING(),getPID(),getChildren_Order(),getNotation(),getPost_ID(),getHeight());
		PostTree.add(node);
		
	}
}
