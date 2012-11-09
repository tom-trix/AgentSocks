package ru.tomtrix.agentsocks.test;

import ru.tomtrix.agentsocks.mathmodel.*;

public class Man extends Agent
{
	public Man(String name) throws Exception
	{
		super(name);
		addVariable("int x=3;");
		addFunction("public void go() {x++; System.out.println(x);}");
		/*get_eventList().addEvent(2d, "go");
		get_eventList().addEvent(4d, "go");
		get_eventList().addEvent(7d, "go");*/
		compileAgent();
	}
}
