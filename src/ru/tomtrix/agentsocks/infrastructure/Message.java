/**
 * 
 */
package ru.tomtrix.agentsocks.infrastructure;

import java.io.Serializable;

/** @author tom-trix */
public class Message implements Serializable
{
	private static final long	serialVersionUID	= 1059235822217521090L;
	private final String		_process;
	private final int			_agent;
	private final Serializable	_data;

	/** @param data
	 * @param process
	 * @param agent */
	public Message(Serializable data, String process, int agent)
	{
		_process = process;
		_agent = agent;
		_data = data;
	}

	/** @return the _process */
	public String get_process()
	{
		return _process;
	}

	/** @return the _agent */
	public int get_agent()
	{
		return _agent;
	}

	/** @return the _data */
	public Serializable get_data()
	{
		return _data;
	}
}
