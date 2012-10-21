/**
 * 
 */
package ru.tomtrix.agentsocks.labirint;

import org.apache.log4j.Logger;

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
		System.out.println("x = " + _state.get("x"));
		_transformFunctions.addNewMethod("go", "public void go(ru.tomtrix.agentsocks.labirint.Mammals man, Double newx) {  }", "go", Mammals.class, Double.class);
		_eventList.addEvent(2d, "go", new Cat(), 4d);
		System.out.println("x = " + _state.get("x"));
		Logger.getLogger(getClass()).info("Всё ОК");
	}
}
