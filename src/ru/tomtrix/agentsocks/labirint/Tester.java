package ru.tomtrix.agentsocks.labirint;

import org.apache.log4j.Logger;
import ru.tomtrix.agentsocks.mathmodel.*;

public class Tester
{
	public static void main(String[] args) throws Exception
	{
		Container container = new Container(1, "MainContainer");
		LogicProcess process = new LogicProcess("fuck");
		process.addAgent(new Man());
		container.addLogicProcess(process);
		Logger.getLogger(Tester.class).info("Всё ОК");
		System.in.read();
	}
}
