package ru.tomtrix.agentsocks.test;

import org.junit.Test;
import java.util.Random;
import static org.junit.Assert.*;
import ru.tomtrix.agentsocks.mathmodel.*;
import ru.tomtrix.agentsocks.messaging.LocalMail;
import ru.tomtrix.agentsocks.messaging.Mail;
import ru.tomtrix.agentsocks.infrastructure.*;
import ru.tomtrix.javassistwraper.ClassStore;

/** gsrrg
 * @author tom-trix */
public class HeisenbugTest
{
	private Random	_r	= new Random(System.currentTimeMillis());

	@Test
	public void test() throws Exception
	{
		final int n = 3;
		final int m = 500;
		ClassStore.getInstance().addClassPath(LocalMail.class);
		ClassStore.getInstance().addImport(LocalMail.class.getPackage().getName());
		Model model = new Model("debug");
		model.addNode();
		Node node = model.getNodeByNumber(0);
		for (int i = 0; i < n; i++)
		{
			node.get_container().addLogicProcess("Process" + i);
			LogicProcess p = node.get_container().getProcessByName("Process" + i);
			p.addAgent(new DefaultAgent("TestAgent" + i, "TestAgent" + i));
			Agent agent = p.getAgentByNumber(0);
			agent.addVariable("public long msg = 0L;");
			agent.addFunction(String.format("public void go() {msg++; LocalMail.send(new StateChanged(\"msg\", Long.valueOf(msg)), \"Process%d\", \"TestAgent%d\", null);}", i+1 % n, i+1 % n));
			for (int j = 0; j < m; j++)
				agent.addEvent(_r.nextDouble() * 100, "go");
		}
		model.compileAgents();
		node.run();
		Thread.sleep(4000);
		assertTrue(true);
	}
}
