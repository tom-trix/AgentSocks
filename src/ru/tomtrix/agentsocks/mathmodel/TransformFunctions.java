/**
 * 
 */
package ru.tomtrix.agentsocks.mathmodel;

import javassist.*;

/** @author tom-trix */
public class TransformFunctions
{
	
	private final CtClass _class = ClassPool.getDefault().makeClass("Trix");
	private Object _instance;
	

	public void addNewMethod(String name, String code) throws Exception
	{
		_class.addMethod(CtNewMethod.make(code, _class));
		_instance = _class.toClass().newInstance();
	}
	
	public void invokeMethod(String name) throws Exception
	{
		_instance.getClass().getMethod(name).invoke(_instance);
	}
}
