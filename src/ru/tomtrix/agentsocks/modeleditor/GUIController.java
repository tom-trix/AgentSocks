package ru.tomtrix.agentsocks.modeleditor;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.Collection;
import javax.swing.tree.TreeNode;
import java.util.NoSuchElementException;
import ru.tomtrix.agentsocks.mathmodel.Agent;
import ru.tomtrix.agentsocks.utils.StringUtils;

/** fwer */
public class GUIController implements MouseListener, TreeSelectionListener
{
    public final transient static String SPLIT = " ";
    public final transient static String SPLIT_EVENT = ": ";

    private final MVCModel _mvcModelRef;
    private final GraphicUI _mvcViewRef;
    /** Переиспользуемая ссылка на popup-меню */
    private JPopupMenu _menu;
    /** Переиспользуемая ссылка на пункт popup-меню */
    private JMenuItem _item;

    public GUIController(MVCModel model, GraphicUI view)
    {
        if (model==null || view == null) throw new NullPointerException("fsr");
        _mvcModelRef = model;
        _mvcViewRef = view;
    }

    private JPopupMenu getTreeMenu()
    {
        final TreeNode cur = (TreeNode) _mvcViewRef.getTree().getLastSelectedPathComponent();
        if (cur == null) return null;
        _menu = new JPopupMenu();
        if (cur.toString().startsWith("<"))
        {
            _item = new JMenuItem("Create new Model");
            _item.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s = JOptionPane.showInputDialog(null, "Input the model name");
                    if (s == null || s.trim().isEmpty()) return;
                    _mvcViewRef.setStatus(_mvcModelRef.createModel(s.trim()));
                    _mvcViewRef.rebuildTreeByModel();
                }
            });
            _menu.add(_item);
        }
        else if (cur.toString().startsWith("Model"))
        {
            _item = new JMenuItem("Create new Node");
            _item.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    _mvcViewRef.setStatus(_mvcModelRef.createNode());
                    _mvcViewRef.rebuildTreeByModel();
                }
            });
            _menu.add(_item);
        }
        else if (cur.toString().startsWith("Node"))
        {
            final String rank = cur.toString().split(SPLIT)[1];
            _item = new JMenuItem("Create new Logic Process");
            _item.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s = JOptionPane.showInputDialog(null, "Input the process name");
                    if (s == null || s.trim().isEmpty()) return;
                    _mvcViewRef.setStatus(_mvcModelRef.createProcess(s.trim(), rank));
                    _mvcViewRef.rebuildTreeByModel();
                }
            });
            _menu.add(_item);
        }
        else if (cur.toString().startsWith("Process"))
        {
            final String name = cur.toString().split(SPLIT)[1];
            final String rank = cur.getParent().toString().split(SPLIT)[1];
            _item = new JMenuItem("Create new Agent");
            _item.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s = JOptionPane.showInputDialog(null, "Input the agent name");
                    if (s==null || s.trim().isEmpty()) return;
                    _mvcViewRef.setStatus( _mvcModelRef.createAgent(s, name, rank));
                    _mvcViewRef.rebuildTreeByModel();
                }
            });
            _menu.add(_item);
            _item = new JMenuItem("Rename process");
            _item.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s = JOptionPane.showInputDialog(null, "Input new process name");
                    if (s==null || s.trim().isEmpty()) return;
                    _mvcViewRef.setStatus(_mvcModelRef.renameProcess(name, rank, s));
                    _mvcViewRef.rebuildTreeByModel();
                }
            });
            _menu.add(_item);
            _item = new JMenuItem("Delete process");
            _item.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Are you sure") > 0) return;
                    _mvcViewRef.setStatus(_mvcModelRef.deleteProcess(name, rank));
                    _mvcViewRef.rebuildTreeByModel();
                }
            });
            _menu.add(_item);
        }
        return _menu;
    }

    private Agent getAgent()
    {
        Agent agent = _mvcModelRef.getCurrentAgent();
        if (agent==null)
        {
            JOptionPane.showMessageDialog(null, "Select an agent from the tree first");
            return null;
        }
        return agent;
    }

    private JPopupMenu getVariablesMenu()
    {
        final Agent agent = getAgent();
        if (agent == null) return null;
        final String cur =  _mvcViewRef.getVariablesListbox().getSelectedValue().toString();
        _menu = new JPopupMenu();
        _item = new JMenuItem("Add variable to " + agent.get_name());
        _item.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog(null, "Input the variable definition");
                if (s == null || s.trim().isEmpty()) return;
                s = _mvcModelRef.addVariable(s);
                if (s.contains("Exception"))
                    JOptionPane.showMessageDialog(null, "Input a correct variable. E.g. \"public int x = 8;\"");
                _mvcViewRef.setStatus(s);
                _mvcViewRef.refreshVariables(agent.getVariables());
            }
        });
        _menu.add(_item);
        if (!cur.startsWith("<"))
        {
            _item = new JMenuItem("Change variable in " + agent.get_name());
            _item.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s = JOptionPane.showInputDialog(null, "Input new variable definition");
                    if (s==null || s.trim().isEmpty()) return;
                    _mvcViewRef.setStatus("Not implemented"); //TODO
                    _mvcViewRef.refreshVariables(agent.getVariables());
                }
            });
            _menu.add(_item);
            _item = new JMenuItem("Delete variable from " + agent.get_name());
            _item.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Are you sure") > 0) return;
                    _mvcViewRef.setStatus(_mvcModelRef.deleteVariable(cur));
                    _mvcViewRef.refreshVariables(agent.getVariables());
                }
            });
            _menu.add(_item);
        }
        return _menu;
    }

    private JPopupMenu getEventsMenu()
    {
        final Agent agent = getAgent();
        if (agent == null) return null;
        final String cur =  _mvcViewRef.getEventsListbox().getSelectedValue().toString();
        _menu = new JPopupMenu();
        _item = new JMenuItem("Add event to " + agent.get_name());
        _item.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collection<String> fids = agent.getFids();
                if (fids.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "There are no functions. Add a function first");
                    return;
                }
                String fid = JOptionPane.showInputDialog(null, String.format("Input function name:\n%s", StringUtils.getElements(fids, ", ")));
                if (fid == null || fid.trim().isEmpty()) return;
                String time = JOptionPane.showInputDialog(null, "Input the timestamp");
                _mvcViewRef.setStatus(_mvcModelRef.addEvent(fid, time, null));
                _mvcViewRef.refreshEvents(agent.getEvents());
            }
        });
        _menu.add(_item);
        if (!cur.startsWith("<"))
        {
            _item = new JMenuItem("Change event in " + agent.get_name());
            _item.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    _mvcViewRef.setStatus("Not implemented"); //TODO
                    _mvcViewRef.refreshEvents(agent.getEvents());
                }
            });
            _menu.add(_item);
            _item = new JMenuItem("Delete event from " + agent.get_name());
            _item.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Are you sure") > 0) return;
                    _mvcViewRef.setStatus(_mvcModelRef.deleteEvent(cur.split(SPLIT_EVENT)[1], cur.split(SPLIT_EVENT)[0]));
                    _mvcViewRef.refreshEvents(agent.getEvents());
                }
            });
            _menu.add(_item);
        }
        return _menu;
    }

    private JPopupMenu getFidsMenu()
    {
        final Agent agent = getAgent();
        if (agent == null) return null;
        if (_mvcViewRef.getFunctionText().trim().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please, input the function code below!");
            return null;
        }
        final String cur =  _mvcViewRef.getFidsListbox().getSelectedValue().toString();
        _menu = new JPopupMenu();
        _item = new JMenuItem("Add function to " + agent.get_name());
        _item.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _mvcViewRef.setStatus(_mvcModelRef.addFunction(_mvcViewRef.getFunctionText()));
                _mvcViewRef.refreshEvents(agent.getFids());
            }
        });
        _menu.add(_item);
        if (!cur.startsWith("<"))
        {
            _item = new JMenuItem("Save function changes in " + agent.get_name());
            _item.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    _mvcViewRef.setStatus("Not implemented"); //TODO
                    _mvcViewRef.refreshEvents(agent.getFids());
                }
            });
            _menu.add(_item);
            _item = new JMenuItem("Delete function from " + agent.get_name());
            _item.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Are you sure") > 0) return;
                    _mvcViewRef.setStatus(_mvcModelRef.deleteFunction(cur));
                    _mvcViewRef.refreshEvents(agent.getFids());
                }
            });
            _menu.add(_item);
        }
        return _menu;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (!(e.getSource() instanceof JComponent)) return;
        if (!SwingUtilities.isRightMouseButton(e)) return;
        JPopupMenu m;
        switch (((JComponent)e.getSource()).getName())
        {
            case "tree":
                m = getTreeMenu();
                if (m!=null)
                    _menu.show(_mvcViewRef.getTree(), e.getX(), e.getY());
                break;
            case "variables":
                m = getVariablesMenu();
                if (m!=null)
                    _menu.show(_mvcViewRef.getVariablesListbox(), e.getX(), e.getY());
                break;
            case "events":
                m = getEventsMenu();
                if (m!=null)
                    _menu.show(_mvcViewRef.getEventsListbox(), e.getX(), e.getY());
                break;
            case "fids":
                m = getFidsMenu();
                if (m!=null)
                    _menu.show(_mvcViewRef.getEventsListbox(), e.getX(), e.getY());
                break;
            default: throw new NoSuchElementException("gtd");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void valueChanged(TreeSelectionEvent e)
    {
        // в модели следует "выделить" текущего агента
        final TreeNode curT = (TreeNode) _mvcViewRef.getTree().getLastSelectedPathComponent();
        if (curT == null) return;
        if (curT.toString().startsWith("Agent"))
        {
            String agent = curT.toString().split(SPLIT)[1];
            String process = curT.getParent().toString().split(SPLIT)[1];
            String node = curT.getParent().getParent().toString().split(SPLIT)[1];
            _mvcModelRef.useAgent(agent, process, node);
            Agent ag = _mvcModelRef.getCurrentAgent();
            _mvcViewRef.refreshVariables(ag.getVariables());
            _mvcViewRef.refreshEvents(ag.getEvents());
            _mvcViewRef.refreshFids(ag.getFids());
        }
    }
}
