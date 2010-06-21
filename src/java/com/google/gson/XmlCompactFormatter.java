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
import java.util.Set;
import java.util.Stack;
import java.util.Map.Entry;

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

		JsonElementVisitor visitor = new XmlFormattingVisitor(writer, new Escaper(escapeHtmlChars), serializeNulls);
		JsonTreeNavigator navigator = new JsonTreeNavigator(visitor, serializeNulls);
		navigator.navigate(root);
	}
}

final class XmlFormattingVisitor
implements JsonElementVisitor
{
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

	@Override
	public void endArray(JsonArray array)
	throws IOException
	{
		System.out.println("XmlFormattingVisitor.endArray()");
		writeEndElement(tokens.pop());
	}

	@Override
	public void startArray(JsonArray array)
	throws IOException
	{
		System.out.println("XmlFormattingVisitor.startArray()");
		
		if (tokens.isEmpty())
		{
			tokens.push("list");
		}
		
		writeStartElement(tokens.peek());
	}

	@Override
	public void startObject(JsonObject object)
	throws IOException
	{
		System.out.println("XmlFormattingVisitor.startObject()");
		
		if (!tokens.isEmpty())
		{
			writeStartElement(tokens.peek());
		}
		else
		{
			Set<Entry<String, JsonElement>> entries = object.entrySet();
			
			if (entries.size() > 1)
			{
				tokens.push("root");
				writeStartElement(tokens.peek());
			}
		}
	}

	@Override
	public void endObject(JsonObject object)
	throws IOException
	{
		System.out.println("XmlFormattingVisitor.endObject()");
		if (!tokens.isEmpty())
		{
			writeEndElement(tokens.pop());
		}
	}

	@Override
	public void visitArrayMember(JsonArray parent, JsonPrimitive member, boolean isFirst)
	throws IOException
	{
		System.out.println("XmlFormattingVisitor.visitArrayMember(array,primitive,boolean)");
		writeStartElement("item");
		writer.append(member.getAsString());
		writeEndElement("item");
	}

	@Override
	public void visitArrayMember(JsonArray parent, JsonArray member, boolean isFirst)
	throws IOException
	{
		System.out.println("XmlFormattingVisitor.visitArrayMember(array,array,boolean)");
		tokens.push("item");
	}

	@Override
	public void visitArrayMember(JsonArray parent, JsonObject member, boolean isFirst)
	throws IOException
	{
		System.out.println("XmlFormattingVisitor.visitArrayMember(array,object,boolean)");
		tokens.push("item");
	}

	@Override
	public void visitNull()
	throws IOException
	{
		System.out.println("XmlFormattingVisitor.visitNull()");
	}

	@Override
	public void visitNullArrayMember(JsonArray parent, boolean isFirst)
	throws IOException
	{
		System.out.println("XmlFormattingVisitor.visitNullArrayMember()");
	}

	@Override
	public void visitNullObjectMember(JsonObject parent, String memberName, boolean isFirst)
	throws IOException
	{
		System.out.println("XmlFormattingVisitor.visitNullObjectMember()");
	}

	@Override
	public void visitObjectMember(JsonObject parent, String memberName, JsonPrimitive member, boolean isFirst)
	throws IOException
	{
		System.out.println("XmlFormattingVisitor.visitObjectMember(object,string,primitive,boolean)");
		writeStartElement(memberName);
		writer.append(member.getAsString());
		writeEndElement(memberName);
	}

	@Override
	public void visitObjectMember(JsonObject parent, String memberName, JsonArray member, boolean isFirst)
	throws IOException
	{
		System.out.println("XmlFormattingVisitor.visitObjectMember(object,string,array,boolean)");
		tokens.push(memberName);
	}

	@Override
	public void visitObjectMember(JsonObject parent, String memberName, JsonObject member, boolean isFirst)
	throws IOException
	{
		System.out.println("XmlFormattingVisitor.visitObjectMember(object,string,object,boolean)");
		tokens.push(memberName);
	}

	@Override
	public void visitPrimitive(JsonPrimitive primitive)
	throws IOException
	{
		System.out.println("XmlFormattingVisitor.visitPrimitive()");
	}

	
	// SECTION: UTILITY - PRIVATE

	private void writeStartElement(String token)
	throws IOException
	{
		writer.append('<');
		writer.append(token);
		writer.append('>');
	}

	private void writeEndElement(String token)
	throws IOException
	{
		writer.append("</");
		writer.append(token);
		writer.append('>');
	}
}

final class TokenStack
{
	private Stack<String> stack = new Stack<String>();
	
	public void push(String token)
	{
		stack.push(token);
	}
	
	public String pop()
	{
		return stack.pop();
	}
	
	public String peek()
	{
		return stack.peek();
	}
	
	public boolean isEmpty()
	{
		return stack.isEmpty();
	}
}