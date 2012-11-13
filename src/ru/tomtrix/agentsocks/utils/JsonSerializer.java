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

	/** fshuiefui
	 * @return dhth */
	public static ObjectMapper getMapper()
	{
		if (!_configured)
		{
			//let mapper handle private fields
			_mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(Visibility.ANY));
			//let mapper ignore classes that have no fields
			_mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			//let mapper ignore unknown properties
			_mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			//all done (there's no need to configure afterwards)
			_configured = true;
		}
		return _mapper;
	}

	/** fsefesg */
	private JsonSerializer()
	{}
}
