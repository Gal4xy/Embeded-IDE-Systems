package com.bjsasc.utils.tree;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * @author  maobaolong
 */
public abstract class NavigatorNode extends TreeNode {

	/**
	 * @uml.property  name="text"
	 */
	private String text;
	/**
	 * @uml.property  name="image"
	 */
	private Image image;
	protected String fName;
	/**
	 * @uml.property  name="id"
	 */

	public NavigatorNode() {
		super(null);
	}
	
	public NavigatorNode(String text, ImageDescriptor imageDesc) {
		super(null);
		this.text = text;
		if(imageDesc==null) {
			image=ImageDescriptor.getMissingImageDescriptor().createImage();
		} else {
			image = imageDesc.createImage();
		}
	}

	/**
	 * @return
	 * @uml.property  name="text"
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * @return
	 * @uml.property  name="image"
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param text
	 * @uml.property  name="text"
	 */
	public void setText(String text){
		this.text = text;
	}

	public  void setImage(ImageDescriptor imageDesc) {
		if(imageDesc==null) {
			return;
		} else {
			image = imageDesc.createImage();
		}
	}

	public void setName(String name) {
		// TODO Auto-generated method stub
		fName = name;
	}

}
