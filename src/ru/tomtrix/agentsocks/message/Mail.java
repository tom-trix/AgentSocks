package ru.tomtrix.agentsocks.message;

import java.io.*;
import ru.tomtrix.mpiagent.*;
import org.apache.log4j.Logger;

import ru.tomtrix.agentsocks.infrastructure.Node;
import ru.tomtrix.agentsocks.utils.ArrayTransformer;

/**
 * @author tom-trix
 */
public class Mail implements MPIAgentListener
{
	public static void send(IMessage data, int node, String process, String agent, String sender)
	{
		try
		{
			MPIAgent.getInstance().send(ArrayTransformer.serialize(new Envelope(data, process, agent, sender)), node);
		}
		catch (IOException e)
		{
			Logger.getLogger(Mail.class).error("daeaw", e);
		}
	}
	
	private final Node _nodeRef;
	
	public Mail(Node node)
	{
		if (node == null) throw new NullPointerException("aef");
		_nodeRef = node;
		Logger.getLogger(getClass()).info(String.format("Mail is sucessfully loaded within node %d", _nodeRef.get_rank()));
	}
	
	public void startListening(int bufferSize)
	{
		if (bufferSize < 16 || bufferSize > 65536) throw new IllegalArgumentException("aef");
		Logger.getLogger(getClass()).info(String.format("Listener on node %d OK", _nodeRef.get_rank()));
		MPIAgent.getInstance().start(this, bufferSize);		
	}

	@Override
	public void messageReceived(byte[] data)
	{
		try
		{
			Envelope m = (Envelope) ArrayTransformer.deserialize(data);
			_nodeRef.get_container().getProcessByName(m.get_process()).getAgentByName(m.get_agent()).notifyAgent(m.get_data());
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass()).error("htfjyfee", e);
		}
	}
}
