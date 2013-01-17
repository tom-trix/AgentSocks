package ru.tomtrix.agentsocks.modeleditor;


import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.util.Enumeration;
import org.apache.log4j.Logger;
import ru.tomtrix.agentsocks.mathmodel.Agent;
import ru.tomtrix.agentsocks.infrastructure.*;

/**
 * few
 */
public class GraphicUI extends JFrame
{
    private final MVCModel _mvcModel;
    private final JTextField _statusBar = new JTextField();
    private final JTree _tree = new JTree(new DefaultMutableTreeNode("<Right-click here>"));
    private final JSplitPane _split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    public GraphicUI(MVCModel model)
    {
        super("Model Editor");
        if (model==null) throw new NullPointerException("gdr");
        _mvcModel = model;
    }

    private static void expandAll(JTree tree, TreePath parent, boolean expand)
    {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0)
            for (Enumeration e=node.children(); e.hasMoreElements();)
                expandAll(tree, parent.pathByAddingChild(e.nextElement()), expand);
        if (expand)
            tree.expandPath(parent);
        else
            tree.collapsePath(parent);
    }

    void rebuildTreeByModel()
    {
        try
        {
            Model model = _mvcModel.get_model();
            if (model==null) return;
            DefaultTreeModel tm = (DefaultTreeModel)_tree.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) tm.getRoot();
            root.removeAllChildren();
            root.setUserObject(new DefaultMutableTreeNode("Model " +model.get_name()));
            for (Node node : model.get_nodes())
            {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode("Node " + node.get_rank());
                root.add(childNode);
                for (LogicProcess p : node.get_container().get_processes())
                {
                    DefaultMutableTreeNode childProcess = new DefaultMutableTreeNode("Process " + p.get_name());
                    childNode.add(childProcess);
                    for (Agent agent : p.get_agents())
                        childProcess.add(new DefaultMutableTreeNode("Agent " + agent.get_name()));
                }
            }
            tm.reload();            //на эту херотень я потратил 3,5 часа!!!
            expandAll(_tree, new TreePath(root), true);
            _tree.setSelectionRow(0);
        }
        catch (IllegalAccessException e)
        {
            Logger.getLogger(getClass()).error("frs", e);
        }
    }

    public void run()
    {
        setSize(700, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignore) {}
        rebuildTreeByModel();
        _tree.setSelectionRow(0);
        _tree.addMouseListener(new GUIController(_mvcModel, this));
        _split.setLeftComponent(new JList<>(new String[]{"fgser", "frs", "111", "22"}));
        getContentPane().add(new JScrollPane(_tree), BorderLayout.WEST);
        getContentPane().add(_statusBar, BorderLayout.SOUTH);
        getContentPane().add(_split, BorderLayout.EAST);
        setVisible(true);
    }

    public void setStatus(String s)
    {
        _statusBar.setText(s);
    }

    public JTree getTree()
    {
        return _tree;
    }
}
