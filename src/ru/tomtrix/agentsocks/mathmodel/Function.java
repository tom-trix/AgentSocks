package ru.tomtrix.agentsocks.mathmodel;

import java.lang.reflect.Method;

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
	public Function(String fid, Object... parameters)
	{
		_fid = fid;
		_parameters = parameters;
	}
	
	public Object execute(Object methodKeeper) throws Exception
	{
		for (Method m : methodKeeper.getClass().getMethods())
			if (m.getName().equals(_fid))
				return m.invoke(methodKeeper, _parameters);
		return null;
	}

	/** @return the _name */
	public String get_fid()
	{
		return _fid;
	}

	/** @return the _parameters */
	public Object[] get_parameters()
	{
		return _parameters;
	}
}
