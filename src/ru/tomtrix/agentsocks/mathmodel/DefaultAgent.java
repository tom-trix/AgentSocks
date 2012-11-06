package ru.tomtrix.agentsocks.mathmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author tom-trix
 *
 */
public class DefaultAgent extends Agent
{

	public DefaultAgent(@JsonProperty("_name") String name) throws Exception
	{
		super(name);
	}

}
