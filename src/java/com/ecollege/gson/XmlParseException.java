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

/**
 * @author toddf
 * @since Jun 10, 2010
 */
public class XmlParseException
extends RuntimeException
{
    private static final long serialVersionUID = -8573588391005811183L;

	public XmlParseException()
    {
	    super();
    }

	public XmlParseException(String message, Throwable cause)
    {
	    super(message, cause);
    }

	public XmlParseException(String message)
    {
	    super(message);
    }

	public XmlParseException(Throwable cause)
    {
	    super(cause);
    }
}
