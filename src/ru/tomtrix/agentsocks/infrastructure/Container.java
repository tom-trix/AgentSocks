package ru.tomtrix.agentsocks.infrastructure;

import java.util.*;
import org.apache.log4j.Logger;
import ru.tomtrix.agentsocks.Constants;
import java.util.concurrent.ConcurrentLinkedDeque;
import com.fasterxml.jackson.annotation.JsonProperty;

/** @author tom-trix */
public class Container implements IAgentProcessible
{
	/** fseef */
	private final Queue<LogicProcess>	_processes	= new ConcurrentLinkedDeque<>();
	/** wwd */
	private final int					_node;
	/** fae */
	private final int					_threads;
	/** fes */
	private boolean						_alive		= true;

	/** @param threads
	 * @param name */
	Container(@JsonProperty("_threads") int threads, @JsonProperty("_node") int node)
	{
		if (threads < 0 || threads > Constants.MAX_THREADS) throw new IllegalArgumentException(String.format("Container can't contain %d threads", threads));
		_threads = threads;
		_node = node;
	}

	/** fsef */
	void run()
	{
		for (int i = 0; i < _threads; i++)
		{
			final String threadName = String.format("node%d.thread%d", _node, i);
			// create a thread that picks a logic processes from the queue and calls their "next" method
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
							Thread.sleep(40);
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

	/** dae
	 * @param name */
	public void addLogicProcess(String name)
	{
		if (name == null || name.trim().isEmpty()) throw new NullPointerException("Process must have a correct name");
		_processes.add(new LogicProcess(name));
	}

	/** fasef
	 * @param process */
	public void addLogicProcess(LogicProcess process)
	{
		if (process == null) throw new NullPointerException("Logic process can't be null");
		_processes.add(process);
	}

	/** vgfsr
	 * @param name */
	public void removeProcess(String name)
	{
		LogicProcess p = getProcessByName(name);
		if (p == null) throw new NullPointerException("There is no such a process");
		_processes.remove(p);
	}

	/** gugu
	 * @param name
	 * @return */
	public LogicProcess getProcessByName(String name)
	{
		if (name == null || name.trim().isEmpty()) throw new NullPointerException("Name parameter can't be null");
		for (LogicProcess p : _processes)
			if (p.get_name().equals(name)) return p;
		return null;
	}

	@Override
	public void loadCode() throws Exception
	{
		for (IAgentProcessible process : _processes)
			process.loadCode();
	}

	@Override
	public void compileAgents() throws Exception
	{
		for (IAgentProcessible process : _processes)
			process.compileAgents();
	}
}
