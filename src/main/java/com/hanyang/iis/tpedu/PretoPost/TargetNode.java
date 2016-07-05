package com.hanyang.iis.tpedu.PretoPost;

import java.util.ArrayList;
import java.util.List;

public class TargetNode {
	private int SID;
	private int UID;
	private String USTRING;
	private int PID;
	private int Children_Order;
	private int Post_ID;
	private int height;
	private ArrayList<Integer> Children;
	
	
	public TargetNode() {
		this.Children = new ArrayList<Integer>();
	}
	public TargetNode(int sID, int uID, String uSTRING, int pID,int children_Order, int post_ID) {
		super();
		SID = sID;
		UID = uID;
		USTRING = uSTRING;
		PID = pID;
		Children_Order = children_Order;
		Post_ID = post_ID;
		this.height = -1;
		Children = new ArrayList<Integer>();
	}
	
	
	public TargetNode(int sID, int uID, String uSTRING, int pID,int children_Order, int post_ID, int height) {
		super();
		SID = sID;
		UID = uID;
		USTRING = uSTRING;
		PID = pID;
		Children_Order = children_Order;
		Post_ID = post_ID;
		this.height = height;
		Children = new ArrayList<Integer>();
	}
	
	
	public int getSID() {
		return SID;
	}
	public void setSID(int sID) {
		SID = sID;
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
	public void addChildren(Integer Child){
		Children.add(Child);
	}
	
	public int PostOrderTrvs(int SID, List<TargetNode> pre_tree,int NumOfNodes) {

		int Post_ID = -1;

		int cur_height = -1;

		int Max_Children_height = -1;

		for (int i = 0; i < Children.size(); i++) {
			int Cur_Child_Post_ID = pre_tree.get(Children.get(i)).PostOrderTrvs(
					SID, pre_tree, NumOfNodes);
			NumOfNodes = Cur_Child_Post_ID;
			if (Max_Children_height < pre_tree.get(Children.get(i)).getHeight()) {
				Max_Children_height = pre_tree.get(Children.get(i)).getHeight();
			}
		}

		if (Children.size() == 0) {
			cur_height = 1;
		} else {
			cur_height = Max_Children_height + 1;
		}

		setHeight(cur_height);

		Post_ID = NumOfNodes + 1;

		setPost_ID(Post_ID);

		// PID Update

		for (int i = 0; i < Children.size(); i++) {
			pre_tree.get(Children.get(i)).setPID(Post_ID);
		}

		return Post_ID;
	}

	public void BuildPostOrderTree(int SID, List<TargetNode> pre_tree,String sent,String tree,List<TargetNode> post_tree) {


		for (int i = 0; i < Children.size(); i++) {
			pre_tree.get(Children.get(i)).BuildPostOrderTree(SID, pre_tree,sent,tree,post_tree);
		}
		
		String CurStr = getUSTRING();
		if(CurStr.contains("'")){
			CurStr = CurStr.replace("'", "\\'");
			setUSTRING(CurStr);			
		}
		
		String cc = "";
		/*
		Morphology id = new Morphology();
		String cc = id.stem(getUSTRING());*/
				
		String tmp = getUSTRING();
		
		
		TargetNode tmpNode = new TargetNode(SID, getUID(), getUSTRING(), getPID(), getChildren_Order(), getPost_ID());

		post_tree.add(tmpNode);
		
		//jdb.InsertPostORI(SID, getUID(), getUSTRING(), getPID(), getChildren_Order(),getPost_ID(), getHeight(),cc,sent,tree);
	}
	
	
}
