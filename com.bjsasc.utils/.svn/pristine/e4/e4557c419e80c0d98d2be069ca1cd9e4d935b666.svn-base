package com.bjsasc.utils.dom;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bjsasc.utils.tree.TreeNode;

/**
 * @author Administrator
 */
public abstract class ElementNode extends TreeNode {

	/**
	 * @uml.property name="fManager"
	 * @uml.associationEnd
	 */
	protected Manager fManager;
	private Element fElement;
	/**
	 * @uml.property name="fParent"
	 * @uml.associationEnd
	 */
	protected Map<String, String> fAttributes = new HashMap<String, String>();
	private boolean fDirty = false;
	private String fText = "";

	public ElementNode(Manager manager, ElementNode parent) {
		super(parent);
		fManager = manager;
		setDirty(true);
	}
	public ElementNode(Manager manager, ElementNode parent, Element element) {
		super(parent);
		fManager = manager;
		fElement = element;
		NamedNodeMap attrs = fElement.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			Attr attr = (Attr) attrs.item(i);
			fAttributes.put(attr.getNodeName(), attr.getValue());
		}
	}

	public String getText() {
		return fText;
	}

	public void setText(String text) {
		fText = text;
	}

	public void save() {
		if (fDirty || (fElement == null)) {
			if (fElement == null) {
				fElement = fManager.getDocument().createElement(getElementName());
				((ElementNode) fParent).addChildElement(this);
			}

			NamedNodeMap attrs = fElement.getAttributes();
			for (int i = 0; i < attrs.getLength(); i++) {
				fElement.removeAttribute(attrs.item(i).getNodeName());
			}

			for (String name : fAttributes.keySet()) {
				fElement.setAttribute(name, fAttributes.get(name));
			}

			if (!fText.isEmpty()) {
				fElement.setTextContent(fText);
			}

			setDirty(false);
		}
	}

	public void setDirty() {
		setDirty(true);
	}

	public void setDirty(boolean dirty) {
		fDirty = dirty;
	}

	protected abstract String getElementName();

	public String getAttribute(String name) {
		return fAttributes.get(name);
	}

	public void setAttribute(String name, String value) {
		fAttributes.put(name, value);
		setDirty(true);
	}

	private void removeChildElement(ElementNode child) {
		if (fElement != null && child.fElement != null) {
			fElement.removeChild(child.fElement);
		}
	}

	private void addChildElement(ElementNode child) {
		if(fElement == null) {
			return;
		}
	
		int index = fChildren.indexOf(child);
		if (index < 0) {
			return;
		}

		Element refChild = null;
		if(fElement == null){
			return;
		}
		NodeList nodeList = fElement.getChildNodes();
		
outer:	
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if ((node.getNodeType() == Node.ELEMENT_NODE) && (node instanceof Element)) {
				Element ref = (Element) nodeList.item(i);
				for (int j = index + 1; j < fChildren.size(); j++) {
					if (ref.isSameNode(((ElementNode) fChildren.get(j)).fElement)) {
						refChild = ref;
						break outer;
					}
				}
			}
		}
		
		if(refChild != null) {
			fElement.insertBefore(child.fElement, refChild);
		} else {
			fElement.appendChild(child.fElement);
		}
	}

	public void doDelete() {
		((ElementNode) fParent).removeChildElement(this);
		fParent.removeChild(this);
	}
	
	public int getIntegerAttribute(String name, int defaultValue) {
		String s = getAttribute(name);
		if(s == null) {
			return defaultValue;
		}
		
		return Integer.parseInt(s);
	}
	
	public String getStringAttribute(String name, String defaultValue) {
		// TODO Auto-generated method stub
		String s = getAttribute(name);
		if(s == null) {
			return defaultValue;
		}
		
		return s;
	}

	/**
	 *  add by gengbo
	 */
	public void getBack(){
		fAttributes.clear();
		NamedNodeMap attrs = fElement.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			Attr attr = (Attr) attrs.item(i);
			fAttributes.put(attr.getNodeName(), attr.getValue());
		}
	}
	
	public Manager getManager(){
		return fManager;
	}
    
	public Element getElement(){
		return fElement;
	}
	
	public boolean isDirty() {
		return fDirty;
	}

	protected void dispose() {
		
	}

}
