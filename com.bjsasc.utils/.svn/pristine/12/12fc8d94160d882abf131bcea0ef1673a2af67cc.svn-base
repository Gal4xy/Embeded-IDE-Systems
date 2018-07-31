package com.bjsasc.utils.dom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bjsasc.utils.TraverseType;
import com.bjsasc.utils.tree.TreeNode;
import com.bjsasc.utils.tree.TreeNode.IAction;

/**
 * 实现DOM管理器的功能。
 */
public abstract class Manager {
	
	/**
	 *  DOM文档
	 */
	private Document fDocument;
	/**
	 *  XML文件名
	 */
	protected String fFileName;
	/**
	 *  DOM树的根元素节点
	 */
	/**
	 * @uml.property name="fRootElementNode"
	 * @uml.associationEnd
	 */
	protected ElementNode fRootElementNode;

	/**
	 * 打开XML文件，并对文件内容进行解析处理。
	 * 
	 * @param fileName
	 *           XML文件名
	 * @return <code>true</code> XML文件打开及解析成功, <code>false</code> 失败
	 */
	public boolean open(String fileName) {
		fFileName = null;
		boolean ret = true;
		//读取文档，解析XML，获得DOM树
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(fileName);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setIgnoringElementContentWhitespace(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			fDocument = db.parse(stream);

			if (fDocument != null) {
				printDomTree(fDocument);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret = false;
		}

		if (stream != null) {
			try {
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (ret == false) {
			return false;
		}
		//取得文档节点
		Element element = fDocument.getDocumentElement();
		if (element == null) {
			return false;
		}
		//构建元素树
		fRootElementNode = buildElementTree(null, element);
		fFileName = fileName;
		return true;
	}

	public ElementNode buildElementTree(ElementNode parent, Element element) {
		ElementNode elementNode = getElementFactory().createElement(this, parent, element);
		if(parent != null && elementNode != null) {
			parent.addChild(elementNode);
		}
		
		if (element.hasChildNodes()) {
			NodeList children = element.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node node = children.item(i);
				if ((node.getNodeType() == Node.ELEMENT_NODE) && (node instanceof Element)) {
					Element child = (Element) node;
					buildElementTree(elementNode, child);
				} else if((node.getNodeType() == Node.TEXT_NODE) && (elementNode != null)) {
					elementNode.setText(node.getNodeValue().trim());
				}
			}
		}

		return elementNode;
	}

	public void save() {
		save(true);
	}

	public void save(boolean saveAll) {
		try {
			if (saveAll) {
				fRootElementNode.traverse(new IAction() {

					@Override
					public TraverseType process(TreeNode node) {
						// TODO Auto-generated method stub
						((ElementNode)node).save();
						return TraverseType.CONTINUE;
					}
					
				});
			}

			File newFile = new File(fFileName);
			FileWriter newFileStream = new FileWriter(newFile);
			writeModels(fDocument, newFileStream, 0);
			newFileStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Prints the specified node, recursively. */
	private void printDomTree(Node node) {
		int type = node.getNodeType();
		switch (type) {
		// print the document element
		case Node.DOCUMENT_NODE:
			System.out.println("<?xml version=\"1.0\"  encoding=\"???\"?>");
			printDomTree(((Document) node).getDocumentElement());
			break;

		// print element and any attributes
		case Node.ELEMENT_NODE:
			System.out.print("<");
			System.out.print(node.getNodeName());

			NamedNodeMap attrs = node.getAttributes();
			for (int i = 0; i < attrs.getLength(); i++) {
				printDomTree(attrs.item(i));
			}

			System.out.print(">");
			if (node.hasChildNodes()) {
				NodeList children = node.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					printDomTree(children.item(i));
				}
			}

			System.out.print("</");
			System.out.print(node.getNodeName());
			System.out.print('>');
			break;

		// Print attribute nodes
		case Node.ATTRIBUTE_NODE:
			System.out.print(" " + node.getNodeName() + "=\"" + ((Attr) node).getValue() + "\"");
			break;

		// handle entity reference nodes
		case Node.ENTITY_REFERENCE_NODE:
			System.out.print("&");
			System.out.print(node.getNodeName());
			System.out.print(";");
			break;

		// print cdata sections
		case Node.CDATA_SECTION_NODE:
			System.out.print("<![CDATA[");
			System.out.print(node.getNodeValue());
			System.out.print("]]>");
			break;

		// print text
		case Node.TEXT_NODE:
			System.out.print(node.getNodeValue());
			break;

		case Node.COMMENT_NODE:
			System.out.print("<!--");
			System.out.print(node.getNodeValue());
			System.out.print("-->");
			break;

		// print processing instruction

		case Node.PROCESSING_INSTRUCTION_NODE:
			System.out.print("<?");
			System.out.print(node.getNodeName());
			String data = node.getNodeValue();
			{
				System.out.print(" ");
				System.out.print(data);
			}
			System.out.print("?>");
			break;
		}
	} // printDomTree(Node)

	private void writeModels(Node node, FileWriter stream, int t) throws IOException {
		switch (node.getNodeType()) {
		// print the document element
		case Node.DOCUMENT_NODE:
			stream.write("<?xml version=\"1.0\"   encoding=\"" + stream.getEncoding() + "\"?>");
			writeModels(((Document) node).getDocumentElement(), stream, 0);
			break;

		// print element and any attributes
		case Node.ELEMENT_NODE:
			stream.write('\n');
			writeTabs(stream, t);
			
			stream.write('<');
			stream.write(node.getNodeName());

			NamedNodeMap attrs = node.getAttributes();
			for (int i = 0; i < attrs.getLength(); i++) {
				stream.write('\n');
				writeModels(attrs.item(i), stream, t + 1);
			}

			stream.write(">");
			boolean hasText = false;
			boolean hasChildElement = false;
			if (node.hasChildNodes()) {
				NodeList children = node.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					Node child = children.item(i);
					writeModels(child, stream, t + 1);
					if((child.getNodeType() == Node.TEXT_NODE) && !child.getNodeValue().trim().isEmpty()) {
						hasText = true;
					}

					if(child.getNodeType() == Node.ELEMENT_NODE) {
						hasChildElement = true;
					}
				}
			}

			if(!hasText && ((attrs.getLength() > 0) || hasChildElement)) {
				stream.write('\n');
				writeTabs(stream, t);
			}
			
			stream.write("</");
			stream.write(node.getNodeName());
			stream.write('>');
			break;

		// Print attribute nodes
		case Node.ATTRIBUTE_NODE:
			writeTabs(stream, t + 1);
			
			String attrValue = ((Attr) node).getValue();
			attrValue = attrValue.replaceAll("&", "&amp;");
			stream.write(node.getNodeName() + "=\"" + attrValue + "\"");
			break;

		// handle entity reference nodes
		case Node.ENTITY_REFERENCE_NODE:
			stream.write("&");
			stream.write(node.getNodeName());
			stream.write(";");
			break;

		// print cdata sections
		case Node.CDATA_SECTION_NODE:
			stream.write("<![CDATA[");
			stream.write(node.getNodeValue());
			stream.write("]]>\n");
			break;

		// print text
		case Node.TEXT_NODE:
			stream.write(node.getNodeValue().trim());
			break;

		case Node.COMMENT_NODE:
			stream.write("<!--");
			stream.write(node.getNodeValue());
			stream.write("-->\n");
			break;

		// print processing instruction
		case Node.PROCESSING_INSTRUCTION_NODE:
			stream.write("<?");
			stream.write(node.getNodeName());
			String data = node.getNodeValue();
			{
				stream.write(" ");
				stream.write(data);
			}
			stream.write("?>\n");
			break;
		}
	}

	private void writeTabs(FileWriter stream, int t) throws IOException {
		// TODO Auto-generated method stub
		for (int i = 0; i < t; i++) {
			stream.write('\t');
		}
	}

	public Document getDocument() {
		// TODO Auto-generated method stub
		return fDocument;
	}

	protected abstract IElementFactory getElementFactory();

	public String getFileName() {
		// TODO Auto-generated method stub
		return fFileName;
	}
	
	public boolean newFile(String fileName, String rootElementName) {
		try {
			File f = new File(fileName);
			if (f.exists()) {
				f.delete();
			}
			
			f.createNewFile();
			FileWriter newFileStream = new FileWriter(f);
			newFileStream.write("<?xml version=\"1.0\"  encoding=\"" + newFileStream.getEncoding() + "\"?>\n");
			newFileStream.write("<" + rootElementName + "/>\n");
			newFileStream.close();
			return open(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean isOpened() {
		return fFileName != null;
	}

	public boolean isDirty() {
		if (!isOpened()) {
			return false;
		}
		
		final boolean dirty[] = new boolean[] { false };
		fRootElementNode.traverse(new IAction() {

			@Override
			public TraverseType process(TreeNode node) {
				// TODO Auto-generated method stub
				if (((ElementNode) node).isDirty()) {
					dirty[0] = true;
					return TraverseType.BREAK;
				}

				return TraverseType.CONTINUE;
			}
			
		});
		
		return dirty[0];
	}

	public void close() {
		// TODO Auto-generated method stub
		fRootElementNode.traverse(new IAction() {

			@Override
			public TraverseType process(TreeNode node) {
				// TODO Auto-generated method stub
				((ElementNode) node).dispose();
				return TraverseType.CONTINUE;
			}
			
		});
		
		fFileName = null;
		fDocument = null;
		fRootElementNode = null;
	}

}
