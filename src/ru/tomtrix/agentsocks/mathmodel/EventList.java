/**
 * 
 */
package ru.tomtrix.agentsocks.mathmodel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** @author tom-trix */
public class EventList
{
	private final Map<Double, Function>	_eventList	= new ConcurrentHashMap<>();
	private final TransformFunctions	_transformFunctions;

	public EventList(TransformFunctions functions)
	{
		if (functions == null) throw new IllegalArgumentException("Transform functions can't be NULL");
		_transformFunctions = functions;
	}

	public void addEvent(Double timestamp, String fid, Object ... pars) throws Exception
	{
		//if (!_transformFunctions.parametersCorrespondToClasses(fid, pars)) throw new IllegalArgumentException("fuck");
		_eventList.put(timestamp, new Function(fid, pars));
		//temp
		_transformFunctions.invokeMethod(new Function(fid, pars));
	}
}
