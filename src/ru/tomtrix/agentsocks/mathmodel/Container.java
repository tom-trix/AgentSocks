package ru.tomtrix.agentsocks.mathmodel;

import java.util.*;
import org.apache.log4j.Logger;
import java.util.concurrent.ConcurrentLinkedDeque;

/** @author tom-trix */
public class Container
{
	/**
	 * 
	 */
	private final Deque<LogicProcess>	_processes	= new ConcurrentLinkedDeque<>();
	/**
	 * 
	 */
	private final List<Thread> _threads = new LinkedList<>();
	/**
	 * 
	 */
	private boolean						_alive		= true;

	/**
	 * @param threads
	 * @param name
	 */
	public Container(int threads, String name)
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
							process = _processes.pollFirst();
							if (process == null) continue;
							process.nextStep();
							_processes.add(process);
							Logger.getLogger(getClass()).debug("next() done");
						}
						catch (InterruptedException e) 		//done specially to avoid NullPointerException!
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

	/**
	 * @param process
	 */
	public void addLogicProcess(LogicProcess process)
	{
		_processes.add(process);
	}
	
	/**
	 * 
	 */
	public void run()
	{
		for (Thread th : _threads)
			th.start();
	}
	
	/**
	 * 
	 */
	public void stop()
	{
		_alive = false;
	}
}
