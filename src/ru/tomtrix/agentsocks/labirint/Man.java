package ru.tomtrix.agentsocks.labirint;

import org.apache.log4j.Logger;
import ru.tomtrix.agentsocks.mathmodel.*;

public class Man extends Agent
{
	public Man() throws Exception
	{
		_state.addStateItem("x", Double.class, StateItemAccess.PUBLIC, 3d);
		Logger.getLogger(getClass()).debug("x = " + _state.get("x"));
		_transformFunctions.addNewMethod("go", "public void go(ru.tomtrix.agentsocks.labirint.Man man) { double d = ((Double) man.get_state().get(\"x\")).doubleValue(); man.get_state().set(\"x\", Double.valueOf(d+7)); }", "go", Man.class);
		_eventList.addEvent(2d, "go", this);
	}
}
