package ru.tomtrix.agentsocks.mathmodel;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

/** fvsdfvsejop
 * @author tom-trix */
public class EventList
{
	/** map containing functions & their timestamps */
	private final Map<Double, Function>	_eventList	= new ConcurrentHashMap<>(); //TODO
	/** reference to transform functions */
	private Object	_runtimeAssistant;

	/** adds new function to the event list
	 * @param timestamp - time the function to be invoked
	 * @param fid - function id
	 * @param pars - parameters for functions
	 * @throws Exception - if something goes wrong */
	public void addEvent(Double timestamp, String fid, Object... pars) throws Exception
	{
		_eventList.put(timestamp, new Function(fid, pars));
	}
	
	/**
	 * @throws Exception
	 */
	public void executeNextEvent() throws Exception
	{
		if (_runtimeAssistant == null) throw new NullPointerException("fsfe");
		if (_eventList.isEmpty()) return;
		Double d = _eventList.keySet().iterator().next(); //TODO ТУТ М.Б. ПРОБЛЕМА СОВМ. ДОСТУПА!!!
		_eventList.get(d).execute(_runtimeAssistant);
		_eventList.remove(d);
	}
	
	/**
	 * @param runtimeAssistant
	 */
	public void setRuntimeAssistant(Object runtimeAssistant)
	{
		if (runtimeAssistant == null) throw new NullPointerException("fsf");
		_runtimeAssistant = runtimeAssistant;
	}
	
	/** @return timestamp of the nearest event in the event list (or <b>NULL</b> if there are no events presented) */
	@JsonIgnore(value = true)
	public Double getNextEventTime()
	{
		if (_eventList.isEmpty()) return null;
		return _eventList.keySet().iterator().next();
	}
	
	/**
	 * @return
	 */
	@JsonIgnore(value = true)
	public Map<Double, String> getInfo()
	{
		Map<Double, String> result = new HashMap<>();
		for (Entry<Double, Function> e : _eventList.entrySet())
		{
			result.put(e.getKey(), e.getValue().get_fid());
		}
		return result;
	}
}
