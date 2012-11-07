package ru.tomtrix.agentsocks.infrastructure;

/** @author tom-trix */
public class Node
{
	public static final String CONTAINER_NAME = "MainContainer";
	public static final int BUFFER_SIZE = 256;
	
	/** fse */
	private final Container	_container	= new Container(5, CONTAINER_NAME);
	/** dawfse */
	private final Mail 	_mail;
	private final int		_rank;

	/** fs */
	public Node(int rank)
	{
		_rank = rank;
		_mail = new Mail(BUFFER_SIZE, this);
	}

	/** @throws IllegalAccessException */
	public void run() throws IllegalAccessException
	{
		_container.run();
	}

	/** @throws IllegalAccessException */
	public void stop() throws IllegalAccessException
	{
		_container.stop();
	}

	/** @return the _container */
	public Container get_container()
	{
		return _container;
	}

	/** @return the _rank */
	public int get_rank()
	{
		return _rank;
	}

	/**
	 * @return the _mail
	 */
	public Mail get_mail()
	{
		return _mail;
	}
}
