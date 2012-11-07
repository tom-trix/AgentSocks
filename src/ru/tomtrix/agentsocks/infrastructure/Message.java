/**
 * 
 */
package ru.tomtrix.agentsocks.infrastructure;

import java.io.Serializable;

/** @author tom-trix */
public class Message implements Serializable
{
	private static final long	serialVersionUID	= 1059235822217521090L;
	private String			_process;
	private int				_agent;
	private Serializable	_data;

	/** @param data 
	 * @param process
	 * @param agent*/
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

	/** @param _process the _process to set */
	public void set_process(String _process)
	{
		this._process = _process;
	}

	/** @return the _agent */
	public int get_agent()
	{
		return _agent;
	}

	/** @param _agent the _agent to set */
	public void set_agent(int _agent)
	{
		this._agent = _agent;
	}

	/** @return the _data */
	public Serializable get_data()
	{
		return _data;
	}

	/** @param _data the _data to set */
	public void set_data(Serializable _data)
	{
		this._data = _data;
	}
}
