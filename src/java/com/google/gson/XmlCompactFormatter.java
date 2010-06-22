/*
    Copyright 2010, Strategic Gains, Inc.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package com.google.gson;

import java.io.IOException;
import java.util.Stack;

/**
 * Visits
 * 
 * @author toddf
 * @since Jun 10, 2010
 */
public class XmlCompactFormatter
implements JsonFormatter
{
	private final boolean escapeHtmlChars;

	public XmlCompactFormatter()
	{
		this(true);
	}

	public XmlCompactFormatter(boolean escapeHtmlChars)
	{
		this.escapeHtmlChars = escapeHtmlChars;
	}

	@Override
	public void format(JsonElement root, Appendable writer, boolean serializeNulls)
	throws IOException
	{
		if (root == null)
		{
			return;
		}

		XmlFormattingVisitor visitor = new XmlFormattingVisitor(writer, new Escaper(escapeHtmlChars), serializeNulls);
		JsonTreeNavigator navigator = new JsonTreeNavigator(visitor, serializeNulls);
		navigator.navigate(root);
		visitor.finish();
	}
}

final class XmlFormattingVisitor
implements JsonElementVisitor
{
	private static final boolean SHOULD_TRACE = true;

	private static final String DEFAULT_ARRAY_ELEMENT_NAME = "list";
    private static final String DEFAULT_ARRAY_ITEM_ELEMENT_NAME = "item";
    private static final String DEFAULT_ROOT_ELEMENT_NAME = "root";

	private final Appendable writer;
	private final Escaper escaper;
	private final boolean serializeNulls;
	private final TokenStack tokens = new TokenStack();
	
	XmlFormattingVisitor(Appendable writer, Escaper escaper, boolean serializeNulls)
	{
		this.writer = writer;
		this.escaper = escaper;
		this.serializeNulls = serializeNulls;
	}
	
	public void finish()
	throws IOException
	{
		if (SHOULD_TRACE)
			System.out.println("XmlFormattingVisitor.finish()");

		do
		{
			while (!tokens.isEmpty())
			{
				writeEndElement(tokens.pop());
			}
		}
		while (tokens.reduceScope());
	}

	@Override
	public void startObject(JsonObject object)
	throws IOException
	{
		if (SHOULD_TRACE)
			System.out.println("XmlFormattingVisitor.startObject()");
		
		if (isUnnamedObject(object))
		{				
			tokens.push(DEFAULT_ROOT_ELEMENT_NAME);
			writeStartElement(tokens.peek());
		}
	}

	@Override
	public void endObject(JsonObject object)
	throws IOException
	{
		if (SHOULD_TRACE)
			System.out.println("XmlFormattingVisitor.endObject()");

		if (!tokens.isEmpty())
		{
			writeEndElement(tokens.pop());
		}
	}

	@Override
	public void startArray(JsonArray array)
	throws IOException
	{
		if (SHOULD_TRACE)
			System.out.println("XmlFormattingVisitor.startArray()");
		
		if (isUnnamedArray(array))
		{
			tokens.increaseScope();
			tokens.push(DEFAULT_ARRAY_ELEMENT_NAME);
			writeStartElement(tokens.peek());
		}
	}

	@Override
	public void endArray(JsonArray array)
	throws IOException
	{
		if (SHOULD_TRACE)
			System.out.println("XmlFormattingVisitor.endArray()");

		if (!tokens.isEmpty())
		{
			writeEndElement(tokens.pop());
		}

		if (tokens.isScopeIncreased())
		{
			if (!tokens.isEmpty())
			{
				writeEndElement(tokens.pop());
			}

			tokens.reduceScope();
		}
	}

	@Override
	public void visitArrayMember(JsonArray parent, JsonPrimitive member, boolean isFirst)
	throws IOException
	{
		if (SHOULD_TRACE)
			System.out.println("XmlFormattingVisitor.visitArrayMember(array,primitive,boolean)");

		writeStartElement(DEFAULT_ARRAY_ITEM_ELEMENT_NAME);
		writer.append(escaper.escapeJsonString(member.getAsString()));
		writeEndElement(DEFAULT_ARRAY_ITEM_ELEMENT_NAME);
	}

	@Override
	public void visitArrayMember(JsonArray parent, JsonArray member, boolean isFirst)
	throws IOException
	{
		if (SHOULD_TRACE)
			System.out.println("XmlFormattingVisitor.visitArrayMember(array,array,boolean)");

		tokens.push(DEFAULT_ARRAY_ITEM_ELEMENT_NAME);
//		tokens.increaseScope();
	}

	@Override
	public void visitArrayMember(JsonArray parent, JsonObject member, boolean isFirst)
	throws IOException
	{
		if (SHOULD_TRACE)
			System.out.println("XmlFormattingVisitor.visitArrayMember(array,object,boolean)");

		tokens.push(DEFAULT_ARRAY_ITEM_ELEMENT_NAME);
		writeStartElement(tokens.peek());
	}

	@Override
	public void visitNull()
	throws IOException
	{
		if (SHOULD_TRACE)
			System.out.println("XmlFormattingVisitor.visitNull()");
	}

	@Override
	public void visitNullArrayMember(JsonArray parent, boolean isFirst)
	throws IOException
	{
		if (SHOULD_TRACE)
			System.out.println("XmlFormattingVisitor.visitNullArrayMember()");
	}

	@Override
	public void visitNullObjectMember(JsonObject parent, String memberName, boolean isFirst)
	throws IOException
	{
		if (SHOULD_TRACE)
			System.out.println("XmlFormattingVisitor.visitNullObjectMember(object,string,boolean)");
		
		if (serializeNulls)
		{
			writeStartElement(memberName);
			writeEndElement(memberName);
		}
	}

	@Override
	public void visitObjectMember(JsonObject parent, String memberName, JsonPrimitive member, boolean isFirst)
	throws IOException
	{
		if (SHOULD_TRACE)
			System.out.println("XmlFormattingVisitor.visitObjectMember(object,string,primitive,boolean)");

		writeStartElement(memberName);
		writer.append(escaper.escapeJsonString(member.getAsString()));
		writeEndElement(memberName);
	}

	@Override
	public void visitObjectMember(JsonObject parent, String memberName, JsonArray member, boolean isFirst)
	throws IOException
	{
		if (SHOULD_TRACE)
			System.out.println("XmlFormattingVisitor.visitObjectMember(object,string,array,boolean)");

		tokens.push(memberName);
		writeStartElement(tokens.peek());
	}

	@Override
	public void visitObjectMember(JsonObject parent, String memberName, JsonObject member, boolean isFirst)
	throws IOException
	{
		if (SHOULD_TRACE)
			System.out.println("XmlFormattingVisitor.visitObjectMember(object,string,object,boolean)");

		tokens.push(memberName);
		writeStartElement(tokens.peek());
	}

	@Override
	public void visitPrimitive(JsonPrimitive primitive)
	throws IOException
	{
		if (SHOULD_TRACE)
			System.out.println("XmlFormattingVisitor.visitPrimitive()");
	}

	
	// SECTION: UTILITY - PRIVATE

	private void writeStartElement(String token)
	throws IOException
	{
		writer.append('<');
		writer.append(escaper.escapeJsonString(token));
		writer.append('>');
	}

	private void writeEndElement(String token)
	throws IOException
	{
		writer.append("</");
		writer.append(escaper.escapeJsonString(token));
		writer.append('>');
	}

	/**
     * @param object
     * @return
     */
    private boolean isUnnamedObject(JsonObject object)
    {
    	return (tokens.isEmpty() && object.entrySet().size() > 1);
    }
    
    private boolean isUnnamedArray(JsonArray array)
    {
    	return (tokens.isEmpty());
    }
}

final class TokenStack
{
	private Stack<Stack<String>> tokens = new Stack<Stack<String>>();
	private Stack<String> currentScope = new Stack<String>();
	
	TokenStack()
	{
		tokens.add(currentScope);
	}

	public void increaseScope()
	{
		currentScope = new Stack<String>();
		tokens.push(currentScope);
	}
	
	public boolean reduceScope()
	{
		if (!tokens.isEmpty())
		{
			currentScope = tokens.pop();
		}
		
		return isScopeIncreased();
	}
	
	public void push(String token)
	{
		currentScope.push(token);
	}
	
	public String peek()
	{
		return currentScope.peek();
	}
	
	public String pop()
	{
		return currentScope.pop();
	}
	
	public boolean isEmpty()
	{
		return currentScope.isEmpty();
	}
	
	public boolean isScopeIncreased()
	{
		return (!tokens.isEmpty());
	}
}
