/**
 * 
 */
package ru.tomtrix.agentsocks.mathmodel;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javassist.*;

/** @author tom-trix */
public class TransformFunctions
{
	private static int _currentClass = 0;
	private final Map<String, Method> _methods = new ConcurrentHashMap<>();
	private final CtClass _class = ClassPool.getDefault().makeClass("RuntimeClass" + _currentClass++);
	private Object _instance;
	

	public void addNewMethod(String fid, String code, String name, Class<?> ... argTypes) throws Exception
	{
		_class.addMethod(CtNewMethod.make(code, _class));
		_instance = _class.toClass().newInstance();
		_methods.put(fid, _instance.getClass().getMethod(name, argTypes));
	}
	
	public void invokeMethod(Function f) throws Exception
	{
		if (!_methods.containsKey(f.get_fid())) throw new NoSuchMethodException(String.format("Function with fid \"%s\" doesn't exist", f.get_fid()));
		_methods.get(f.get_fid()).invoke(_instance, f.get_parameters());
	}
	
	public boolean parametersCorrespondToClasses(String fid, Object ... pars)
	{
		Class<?>[] curr = _methods.get(fid).getParameterTypes();
		if (curr.length != pars.length) return false;
		for(int i=0; i<curr.length; i++)
			if (!curr[i].isInstance(pars[i])) return false;
		return true;
	}
}
