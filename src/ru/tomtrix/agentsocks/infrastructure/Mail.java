package ru.tomtrix.agentsocks.infrastructure;

import java.io.*;
import ru.tomtrix.mpiagent.*;
import org.apache.log4j.Logger;
import ru.tomtrix.agentsocks.utils.ArrayTransformer;

/**
 * @author tom-trix
 */
public class Mail implements MPIAgentListener
{
	public static void send(Serializable data, int node, String process, int agent)
	{
		try
		{
			MPIAgent.getInstance().send(ArrayTransformer.serialize(new Message(data, process, agent)), node);
		}
		catch (IOException e)
		{
			Logger.getLogger(Mail.class).error("daeaw", e);
		}
	}
	
	private final Node _node;
	
	public Mail(int bufferSize, Node node)
	{
		if (node == null || bufferSize < 8 || bufferSize > 65536) throw new IllegalArgumentException("aef");
		_node = node;
		MPIAgent.getInstance().start(this, bufferSize);
	}

	@Override
	public void messageReceived(byte[] data)
	{
		try
		{
			Message m = (Message) ArrayTransformer.deserialize(data);
			_node.get_container().getProcessByName(m.get_process()).get_agents().get(m.get_agent()).notifyAgent(m.get_data());
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("htfjyfee", e);
		}
	}
}
