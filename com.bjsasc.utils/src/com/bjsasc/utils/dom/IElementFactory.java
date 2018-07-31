package com.bjsasc.utils.dom;

import org.w3c.dom.Element;

public interface IElementFactory {

	ElementNode createElement(Manager manager, ElementNode parent, Element element);

}
