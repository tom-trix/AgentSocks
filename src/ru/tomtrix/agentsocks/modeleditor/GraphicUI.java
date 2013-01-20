package ru.tomtrix.agentsocks.modeleditor;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.tree.*;
import java.util.*;

import org.apache.log4j.Logger;
import ru.tomtrix.agentsocks.mathmodel.Agent;
import ru.tomtrix.agentsocks.infrastructure.*;

/**
 * few
 */
public class GraphicUI extends JFrame
{
    private final MVCModel _mvcModelRef;
    private final JTextField _statusBar = new JTextField();
    private final JMenuBar _menu = new JMenuBar();
    private final JTree _tree = new JTree(new DefaultMutableTreeNode("<Right-click here>"));
    private final JSplitPane _split1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private final JSplitPane _split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private final JList<String> _variables = new JList<>(new String[] {"<No variables>"});
    private final JList<String> _events = new JList<>(new String[] {"<No events>"});
    private final JList<String> _fids = new JList<>(new String[] {"<No functions>"});
    private final JTextArea _functionBox = new JTextArea();

    private String _lastTreeNode;

    public GraphicUI(MVCModel model)
    {
        super("Model Editor");
        if (model==null) throw new NullPointerException("gdr");
        _mvcModelRef = model;
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

    private static void selectNode(JTree tree, TreePath parent, String node)
    {
        TreeNode n = (TreeNode) parent.getLastPathComponent();
        if (n.toString().equals(node))
            tree.setSelectionPath(parent);
        else if (n.getChildCount() >= 0)
            for (Enumeration e=n.children(); e.hasMoreElements();)
                selectNode(tree, parent.pathByAddingChild(e.nextElement()), node);
    }

    void rebuildTreeByModel()
    {
        try
        {
            Model model = _mvcModelRef.get_model();
            if (model==null) return;
            _lastTreeNode = _tree.getLastSelectedPathComponent().toString();
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
            selectNode(_tree, new TreePath(root), _lastTreeNode);
            if (_tree.getSelectionCount()==0) _tree.setSelectionRow(0);
        }
        catch (IllegalAccessException e)
        {
            Logger.getLogger(getClass()).error("frs", e);
        }
    }

    public void run()
    {
        GUIController controller = new GUIController(_mvcModelRef, this);

        // tree
        rebuildTreeByModel();
        _tree.setName("tree");
        _tree.setSelectionRow(0);
        _tree.addMouseListener(controller);
        _tree.addTreeSelectionListener(controller);
        _tree.setPreferredSize(new Dimension(200, 0));

        // splitContainer 1
        _split1.setRightComponent(new JScrollPane(_functionBox));
        _split1.setLeftComponent(new JScrollPane(_fids));

        // splitContainer 2
        _split2.setRightComponent(new JScrollPane(_events));
        _split2.setLeftComponent(new JScrollPane(_variables));

        // fids listbox
        _fids.setName("fids");
        _fids.setSelectedIndex(0);
        _fids.addMouseListener(controller);

        // variables listbox
        _variables.setName("variables");
        _variables.setSelectedIndex(0);
        _variables.setFixedCellWidth(200);
        _variables.addMouseListener(controller);

        // events listbox
        _events.setName("events");
        _events.setSelectedIndex(0);
        _events.setFixedCellWidth(200);
        _events.addMouseListener(controller);

        // menu
        JMenu file = new JMenu("File");
        JMenuItem load = new JMenuItem("Load model");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "fes");
            }
        });
        JMenuItem save = new JMenuItem("Save model");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "fes");
            }
        });
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        file.add(load);
        file.add(save);
        file.addSeparator();
        file.add(exit);
        _menu.add(file);

        // form
        setSize(900, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignore) {}
        setJMenuBar(_menu);
        getContentPane().add(_split2, BorderLayout.EAST);
        getContentPane().add(_split1, BorderLayout.CENTER);
        getContentPane().add(_statusBar, BorderLayout.SOUTH);
        getContentPane().add(new JScrollPane(_tree), BorderLayout.WEST);
        setVisible(true);
    }

    public void setStatus(String s)
    {
        _statusBar.setText(s);
    }

    public void refreshVariables(Collection<String> vars)
    {
        _variables.removeAll();
        _variables.setListData(vars==null || vars.isEmpty() ? new String[] {"<No variables>"} : vars.toArray(new String[vars.size()]));
        _variables.setSelectedIndex(0);
    }

    public void refreshEvents(Collection<String> events)
    {
        _events.removeAll();
        _events.setListData(events==null || events.isEmpty() ? new String[] {"<No events>"} : events.toArray(new String[events.size()]));
        _events.setSelectedIndex(0);
    }

    public void refreshFids(Collection<String> fids)
    {
        _fids.removeAll();
        _fids.setListData(fids==null || fids.isEmpty() ? new String[] {"<No fids>"} : fids.toArray(new String[fids.size()]));
        _fids.setSelectedIndex(0);
    }

    public JTree getTree()
    {
        return _tree;
    }

    public JList getVariablesListbox()
    {
        return _variables;
    }

    public JList getEventsListbox()
    {
        return _events;
    }

    public JList getFidsListbox()
    {
        return _fids;
    }

    public String getFunctionText()
    {
        return _functionBox.getText();
    }
}
