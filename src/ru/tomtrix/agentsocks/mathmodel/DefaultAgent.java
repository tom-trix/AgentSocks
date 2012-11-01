package ru.tomtrix.agentsocks.mathmodel;

import ru.tomtrix.agentsocks.Control;
import java.nio.file.AccessDeniedException;

/**
 * @author tom-trix
 *
 */
public class DefaultAgent extends Agent
{

	/**
	 * @param name
	 * @throws Exception 
	 */
	public DefaultAgent(String name) throws Exception
	{
		super(name);
	}
	
	/**
	 * @throws Exception
	 */
	public DefaultAgent() throws Exception 
	{
		super("Stub");			
		if (Control.CONSTRUCTOR_ACCESS_DENIED) throw new AccessDeniedException("fses");
	}

}
