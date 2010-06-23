/*
    Copyright 2010, Pearson eCollege.

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
import java.io.StringWriter;

/**
 * @author toddf
 * @since Jun 21, 2010
 */
public class Xml
{
	/**
	 * @param xml
	 * @return
	 */
	public JsonElement parse(String xml)
	throws XmlParseException
	{
		// TODO: optimize to reuse XmlParser instance when assured it's thread safe.
		return new XmlParser().parse(xml);
	}

	/**
	 * @param jsonElement
	 * @return
	 */
	public String toXml(JsonElement jsonElement)
	throws XmlParseException
	{
		try
		{
			StringWriter writer = new StringWriter();
			// TODO: optimize to reuse XmlCompactFormatter instance when assured it's thread safe.
			new XmlCompactFormatter().format(jsonElement, writer, false);
			return writer.toString();
		}
		catch (IOException e)
		{
			throw new XmlParseException(e);
		}
	}
}
