package ru.tomtrix.agentsocks.infrastructure;

import mpi.*;
import java.util.*;
import org.apache.log4j.Logger;
import java.util.concurrent.ConcurrentLinkedDeque;

/** @author tom-trix */
public class MPISenderListener
{
	private static final MPISenderListener	_mpi	= new MPISenderListener();

	private MPISenderListener()
	{}

	public static MPISenderListener getInstance()
	{
		return _mpi;
	}

	private class Query
	{
		public int[]	data;
		public int		destination;

		public Query(int[] data, int destination)
		{
			this.data = data;
			this.destination = destination;
		}
	}

	private Node			_node;
	private Queue<Query>	_queue	= new ConcurrentLinkedDeque<>();

	void setNode(Node node)
	{
		_node = node;
	}

	int getMPIrank()
	{
		return MPI.COMM_WORLD.Rank();
	}

	void startService() throws IllegalAccessException
	{
		if (_node == null) throw new IllegalAccessException("fsse");
		if (_node.get_rank() != MPI.COMM_WORLD.Rank()) throw new RuntimeException("fsjhtyopui");
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (true)
				{
					try
					{
						Thread.sleep(38);
						Query q = _queue.poll();
						if (q == null) continue;
						MPI.COMM_WORLD.Send(q.data, 0, q.data.length, MPI.INT, q.destination, 0);
					}
					catch (Exception e)
					{
						Logger.getLogger(getClass()).error("sfefs", e);
					}
				}
			}
		}, "MPISender").start();
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (true)
				{
					try
					{
						Thread.sleep(37);
						int buf[] = new int[100];
						Status status = MPI.COMM_WORLD.Recv(buf, 0, 100, MPI.INT, MPI.ANY_SOURCE, MPI.ANY_TAG);
						_node.get_container().getProcessByName("MainProcess").get_agents().get(0).notifyAgent(Arrays.copyOf(buf, status.count));
						Logger.getLogger(getClass()).info("Status = " + status);
					}
					catch (Exception e)
					{
						Logger.getLogger(getClass()).error("aegliu", e);
					}
				}
			}
		}, "MPIListener").start();
	}

	public void send(int[] data, int destination)
	{
		_queue.add(new Query(data, destination));
	}
}
