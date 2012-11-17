package ru.tomtrix.agentsocks.messaging;

import java.io.IOException;
import org.apache.log4j.Logger;
import ru.tomtrix.agentsocks.infrastructure.Node;

/** vsohjfs
 * @author tom-trix */
public class LocalMail
{
	/** saeopjfoe */
	private static Node	_nodeRef;

	/** fosehfuise
	 * @param node */
	public LocalMail(Node node)
	{
		if (node == null) throw new NullPointerException("Node can't be equal to null");
		_nodeRef = node;
	}

	/** osfiohnefo
	 * @param data
	 * @param process
	 * @param agent
	 * @param sender
	 * @throws Exception */
	public static void send(IMessage data, String process, String agent, String sender) throws Exception
	{
		// checks
		if (data == null) throw new NullPointerException("Data can't be null");
		if (process == null || process.trim().isEmpty()) throw new NullPointerException("Process that you're attempting to send can't be null");
		if (agent == null || agent.trim().isEmpty()) throw new NullPointerException("Agent that you're attempting to send can't be null");

		// sending
		try
		{
			_nodeRef.get_container().getProcessByName(process).getAgentByName(agent).notifyAgent(data, sender);
		}
		catch (IOException e)
		{
			Logger.getLogger(Mail.class).error("Failed to send a local message", e);
		}
	}
}
