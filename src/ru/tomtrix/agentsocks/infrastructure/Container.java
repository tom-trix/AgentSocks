package ru.tomtrix.agentsocks.infrastructure;

import java.util.*;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.ConcurrentLinkedDeque;

/** @author tom-trix */
public class Container implements ICodeLoadable
{
	/** wwd */
	private final Queue<LogicProcess>	_processes	= new ConcurrentLinkedDeque<>();
	/** fae */
	private final int					_threads;
	private final int _node;
	/** fes */
	private boolean						_alive		= true;

	/** @param threads
	 * @param name */
	Container(@JsonProperty("_threads") int threads, @JsonProperty("_node") int node)
	{
		if (threads < 0 || threads > 100) throw new IllegalArgumentException("");
		_threads = threads;
		_node = node;
	}

	/** fsef */
	void run()
	{
		for (int i = 0; i < _threads; i++)
		{
			final String threadName = String.format("node%d.thread%d", _node, i);
			// create a thread that picks logic processes from the queue and calls their "next" method
			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					while (_alive)
					{
						LogicProcess process = null;
						try
						{
							Thread.sleep(50);
							process = _processes.poll();
							if (process == null) continue;
							process.nextStep();
							_processes.add(process);
						}
						catch (InterruptedException e) 		// done specially to avoid NullPointerException!
						{}
						catch (Exception e)
						{
							Logger.getLogger(getClass()).error(String.format("Error in a logical process \"%s\" (thread \"%s\"):", process.get_name(), threadName), e);
						}
					}
				}
			}, threadName).start();
		}
	}

	/** fsef */
	void stop()
	{
		_alive = false;
	}

	public void addLogicProcess(String name)
	{
		_processes.add(new LogicProcess(name));
	}

	public void addLogicProcess(LogicProcess process)
	{
		_processes.add(process);
	}

	public void removeProcess(String name)
	{
		_processes.remove(getProcessByName(name));
	}

	public LogicProcess getProcessByName(String name)
	{
		for (LogicProcess p : _processes)
			if (p.get_name().equals(name)) return p;
		return null;
	}

	@Override
	public void loadCode() throws Exception
	{
		for (ICodeLoadable process : _processes)
			process.loadCode();
	}

	@Override
	public void compileAgents() throws Exception
	{
		for (ICodeLoadable process : _processes)
			process.compileAgents();
	}

	/** @return the _processes
	 * @throws AccessDeniedException *//*
	public Queue<LogicProcess> get_processes() throws AccessDeniedException
	{
		if (Control.CONSTRUCTOR_ACCESS_DENIED) throw new AccessDeniedException("frfrfr");
		return _processes;
	}*/
}
