package ru.tomtrix.agentsocks.mathmodel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/** fvsdfvsejop
 * @author tom-trix */
public class EventList
{
	/** map containing functions & their timestamps */
	private final Map<Double, Function>	_eventList	= new ConcurrentHashMap<>();
	/** reference to transform functions */
	private Object						_RAref;

	/** adds new function to the event list
	 * @param timestamp - time the function to be invoked
	 * @param fid - function id
	 * @param pars - parameters for functions
	 * @throws Exception - if something goes wrong */
	void addEvent(double timestamp, String fid, Object... pars) throws Exception
	{
		if (timestamp < 0) throw new IllegalArgumentException("Timestamp can't be negative");
		if (fid == null || fid.trim().isEmpty()) throw new IllegalArgumentException("Function id can't be empty");
		_eventList.put(timestamp, new Function(fid, pars));
	}

	/** @throws Exception */
	void executeNextEvent() throws Exception
	{
		if (_RAref == null) throw new NullPointerException("Runtime Assistant is not assigned. Perhaps the agent hasn't been compiled");
		Function f;
		//this must be synchronized cause someone could remove the item while the other finds the minimum
		synchronized (this)
		{
			if (_eventList==null) return;
			Double d = Collections.min(_eventList.keySet()); 	//TODO сортировка коллекции является bottleneck'ом
			f = _eventList.get(d);
			_eventList.remove(d);
		}
		f.execute(_RAref);
	}

	/** @param runtimeAssistant */
	void setRuntimeAssistant(Object runtimeAssistant)
	{
		if (runtimeAssistant == null) throw new NullPointerException("Runtime Assistant can't be equal to null");
		_RAref = runtimeAssistant;
	}

	/** @return timestamp of the nearest event in the event list (or <b>NULL</b> if there are no events presented) */
	synchronized Double getNextEventTime()
	{
		//this must be synchronized cause someone could remove the item while the other finds the minimum
		if (_eventList.isEmpty()) return null;
		return Collections.min(_eventList.keySet()); 			//TODO сортировка коллекции является bottleneck'ом
	}
}
