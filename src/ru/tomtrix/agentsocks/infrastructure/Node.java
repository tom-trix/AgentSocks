package ru.tomtrix.agentsocks.infrastructure;

import ru.tomtrix.agentsocks.Constants;
import ru.tomtrix.agentsocks.message.Mail;
import com.fasterxml.jackson.annotation.*;

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
	 * @see ru.tomtrix.agentsocks.message.Mail#startListening(int) */
	public void run() throws Exception
	{
		_mail.startListening(Constants.BUFFER_SIZE);
		_container.run();
	}

	/** @return the _rank */
	public int get_rank()
	{
		return _rank;
	}
}
