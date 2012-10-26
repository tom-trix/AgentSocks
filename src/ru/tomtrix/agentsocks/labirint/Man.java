package ru.tomtrix.agentsocks.labirint;

import org.apache.log4j.Logger;
import ru.tomtrix.agentsocks.mathmodel.*;
import ru.tomtrix.agentsocks.test.*;

public class Man extends Agent
{
	public Man() throws Exception
	{
		_state.addStateItem("x", Double.class, StateItemAccess.PUBLIC, 0d);
		Logger.getLogger(getClass()).debug("x = " + _state.get("x"));
		_transformFunctions.addNewMethod("go", "public void go(ru.tomtrix.agentsocks.test.Mammals man, Double newx) {  }", "go", Mammals.class, Double.class);
		_eventList.addEvent(2d, "go", new Cat(), 4d);
		Logger.getLogger(getClass()).debug("x = " + _state.get("x"));
	}
}
