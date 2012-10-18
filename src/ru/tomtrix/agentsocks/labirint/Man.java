/**
 * 
 */
package ru.tomtrix.agentsocks.labirint;

import ru.tomtrix.agentsocks.mathmodel.Agent;
import ru.tomtrix.agentsocks.mathmodel.StateItemAccess;

/**
 * @author tom-trix
 *
 */
public class Man extends Agent
{
	public Man() throws Exception
	{
		_state.addStateItem("x", Double.class, StateItemAccess.PUBLIC, 0d);
		_transformFunctions.addNewMethod("go_man", "public void go_man() { System.out.println(\"555\"); }");
		_eventList.addEvent(2d, "go_man");
	}
}
