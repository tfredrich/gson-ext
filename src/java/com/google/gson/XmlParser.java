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

import java.io.EOFException;
import java.io.Reader;
import java.io.StringReader;

/**
 * @author toddf
 * @since Jun 21, 2010
 */
public class XmlParser
{
	public JsonElement parse(String xml) throws XmlParseException
	{
		return parse(new StringReader(xml));
	}

	public JsonElement parse(Reader xml) throws XmlParseException
	{
		try
		{
			JsonParserJavacc parser = new JsonParserJavacc(xml);
			JsonElement element = parser.parse();
			return element;
		}
		catch (TokenMgrError e)
		{
			throw new XmlParseException("Failed parsing XML source: " + xml + " to Json", e);
		}
		catch (ParseException e)
		{
			throw new XmlParseException("Failed parsing XML source: " + xml + " to Json", e);
		}
		catch (StackOverflowError e)
		{
			throw new XmlParseException("Failed parsing XML source: " + xml + " to Json", e);
		}
		catch (OutOfMemoryError e)
		{
			throw new XmlParseException("Failed parsing XML source: " + xml + " to Json", e);
		}
		catch (XmlParseException e)
		{
			if (e.getCause() instanceof EOFException)
			{
				return JsonNull.createJsonNull();
			}
			else
			{
				throw e;
			}
		}
	}
}
