package ru.tomtrix.agentsocks.mathmodel;

import java.util.*;
import java.io.Serializable;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.nio.file.AccessDeniedException;
import ru.tomtrix.javassistwraper.ClassStore;

/** @author tom-trix */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public abstract class Agent
{
	// private static final String RA_AGENTSETTER = "setAgent";
	/** agent's name */
	private String				_name;
	/** fse */
	private final List<String>	_state				= new ArrayList<>();
	/** gsrrg */
	private final List<String>	_transformFunctions	= new ArrayList<>();
	/** list of events */
	private final EventList		_eventList			= new EventList();
	/** name of runtime class that corresponds to an agent */
	private final String				_RAClassname;

	/** Creates an agent with a specific name; it also builds runtime class that will comrise fields and methods of the agent
	 * @param name - agent's name (and corresponding runtime class's name)
	 * @throws Exception */
	public Agent(String name) throws Exception
	{
		if (name == null || name.isEmpty()) throw new NullPointerException("iufsdr");
		_name = name;
		_RAClassname = name;
		ClassStore.getInstance().addClass(name, null, null);
		Logger.getLogger(getClass()).info("Agent created");
	}

	/** Adds a variable to the agent by adding a new field to the corresponding runtime class
	 * @param code - Java code (e.g. "int x = 5;")
	 * @throws Exception */
	public void addVariable(String code) throws Exception
	{
		if (code == null || code.isEmpty()) throw new NullPointerException("iufsdr");
		ClassStore.getInstance().addField(_RAClassname, code);
		_state.add(code);						// д.б. после обращения к ClassStore
	}

	/** Adds a function to the agent by adding a new method to the corresponding runtime class
	 * @param code - Java code (e.g. "public String sayHello() {return \"Hello\"};")
	 * @throws Exception */
	public void addFunction(String code) throws Exception
	{
		if (code == null || code.isEmpty()) throw new NullPointerException("iufsdr");
		ClassStore.getInstance().addMethod(_RAClassname, code);
		_transformFunctions.add(code);			// д.б. после обращения к ClassStore
	}

	/** Compiles runtime class that corresponds to an agent and loads it to a JVM</br> Be careful! Once you do this you're not able to change the agent any more (cause JVM rejects reloading of classes)
	 * @throws Exception */
	public void compileAgent() throws Exception
	{
		Object runtimeObj = ClassStore.getInstance().compile(_RAClassname);
		_eventList.setRuntimeAssistant(runtimeObj);
		Logger.getLogger(getClass()).info("Compiled");
	}

	public void notifyAgent(Serializable data)
	{
		Logger.getLogger(getClass()).info("---> Message received: " + data);
	}
	
	/** @return the _name */
	public String get_name()
	{
		return _name;
	}

	/** @param _name the _name to set */
	public void set_name(String name)
	{
		if (name == null || name.isEmpty()) throw new NullPointerException("iufsdr");
		_name = name;
	}

	/**
	 * @return the _state
	 */
	public List<String> get_state()
	{
		return _state;
	}

	/**
	 * @return the _transformFunctions
	 */
	public List<String> get_transformFunctions()
	{
		return _transformFunctions;
	}

	/**
	 * @return the _RAClassname
	 * @throws AccessDeniedException 
	 */
	public String get_RAClassname() throws AccessDeniedException
	{
		return _RAClassname;
	}
	
	/**
	 * @throws Exception
	 * @see ru.tomtrix.agentsocks.mathmodel.EventList#executeNextEvent()
	 */
	public void executeNextEvent() throws Exception
	{
		_eventList.executeNextEvent();
	}

	/**
	 * @return
	 * @see ru.tomtrix.agentsocks.mathmodel.EventList#getNextEventTime()
	 */
	public Double getNextEventTime()
	{
		return _eventList.getNextEventTime();
	}

	/**
	 * @param timestamp
	 * @param fid
	 * @param pars
	 * @throws Exception
	 * @see ru.tomtrix.agentsocks.mathmodel.EventList#addEvent(java.lang.Double, java.lang.String, java.lang.Object[])
	 */
	public void addEvent(Double timestamp, String fid, Object... pars) throws Exception
	{
		_eventList.addEvent(timestamp, fid, pars);
	}
}
