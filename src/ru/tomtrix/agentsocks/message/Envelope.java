package ru.tomtrix.agentsocks.message;

import java.io.Serializable;

/** @author tom-trix */
public class Envelope implements Serializable
{
	private static final long	serialVersionUID	= 1059235822217521090L;
	private final IMessage		_data;
	private final String		_process;
	private final String		_agent;
	private final String		_sender;

	/** @param data
	 * @param process
	 * @param agent */
	public Envelope(IMessage data, String process, String agent, String sender)
	{
		_process = process;
		_agent = agent;
		_data = data;
		_sender = sender;
	}

	/** @return the _process */
	public String get_process()
	{
		return _process;
	}

	/** @return the _agent */
	public String get_agent()
	{
		return _agent;
	}

	/** @return the _data */
	public IMessage get_data()
	{
		return _data;
	}

	/**
	 * @return the _sender
	 */
	public String get_sender()
	{
		return _sender;
	}
}
