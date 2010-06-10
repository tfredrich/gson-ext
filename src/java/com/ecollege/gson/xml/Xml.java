package com.ecollege.gson.xml;

/*
 Copyright (c) 2002 JSON.org

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 The Software shall be used for Good, not Evil.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

import java.io.StringWriter;
import java.io.Writer;

import com.ecollege.gson.XmlParseException;
import com.google.gson.JsonElement;

/**
 * This provides methods to convert an XML text into a JsonElement, and
 * to covert a JsonElement into an XML text.
 * 
 * @author toddf
 */
public class Xml
{
	/** The Character '&'. */
	public static final Character AMP = new Character('&');

	/** The Character '''. */
	public static final Character APOS = new Character('\'');

	/** The Character '!'. */
	public static final Character BANG = new Character('!');

	/** The Character '='. */
	public static final Character EQ = new Character('=');

	/** The Character '>'. */
	public static final Character GT = new Character('>');

	/** The Character '<'. */
	public static final Character LT = new Character('<');

	/** The Character '?'. */
	public static final Character QUEST = new Character('?');

	/** The Character '"'. */
	public static final Character QUOT = new Character('"');

	/** The Character '/'. */
	public static final Character SLASH = new Character('/');

	
	// SECTION: CONVERSION

	public String toXml(JsonElement jsonElement)
	{
		StringWriter writer = new StringWriter();
		visit(jsonElement, writer);
		writer.flush();
		return writer.toString();
	}
	
	
	// SECTION: PARSING
	
	public JsonElement parse(String xml)
	throws XmlParseException
	{
		return null;
	}
	
	
	// SECTION: VISITOR
	
	private void visit(JsonElement jsonElement, Writer writer)
	{
		// visit the element, writing XML to the writer.
	}
}
