/**
 * 
 */
package ru.tomtrix.agentsocks.mathmodel;

/**
 * @author tom-trix
 *
 */
public class Function
{
	private final String _fid;
	private final Object[] _parameters;
	
	public Function(String fid, Object ... parameters)
	{
		_fid = fid;
		_parameters = parameters;
	}

	/**
	 * @return the _name
	 */
	public String get_fid()
	{
		return _fid;
	}

	/**
	 * @return the _parameters
	 */
	public Object[] get_parameters()
	{
		return _parameters;
	}
	
}
