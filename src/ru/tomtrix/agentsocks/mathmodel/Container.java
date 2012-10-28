package ru.tomtrix.agentsocks.mathmodel;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.apache.log4j.Logger;

/** @author tom-trix */
public class Container
{
	private final Deque<LogicProcess>	_processes	= new ConcurrentLinkedDeque<>();
	private boolean						_alive		= true;

	public Container(int threads, String name)
	{
		if (threads < 0 || threads > 100) throw new IllegalArgumentException("");
		for (int i = 0; i < threads; i++)
		{
			final String threadName = String.format("%s.thread%d", name, i);
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
			}, threadName).start();
		}
	}

	public void addLogicProcess(LogicProcess process)
	{
		_processes.add(process);
	}
}
