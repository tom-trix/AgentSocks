package ru.tomtrix.agentsocks.infrastructure;

/** @author tom-trix */
public class Node
{
	/** fse */
	private final Container			_container	= new Container(5, "MainContainer");
	/** dawfse */
	private final MPIAgent	_mpi		= MPIAgent.getInstance();
	private final int				_rank;

	/** fs */
	public Node(int rank)
	{
		_rank = rank;
		_mpi.setNode(this);
	}

	/** @throws IllegalAccessException */
	public void run() throws IllegalAccessException
	{
		_mpi.startService();
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
}
