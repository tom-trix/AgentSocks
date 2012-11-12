package ru.tomtrix.agentsocks.utils;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

/** fcsea
 * @author tom-trix */
public class JsonSerializer
{
	/** sfef */
	private static final ObjectMapper	_mapper		= new ObjectMapper();
	/** fsefesfp */
	private static boolean				_configured	= false;

	/** @return dhth */
	public static ObjectMapper getMapper()
	{
		if (!_configured)
		{
			_mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(Visibility.ANY));
			_mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			_mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			_configured = true;
		}
		return _mapper;
	}

	/** fsefesg */
	private JsonSerializer()
	{}
}
