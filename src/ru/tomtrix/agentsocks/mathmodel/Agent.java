package ru.tomtrix.agentsocks.mathmodel;

/** @author tom-trix */
public abstract class Agent
{
	/** state of an agent */
	protected final State				_state				= new State();
	/** set of functions that transform the state of an agent */
	protected final TransformFunctions	_transformFunctions	= new TransformFunctions();
	/** list of events that harness the transformfunctions */
	protected final EventList			_eventList			= new EventList(_transformFunctions);

	/** @return the state */
	public State get_state()
	{
		return _state;
	}
}
