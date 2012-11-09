package ru.tomtrix.agentsocks.infrastructure;

import com.fasterxml.jackson.annotation.*;

/** @author tom-trix */
public class Node
{
	/** fse */
	private final Container	_container;
	/** dawfse */
	@JsonIgnore			//ОБЯЗАТЕЛЬНО!!! (Поскольку ссылка на себя должна назначаться в конструкторе Node)
	private final Mail 	_mail;
	private final int		_rank;

	/** fs */
	Node(@JsonProperty("_rank") int rank)
	{
		_rank = rank;
		_container = new Container(5, rank);	//TODO 5 потоков
		_mail = new Mail(this);
	}

	/** @return the _container */
	public Container get_container()
	{
		return _container;
	}

	/**
	 * @param bufferSize
	 * @see ru.tomtrix.agentsocks.infrastructure.Mail#startListening(int)
	 */
	public void startListening(int bufferSize)
	{
		_mail.startListening(bufferSize);
	}

	/**
	 * @return the _rank
	 */
	public int get_rank()
	{
		return _rank;
	}
}
