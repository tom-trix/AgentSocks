package ru.tomtrix.agentsocks.infrastructure;

import java.util.Collection;
import ru.tomtrix.agentsocks.Constants;
import com.fasterxml.jackson.annotation.*;
import ru.tomtrix.agentsocks.messaging.Mail;

/** @author tom-trix */
public class Node
{
	/** fse */
	private final Container	_container;
	/** dawfse */
	@JsonIgnore
	// JsonIgnore ОБЯЗАТЕЛЬНО!!! (Поскольку ссылка на себя должна назначаться в конструкторе Node)
	private final Mail		_mail;
	private final int		_rank;

	/** fs */
	Node(@JsonProperty("_rank") int rank)
	{
		_rank = rank;
		_container = new Container(Constants.DEFAULT_THREADS, rank);
		_mail = new Mail(this);
	}

	/** @return the _container */
	public Container get_container()
	{
		return _container;
	}

	/** @param bufferSize
	 * @throws Exception
	 * @see ru.tomtrix.agentsocks.messaging.Mail#startListening(int) */
	public void run() throws Exception
	{
		_mail.startListening(Constants.BUFFER_SIZE);
		_container.run();
	}

	@Override
	public String toString()
	{
		StringBuffer sbuf = new StringBuffer(String.format("Node %d. Container has the following processes:\n", _rank));
		try
		{
			Collection<LogicProcess> processes = _container.getProcesses();
			if (processes.size() == 0)
				sbuf.append("<no processes>\n");
			for (LogicProcess process : processes)
				sbuf.append(process);
		}
		catch (IllegalAccessException e)
		{
			sbuf.append("Information is unavailable cause the container is running...\n");
		}
		return sbuf.toString();
	}

	/** @return the _rank */
	public int get_rank()
	{
		return _rank;
	}
}
