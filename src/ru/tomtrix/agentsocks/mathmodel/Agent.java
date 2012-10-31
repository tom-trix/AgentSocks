package ru.tomtrix.agentsocks.mathmodel;

import java.util.List;

import ru.tomtrix.javassistwraper.ClassStore;

/** @author tom-trix */
public abstract class Agent
{
	/** agent's name */
	private final String	_name;
	/** list of events */
	private final EventList	_eventList;
	/** name of runtime class that corresponds to an agent */
	private String			_runtimeClassName;

	/** Creates an agent with a specific name; it also builds runtime class that will comrise fields and methods of the agent
	 * @param name - agent's name (and corresponding runtime class's name)
	 * @throws Exception */
	public Agent(String name) throws Exception
	{
		_name = name;
		_runtimeClassName = name;
		_eventList = new EventList();
		ClassStore.getInstance().addClass(name, null, null);
	}

	/** Adds a variable to the agent by adding a new field to the corresponding runtime class
	 * @param code - Java code (e.g. "int x = 5;")
	 * @throws Exception */
	public void addVariable(String code) throws Exception
	{
		ClassStore.getInstance().addField(_runtimeClassName, code);
	}

	/** Adds a function to the agent by adding a new method to the corresponding runtime class
	 * @param code - Java code (e.g. "public String sayHello() {return \"Hello\"};")
	 * @throws Exception */
	public void addFunction(String code) throws Exception
	{
		ClassStore.getInstance().addMethod(_runtimeClassName, code);
	}

	/** Compiles runtime class that corresponds to an agent and loads it to a JVM</br> Be careful! Once you do this you're not able to change the agent any more (cause JVM rejects reloading of classes)
	 * @throws Exception */
	public void compileAgent() throws Exception
	{
		_eventList.setFunctionKeeper(ClassStore.getInstance().compile(_runtimeClassName));
	}
	
	/**
	 * @return
	 */
	public List<String> getVariables()
	{
		return ClassStore.getInstance().getFields(_runtimeClassName);
	}
	
	/**
	 * @return
	 */
	public List<String> getFunctions()
	{
		return ClassStore.getInstance().getMethods(_runtimeClassName);
	}

	/** @return the _name */
	public String get_name()
	{
		return _name;
	}

	/** @return the _eventList */
	public EventList get_eventList()
	{
		return _eventList;
	}

	/** @return the _className */
	public String get_runtimeClassName()
	{
		return _runtimeClassName;
	}
}
