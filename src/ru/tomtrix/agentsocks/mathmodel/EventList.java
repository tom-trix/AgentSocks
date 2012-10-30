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
	private Object	_funcKeeper;

	/** adds new function to the event list
	 * @param timestamp - time the function to be invoked
	 * @param fid - function id
	 * @param pars - parameters for functions
	 * @throws Exception - if something goes wrong */
	public void addEvent(Double timestamp, String fid, Object... pars) throws Exception
	{
		_eventList.put(timestamp, new Function(fid, pars));
	}
	
	public void executeNextEvent() throws Exception
	{
		if (_eventList.isEmpty()) return;
		Double d = _eventList.keySet().iterator().next();
		_eventList.get(d).execute(_funcKeeper);
		_eventList.remove(d);
	}
	
	public void setFunctionKeeper(Object functionKeeper)
	{
		if (functionKeeper == null) throw new NullPointerException("fsf");
		_funcKeeper = functionKeeper;
	}
	
	/** @return timestamp of the nearest event in the event list (or <b>NULL</b> if there are no events presented) */
	public Double getNextEventTime()
	{
		if (_eventList.isEmpty()) return null;
		return _eventList.keySet().iterator().next();
	}
	
	public Set<Double> getAllTimestamps()
	{
		return _eventList.keySet();
	}
}
