package ru.tomtrix.agentsocks;

import org.apache.log4j.Logger;
import ru.tomtrix.productions.Variable;
import ru.tomtrix.productions.core.Core;

/**
 * fesgrht
 */
public class AgentProductionCore extends Core
{
    private final Object _runtimeAssistantRef;

    /**
     * fadefse
     */
    public AgentProductionCore(Object runtimeAssistant)
    {
        super();
        _runtimeAssistantRef = runtimeAssistant;
    }

    @Override
    public String askForVariable(Variable variable)
    {
        try
        {
            return _runtimeAssistantRef.getClass().getField(variable.toString()).get(_runtimeAssistantRef).toString();
        }
        catch (Exception e) { Logger.getLogger(getClass()).error(String.format("Cannot extract the value from the Runtime Assistant [%s]", variable), e); }
        return null;
    }
}
