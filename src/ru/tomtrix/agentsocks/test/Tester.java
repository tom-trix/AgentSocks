package ru.tomtrix.agentsocks.test;

import ru.tomtrix.agentsocks.mathmodel.*;

public class Tester
{
	public static void main(String[] args) throws Exception
	{
		Container container = new Container(1, "MainContainer");
		LogicProcess process = new LogicProcess("fuck");
		process.addAgent(new Man("Man"));
		container.addLogicProcess(process);
		container.run();
	}
}
