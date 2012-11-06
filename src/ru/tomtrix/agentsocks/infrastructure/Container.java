package ru.tomtrix.agentsocks.infrastructure;

import java.util.*;

import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentLinkedDeque;

/** @author tom-trix */
public class Container
{
	/** wwd */
	private final Queue<LogicProcess>	_processes	= new ConcurrentLinkedDeque<>();
	/** fae */
	private final List<Thread>			_threads	= new LinkedList<>();
	/** fes */
	private boolean						_alive		= true;

	/** @param threads
	 * @param name */
	Container(int threads, String name)
	{
		if (threads < 0 || threads > 100) throw new IllegalArgumentException("");
		for (int i = 0; i < threads; i++)
		{
			final String threadName = String.format("%s.thread%d", name, i);
			// create a thread that picks logic processes from the queue and calls their "next" method
			_threads.add(new Thread(new Runnable()
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
			}, threadName));
		}
	}

	/** fsef */
	void run()
	{
		for (Thread th : _threads)
			th.start();
	}

	/** fsef */
	void stop()
	{
		_alive = false;
	}

	/** @param process */
	public LogicProcess addLogicProcess(String name)
	{
		LogicProcess result = new LogicProcess(name);
		_processes.add(result);
		return result;
	}

	public LogicProcess getProcessByName(String name)
	{
		for (LogicProcess p : _processes)
			if (p.get_name().equals(name)) return p;
		return null;
	}
}
