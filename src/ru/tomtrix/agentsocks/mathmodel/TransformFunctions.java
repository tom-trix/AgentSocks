package ru.tomtrix.agentsocks.mathmodel;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javassist.*;

/** @author tom-trix */
public class TransformFunctions
{
	/** container for methods */
	private final Map<String, Method>	_methods	= new ConcurrentHashMap<>();
	/** class whose instance generates new methods at the runtime */
	private final CtClass				_class;
	/** instance that generates new methods at the runtime */
	private Object						_instance;

	public TransformFunctions(Agent agent)
	{
		_class = ClassPool.getDefault().makeClass("$Runtime" + agent._name);
	}

	/** adds new method to the internal class
	 * @param fid - function id (<b>MUST</b> be unique)
	 * @param code - string of java code (including both a signature and the method body)
	 * @param name - name of the method (as common the parameter is recommended to be named like <b>fid</b> excepting the overrided functions)
	 * @param argTypes - list of references to the function parameters
	 * @throws Exception - if something goes wrong, i.e. if code can't be compiled */
	public void addNewMethod(String fid, String code, String name, Class<?>... argTypes) throws Exception
	{
		_class.addMethod(CtNewMethod.make(code, _class));
		_instance = _class.toClass().newInstance();
		_methods.put(fid, _instance.getClass().getMethod(name, argTypes));
	}

	/** calls the function <b>f.fid</b> with the <b>f.parameters</b> arguments
	 * @param f - function object that contains function id (fid) and the parameters
	 * @throws Exception */
	public void invokeMethod(Function f) throws Exception
	{
		if (!_methods.containsKey(f.get_fid())) throw new NoSuchMethodException(String.format("Function with fid \"%s\" doesn't exist", f.get_fid()));
		_methods.get(f.get_fid()).invoke(_instance, f.get_parameters());
	}

	/** checks weather the parameters correspond to the ones determined in the constructor
	 * @param fid - function id
	 * @param pars - list of parameters
	 * @return 
	 * @throws IllegalAccessException */
	public boolean parametersCorrespondToClasses(String fid, Object... pars) throws IllegalAccessException
	{
		if (_methods.get(fid) == null) throw new IllegalAccessException(String.format("There is no function with the fid \"%s\"", fid));
		Class<?>[] curr = _methods.get(fid).getParameterTypes();
		if (curr.length != pars.length) return false;
		for (int i = 0; i < curr.length; i++)
			if (!curr[i].isInstance(pars[i])) return false;
		return true;
	}
	
	public Set<String> getAllFids()
	{
		return _methods.keySet();
	}
}
