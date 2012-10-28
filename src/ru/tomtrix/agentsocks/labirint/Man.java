package ru.tomtrix.agentsocks.labirint;

import org.apache.log4j.Logger;
import ru.tomtrix.agentsocks.mathmodel.*;

public class Man extends Agent
{
	public Man(String name) throws Exception
	{
		super(name);
		int a = 3;
		_state.addStateItem("x", StateItemAccess.PUBLIC, a);
		Logger.getLogger(getClass()).debug("x = " + _state.get("x"));
		String code = 
			"public void go(ru.tomtrix.agentsocks.labirint.Man man) throws Exception" +
			"{" +
				"Integer d = man.get_state().get(\"x\"); " + 
				"man.get_state().set(\"y\", 1+7); " +
				"System.out.println(\"Бля\");" +
			"}";
		_transformFunctions.addNewMethod("go", code, "go", Man.class);
		_eventList.addEvent(2d, "go", this);
	}
}
