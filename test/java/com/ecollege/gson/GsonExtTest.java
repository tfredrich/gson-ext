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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gson.XmlParseException;

/**
 * @author toddf
 * @since Jun 7, 2010
 */
public class GsonExtTest
{
	private static final boolean SHOULD_OUTPUT = false;

	@Test
	public void shouldConvertJsonNamedArrayToXmlElements()
	throws Exception
	{
		String json = "{\"notifications\":[{\"id\":1005,\"subject\":\"foo\"},{\"id\":1006,\"subject\":\"bar\"}]}";
		String xml = GsonExt.toXml(json);

		if (SHOULD_OUTPUT)
		{
			System.out.println(json);
			System.out.println(xml);
		}

		assertEquals("<notifications><item><id>1005</id><subject>foo</subject></item><item><id>1006</id><subject>bar</subject></item></notifications>", xml);
	}

	@Test
	public void shouldConvertJsonUnnamedArrayToXmlElements()
	throws Exception
	{
		String json = "[{\"id\":1005,\"subject\":\"foo\"},{\"id\":1006,\"subject\":\"bar\"}]";
		String xml = GsonExt.toXml(json);

		if (SHOULD_OUTPUT)
		{
			System.out.println(json);
			System.out.println(xml);
		}

		assertEquals("<list><item><id>1005</id><subject>foo</subject></item><item><id>1006</id><subject>bar</subject></item></list>", xml);
	}

	@Test
	public void shouldConvertJsonNamedObjectToXmlElements()
	throws Exception
	{
		String json = "{ \"resource\" : { \"name\" : \"test post\", \"data\" : \"some data\" } }";
		String xml = GsonExt.toXml(json);

		if (SHOULD_OUTPUT)
		{
			System.out.println(json);
			System.out.println(xml);
		}

		assertEquals("<resource><name>test post</name><data>some data</data></resource>", xml);
	}

	@Test
	public void shouldConvertJsonUnnamedObjectToXmlElements()
	throws Exception
	{
		String json = "{\"access_token\":\"mauth|79889m9rwet|2114798|2010-06-07T09%3a51%3a03|66cb32d9e0cf9ea2dad1f999946af951\",\"expires\":3600}";
		String xml = GsonExt.toXml(json);

		if (SHOULD_OUTPUT)
		{
			System.out.println(json);
			System.out.println(xml);
		}

		assertEquals("<root><access_token>mauth|79889m9rwet|2114798|2010-06-07T09%3a51%3a03|66cb32d9e0cf9ea2dad1f999946af951</access_token><expires>3600</expires></root>", xml);
	}

	@Test
	public void shouldConvertJsonNamedValueToXmlElements()
	throws Exception
	{
		String json = "{\"access_token\":{\"name\":\"value\"}}";
		String xml = GsonExt.toXml(json);

		if (SHOULD_OUTPUT)
		{
			System.out.println(json);
			System.out.println(xml);
		}

		assertEquals("<access_token><name>value</name></access_token>", xml);
	}

	@Test
	public void shouldConvertJsonValueToXmlElements()
	throws Exception
	{
		String json = "{\"access_token\":\"value\"}";
		String xml = GsonExt.toXml(json);

		if (SHOULD_OUTPUT)
		{
			System.out.println(json);
			System.out.println(xml);
		}

		assertEquals("<access_token>value</access_token>", xml);
	}

	@Test
	public void shouldConvertHeinousJsonUnnamedListToXmlElements()
	throws Exception
	{
		String json = "[{\"named_list\":[{\"a\":\"a_value\"}, {\"b\":\"b_value\"}, {\"c\":\"c_value\"}]},{\"named_root\":{\"foo\":\"bar\"}},{\"a\":\"b\",\"c\":\"d\"},[{\"humpty\":1}, {\"dumpty\":2}]]";
		String xml = GsonExt.toXml(json);

		if (SHOULD_OUTPUT)
		{
			System.out.println(json);
			System.out.println(xml);
		}

		assertEquals("<list><item><named_list><item><a>a_value</a></item><item><b>b_value</b></item><item><c>c_value</c></item></named_list></item><item><named_root><foo>bar</foo></named_root></item><item><a>b</a><c>d</c></item><item><list><item><humpty>1</humpty></item><item><dumpty>2</dumpty></item></list></item></list>", xml);
	}

	@Test
	public void shouldConvertNestedUnnamedArraysToXml()
	throws Exception
	{
		String json = "[[{\"a\":\"a_value\"}, {\"b\":\"b_value\"}, {\"c\":\"c_value\"}, [{\"humpty\":1}, {\"dumpty\":2}]], [{\"humpty\":3}, {\"dumpty\":4}]]";
		String xml = GsonExt.toXml(json);

		if (SHOULD_OUTPUT)
		{
			System.out.println(json);
			System.out.println(xml);
		}

		assertEquals("<list><item><list><item><a>a_value</a></item><item><b>b_value</b></item><item><c>c_value</c></item><item><list><item><humpty>1</humpty></item><item><dumpty>2</dumpty></item></list></item></list></item><item><list><item><humpty>3</humpty></item><item><dumpty>4</dumpty></item></list></item></list>", xml);
	}

	@Test
	public void shouldConvertUnnamedArrayNestedInNamedArray()
	{
		String json = "[{\"named_list\":[{\"a\":\"a_value\"}, [{\"humpty\":1}, {\"dumpty\":2}], {\"c\":\"c_value\"}]},{\"named_root\":{\"foo\":\"bar\"}},{\"a\":\"b\",\"c\":\"d\"}]";
		String xml = GsonExt.toXml(json);

		if (SHOULD_OUTPUT)
		{
			System.out.println(json);
			System.out.println(xml);
		}

		assertEquals("<list><item><named_list><item><a>a_value</a></item><item><list><item><humpty>1</humpty></item><item><dumpty>2</dumpty></item></list></item><item><c>c_value</c></item></named_list></item><item><named_root><foo>bar</foo></named_root></item><item><a>b</a><c>d</c></item></list>", xml);
	}

	@Test
	public void shouldConvertNamedArrayNestedInNamedArray()
	{
		String json = "[{\"named_list\":[{\"a\":\"a_value\"}, {\"nested_list\":[{\"humpty\":1}, {\"dumpty\":2}]}, {\"c\":\"c_value\"}]},{\"named_root\":{\"foo\":\"bar\"}},{\"a\":\"b\",\"c\":\"d\"}]";
		String xml = GsonExt.toXml(json);

		if (SHOULD_OUTPUT)
		{
			System.out.println(json);
			System.out.println(xml);
		}

		assertEquals("<list><item><named_list><item><a>a_value</a></item><item><nested_list><item><humpty>1</humpty></item><item><dumpty>2</dumpty></item></nested_list></item><item><c>c_value</c></item></named_list></item><item><named_root><foo>bar</foo></named_root></item><item><a>b</a><c>d</c></item></list>", xml);
	}
	
	@Test (expected=XmlParseException.class)
	public void shouldThrowExceptionOnParseXml()
	{
		GsonExt.parseXml("<xml>value</xml>");
	}
	
	@Test (expected=XmlParseException.class)
	public void shouldThrowExceptionOnToJson()
	{
		String xml = "<xml>value</xml>";
		String json = GsonExt.toJson(xml);

		if (SHOULD_OUTPUT)
		{
			System.out.println(xml);
			System.out.println(json);
		}

//		assertEquals("{\"xml\":\"value\"}", json);
	}
}
