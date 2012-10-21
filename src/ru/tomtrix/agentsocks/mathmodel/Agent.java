/**
 * 
 */
package ru.tomtrix.agentsocks.mathmodel;

/** @author tom-trix */
public abstract class Agent
{
	protected final State _state = new State();
	protected final TransformFunctions _transformFunctions = new TransformFunctions();
	protected final EventList _eventList = new EventList(_transformFunctions);
	
	/**
	 * @return the _state
	 */
	public State get_state()
	{
		return _state;
	}
}
