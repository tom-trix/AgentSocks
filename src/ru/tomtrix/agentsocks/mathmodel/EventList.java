package ru.tomtrix.agentsocks.mathmodel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/** @author tom-trix */
public class EventList
{
	/** map containing functions & their timestamps */
	private final Map<Double, Function>	_eventList	= new ConcurrentHashMap<>();
	/** reference to transform functions */
	private final TransformFunctions	_transformFunctions;

	/** creates an event list
	 * @param functions - a set of functions to be chosen from */
	public EventList(TransformFunctions functions)
	{
		if (functions == null) throw new IllegalArgumentException("Transform functions can't be NULL");
		_transformFunctions = functions;
	}

	/** adds new function to the event list
	 * @param timestamp - time the function to be invoked
	 * @param fid - function id
	 * @param pars - parameters for functions
	 * @throws Exception - if something goes wrong */
	public void addEvent(Double timestamp, String fid, Object... pars) throws Exception
	{
		if (!_transformFunctions.parametersCorrespondToClasses(fid, pars)) throw new IllegalArgumentException(String.format("Parameters \"pars\" don't correspond for invoking \"%s\"", fid));
		_eventList.put(timestamp, new Function(fid, pars));
	}

	/** @return timestamp of the nearest event in the event list (or <b>NULL</b> if there are no events presented) */
	public Double getNextEventTime()
	{
		if (_eventList.isEmpty()) return null;
		return _eventList.keySet().iterator().next();
	}
	
	public boolean executeNextEvent() throws Exception
	{
		if (_eventList.isEmpty()) return false;
		Double d = _eventList.keySet().iterator().next();
		_transformFunctions.invokeMethod(_eventList.get(d));
		_eventList.remove(d);
		return true;
	}
	
	public Set<Double> getAllTimestamps()
	{
		return _eventList.keySet();
	}
}
