package com.bjsasc.utils.tree;

import java.util.ArrayList;
import java.util.List;

import com.bjsasc.utils.TraverseType;

public class TreeNode {

	protected TreeNode fParent;
	protected List<TreeNode> fChildren = new ArrayList<TreeNode>();

	public TreeNode(TreeNode parent) {
		// TODO Auto-generated constructor stub
		fParent = parent;
	}

	public void addChild(TreeNode child) {
		if (!fChildren.contains(child)) {
			fChildren.add(child);
		}
	}
	
	public void removeChild(TreeNode child) {
		if(fChildren.contains(child)) {
			fChildren.remove(child);
		}
	}
	
	public void removeAllChildren() {
		fChildren.removeAll(fChildren);
	}

	public void setParent(TreeNode parent) {
		fParent = parent;
	}

	public TreeNode getParent() {
		return fParent;
	}

	public List<TreeNode> getChildren() {
		return fChildren;
	}

	public TraverseType traverse(IAction action) {
		return traverse(action, true);
	}
	
	public TraverseType traverse(IAction action, boolean isRootIncluded) {
		TraverseType tt = TraverseType.CONTINUE;
		if (isRootIncluded) {
			tt = action.process(this);
			if (tt == TraverseType.BREAK) {
				return TraverseType.BREAK;
			}
		}
		
		for (int i = 0; tt != TraverseType.SKIP_CHILDREN && i < fChildren.size();) {
			TreeNode child = fChildren.get(i);
			TraverseType ctt = child.traverse(action, true);
			if (ctt == TraverseType.BREAK) {
				return TraverseType.BREAK;
			}
			
			if (TraverseType.SKIP_SIBLING == ctt) {
				break;
			}
			
			if (fChildren.contains(child)) {
				i++;
			}
		}
		
		return TraverseType.CONTINUE;
	}
	
	/**
	 * 在树遍历过程中，对每个遍历到的树节点进行处理的接口定义。
	 * @author haberlee
	 *
	 */
	public interface IAction {
		
		/**
		 * 对树节点进行处理的抽象方法
		 * @param node 被处理的树节点
		 * @return <code>_continue</code>指示继续进行遍历过程，<code>_break</code>遍历过程终止，
		 * <code>_skip_children</code>跳过其所有子节点的遍历过程
		 */
		TraverseType process(TreeNode node);
	
	}

	public boolean hasChildren() {
		return fChildren.size() > 0;
	}
	
}
