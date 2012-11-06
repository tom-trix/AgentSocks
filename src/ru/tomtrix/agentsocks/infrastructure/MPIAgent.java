package ru.tomtrix.agentsocks.infrastructure;

import mpi.*;

import java.io.IOException;
import java.util.*;
import org.apache.log4j.Logger;
import ru.tomtrix.agentsocks.utils.ArrayTransformer;
import java.util.concurrent.ConcurrentLinkedDeque;

/** @author tom-trix */
public class MPIAgent
{
	private static final MPIAgent	_mpi	= new MPIAgent();

	private MPIAgent()
	{}

	public static MPIAgent getInstance()
	{
		return _mpi;
	}

	private class Query
	{
		public int[]	data;
		public int		node;

		public Query(String data, int node, String process, int agent) throws IOException
		{
			this.node = node;
			this.data = ArrayTransformer.toIntArray(ArrayTransformer.serialize(new Message(process, agent, data)));
		}
	}

	private static final int BUFFER_SIZE = 128;
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
						Logger.getLogger(getClass()).debug("Gonna send " + q.data.length + " bytes of data to node #" + q.node);
						MPI.COMM_WORLD.Send(q.data, 0, q.data.length, MPI.INT, q.node, 0);
						Logger.getLogger(getClass()).debug("Sent successful");
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
						Logger.getLogger(getClass()).info("Start listening");
						int buf[] = new int[BUFFER_SIZE];
						Status status = MPI.COMM_WORLD.Recv(buf, 0, BUFFER_SIZE, MPI.INT, MPI.ANY_SOURCE, MPI.ANY_TAG);
						Logger.getLogger(getClass()).info("New message received!");
						Message m = (Message) ArrayTransformer.deserialize(ArrayTransformer.toByteArray(Arrays.copyOf(buf, status.count)));
						_node.get_container().getProcessByName(m.get_process()).get_agents().get(m.get_agent()).notifyAgent(m.get_data());
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

	public void send(String data, int node, String process, int agent) throws IOException
	{
		Logger.getLogger(getClass()).debug("Let's send: " + data);
		_queue.add(new Query(data, node, process, agent));
	}
}
