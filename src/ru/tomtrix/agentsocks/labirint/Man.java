package ru.tomtrix.agentsocks.labirint;

import ru.tomtrix.agentsocks.mathmodel.*;

public class Man extends Agent
{
	public Man(String name) throws Exception
	{
		super(name);
		addVariable("int x=3;");
		addFunction("public void go() {x++; System.out.println(x)}");
		compileAgent();
		get_eventList().addEvent(2d, "go", this);
	}
}
