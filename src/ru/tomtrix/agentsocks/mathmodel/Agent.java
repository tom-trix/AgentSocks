package ru.tomtrix.agentsocks.mathmodel;

import ru.tomtrix.agentsocks.mathmodel.State;

/** @author tom-trix */
public abstract class Agent
{
	protected final String				_name;
	/** state of an agent */
	protected final State				_state	= new State();
	/** set of functions that transform the state of an agent */
	protected final TransformFunctions	_transformFunctions;
	/** list of events that harness the transformfunctions */
	protected final EventList			_eventList;

	public Agent(String name)
	{
		_name = name;
		_transformFunctions = new TransformFunctions(this);		//be careful! This object must be created AFTER the _name is assigned
		_eventList = new EventList(_transformFunctions);
	}

	/** @return the _state */
	public State get_state()
	{
		return _state;
	}

}
