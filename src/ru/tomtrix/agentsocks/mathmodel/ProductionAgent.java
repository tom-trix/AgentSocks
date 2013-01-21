package ru.tomtrix.agentsocks.mathmodel;

import java.util.*;
import ru.tomtrix.productions.*;
import ru.tomtrix.agentsocks.utils.StringUtils;

/**
 * dw
 */
public class ProductionAgent extends Agent
{
    /** fs */
    private List<String> _initCode = new ArrayList<>(Arrays.asList("public void initialize() throws Exception {", "Condition cond;", "}"));

    /**
     * creates an agent with a specific name; it also builds runtime class that will comrise fields and methods of the agent
     * @param name - agent's name (and corresponding runtime class's name)
     * @throws Exception
     */
    public ProductionAgent(String name, String RAClassname) throws Exception
    {
        super(name, RAClassname);
        super.addVariable("public Core _core = new AgentProductionCore();");
        super.addVariable("public RuleSet _ruleset = new RuleSet();");
    }

    /**
     * fesfsefo
     * @param variable fsefes
     * @param value gdrh
     */
    public void initializeVariable(String variable, String value)
    {
        _initCode.add(_initCode.size()-1, String.format("_%s.initialize(\"%s\");", variable, value));
    }

    /**
     * nfsenfs
     * @param name grseg
     * @param variables grwege
     * @param signs fwsrge
     * @param values ged
     * @param operations gsge
     * @param resultVariable fggre
     * @param resultValue gwsfwa
     */
    public void addRule(String name, List<String> variables, List<Inequality> signs, List<String> values, List<Operation> operations, String resultVariable, String resultValue)
    {
        if (name==null || variables==null || signs==null || values==null || resultVariable==null || resultValue==null || (name.trim()+resultVariable.trim()+resultValue.trim()).isEmpty() || variables.isEmpty() || signs.isEmpty() || values.isEmpty()) throw new NullPointerException("sf");
        if (variables.size()!=signs.size() || variables.size()!=values.size() || variables.size()!=operations.size()+1) throw new IllegalArgumentException("gsef");
        _initCode.add(_initCode.size() - 1, String.format("cond = new Condition(new Operand(_%s, Inequality.%s, \"%s\", _core));", variables.get(0), signs.get(0), values.get(0)));
        for (int i=1; i<variables.size(); i++)
            _initCode.add(_initCode.size()-1, String.format("cond.addOperand(Operation.%s, new Operand(_%s, Inequality.%s, \"%s\", _core));", operations.get(i-1), variables.get(i), signs.get(i), values.get(i)));
        _initCode.add(_initCode.size()-1, String.format("_ruleset.addRule(new Rule(\"R%s\", cond, _%s, \"%s\", _core));", name, resultVariable, resultValue));
    }

    /**
     * fjeoafaon
     * @param goal nfaikebik
     * @throws Exception
     */
    public void addTestConsulting(String goal) throws Exception
    {
        super.addFunction(String.format("public void testConsulting() {\n    System.out.println(_core.startConsulting(_%s, _ruleset));\n}", goal));
    }

    public void addVariable(String s) throws Exception
    {
        if (s == null || s.isEmpty()) throw new NullPointerException("Empty code");
        super.addVariable(String.format("Variable _%s = new Variable(\"%s\", VariableType.INFERRIBLE);", s, s));
        _initCode.add(_initCode.size()-1, String.format("_core.addVariable(_%s);\n", s));
    }

    public void compileAgents() throws Exception
    {
        super.addFunction(StringUtils.getElements(_initCode, "\n"));
        super.compileAgents();
    }
}
