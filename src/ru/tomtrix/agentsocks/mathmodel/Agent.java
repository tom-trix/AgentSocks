package ru.tomtrix.agentsocks.mathmodel;

import ru.tomtrix.javassistwraper.Compiler;

/** @author tom-trix */
public abstract class Agent
{
	private final String				_name;
	public Object				State	= null;
	/** list of events that harness the transformfunctions */
	private final EventList			_eventList;

	public Agent(String name) throws Exception
	{
		_name = name;
		_eventList = new EventList();
		Compiler.getInstance().addClass(name, null, null);
	}
	
	public void addVariable(String code) throws Exception
	{
		if (State != null) throw new IllegalAccessException("frrs");
		Compiler.getInstance().addField(_name, code);
	}
	
	public void addFunction(String code) throws Exception
	{
		if (State != null) throw new IllegalAccessException("frrs");
		Compiler.getInstance().addMethod(_name, code);
	}
	
	public void compileAgent() throws Exception
	{
		_eventList.setFunctionKeeper(Compiler.getInstance().compile(_name));
	}

	/**
	 * @return the _name
	 */
	public String get_name()
	{
		return _name;
	}

	/**
	 * @return the _eventList
	 */
	public EventList get_eventList()
	{
		return _eventList;
	}
}
