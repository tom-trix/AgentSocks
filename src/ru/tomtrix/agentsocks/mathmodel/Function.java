package ru.tomtrix.agentsocks.mathmodel;

import java.lang.reflect.Method;
import com.fasterxml.jackson.annotation.JsonProperty;

/** @author tom-trix */
public class Function
{
	/** function id */
	private final String	_fid;
	/** parameters for function invoking */
	private final Object[]	_parameters;

	/** creates a new function call object
	 * @param fid - function ID
	 * @param parameters - parameters for invoking */
	public Function(@JsonProperty("_fid") String fid, @JsonProperty("_parameters") Object... parameters)
	{
		_fid = fid;
		_parameters = parameters;
	}
	
	/**
	 * @param methodKeeper
	 * @return
	 * @throws Exception
	 */
	public Object execute(Object methodKeeper) throws Exception
	{
		//for each parameter obtain its class
		Class<?>[] classes = new Class<?>[_parameters.length];
		for (int i=0; i<_parameters.length; i++)
			classes[i] = _parameters[i].getClass();
		//trying to find a method via Reflection
		Method m = methodKeeper.getClass().getMethod(_fid, classes);
		if (m == null) throw new NullPointerException("frsfse");
		//execute one
		return m.invoke(methodKeeper, _parameters);
	}

	/**
	 * @return the _fid
	 */
	public String get_fid()
	{
		return _fid;
	}
}
