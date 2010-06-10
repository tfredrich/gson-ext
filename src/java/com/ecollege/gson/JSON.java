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
package com.ecollege.gson;

import com.ecollege.gson.xml.Xml;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;


/**
 * An uninstantiable class offering foreign convenience methods to convert between JSON and XML strings.
 * 
 * @author toddf
 * @since Jun 7, 2010
 */
public final class Json
{	
	// SECTION: CONSTRUCTOR - PRIVATE
	
	private Json()
	{
		// simply to prevent instantiation.
	}

	
	// SECTION: CONVENIENCE METHODS

	public static JsonElement parseJson(String jsonString)
	throws JsonParseException
	{
		if (jsonString == null || jsonString.trim().length() == 0)
		{
			return null;
		}

		String json = jsonString.trim();
		return new JsonParser().parse(json);
	}
	
	public static String toJson(JsonElement jsonElement)
	{
		return new Gson().toJson(jsonElement);
	}
	
	public static String toJson(String xml)
	throws XmlParseException
	{
		return toJson(parseXml(xml));
	}
	
	public static JsonElement parseXml(String xml)
	throws XmlParseException
	{
		return new Xml().parse(xml);
	}
	
	public static String toXml(String json)
	throws JsonParseException
	{
		return toXml(parseJson(json));
	}

	public static String toXml(JsonElement jsonElement)
	{
		// TODO write JsonElement to XML
		return new Xml().toXml(jsonElement);
	}
}
