package ru.tomtrix.agentsocks.mathmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

/** grhtaf
 * @author tom-trix */
public class DefaultAgent extends Agent
{
	/** djaopfjaoef
	 * @param name
	 * @throws Exception */
	public DefaultAgent(@JsonProperty("_name") String name, @JsonProperty("_RAClassname") String RAClassname) throws Exception
	{
		super(name, RAClassname);
	}
}
