package ru.tomtrix.agentsocks.test;

import ru.tomtrix.agentsocks.infrastructure.*;

public class Tester
{
	public static void main(String[] args) throws Exception
	{
		Node node = new Node(0);
		node.get_container().addLogicProcess("Fuck");
		node.get_container().getProcessByName("Fuck").addAgent(new Man("Man"));
		node.run();
	}
}
