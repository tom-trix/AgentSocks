package ru.tomtrix.agentsocks.infrastructure;

import ru.tomtrix.agentsocks.Constants;
import mpi.MPI;

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
		//TODO
		if (MPI.COMM_WORLD.Rank() == rank)
		{
		_rank = rank;
		_container = new Container(5, rank);	//TODO 5 потоков
		_mail = new Mail(this);
		}
		else {
			_rank = -1;
			_container = null;
			_mail = null;
		}
	}

	/** @return the _container */
	public Container get_container()
	{
		return _container;
	}

	/**
	 * @param bufferSize
	 * @throws Exception 
	 * @see ru.tomtrix.agentsocks.infrastructure.Mail#startListening(int)
	 */
	public void run() throws Exception
	{
		_mail.startListening(Constants.BUFFER_SIZE);
		_container.compileAgents();
		_container.run();
	}

	/**
	 * @return the _rank
	 */
	public int get_rank()
	{
		return _rank;
	}
}
