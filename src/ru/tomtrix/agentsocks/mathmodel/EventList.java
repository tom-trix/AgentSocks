/**
 * 
 */
package ru.tomtrix.agentsocks.mathmodel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** @author tom-trix */
public class EventList
{
	private final Map<Double, String>	_eventList	= new ConcurrentHashMap<>();
	private final TransformFunctions	_transformFunctions;

	public EventList(TransformFunctions functions)
	{
		if (functions == null) throw new IllegalArgumentException("Transform functions can't be NULL");
		_transformFunctions = functions;
	}

	public void addEvent(Double timestamp, String f) throws Exception
	{
		_eventList.put(timestamp, f);
		_transformFunctions.invokeMethod(f);
	}
}
