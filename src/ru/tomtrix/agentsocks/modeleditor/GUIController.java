package ru.tomtrix.agentsocks.modeleditor;

import javax.swing.*;
import java.awt.event.*;
import java.util.NoSuchElementException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeNode;

/**
 * fwer
 */
public class GUIController implements MouseListener, TreeSelectionListener
{
    private final MVCModel _mvcModelRef;
    private final GraphicUI _mvcViewRef;

    public GUIController(MVCModel model, GraphicUI view)
    {
        if (model==null || view == null) throw new NullPointerException("fsr");
        _mvcModelRef = model;
        _mvcViewRef = view;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (!(e.getSource() instanceof JComponent)) return;
        if (!SwingUtilities.isRightMouseButton(e)) return;
        JPopupMenu menu = new JPopupMenu();
        JMenuItem item;
        switch (((JComponent)e.getSource()).getName())
        {
            case "tree":
                final TreeNode curT = (TreeNode) _mvcViewRef.getTree().getLastSelectedPathComponent();
                if (curT == null) return;
                else if (curT.toString().startsWith("<"))
                {
                    item = new JMenuItem("Create new Model");
                    item.addActionListener(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String s = JOptionPane.showInputDialog(null, "Input the model name");
                            if (s==null || s.trim().isEmpty()) return;
                            _mvcViewRef.setStatus(_mvcModelRef.createModel(s.trim()));
                            _mvcViewRef.rebuildTreeByModel();
                        }
                    });
                    menu.add(item);
                }
                else if (curT.toString().startsWith("Model"))
                {
                    item = new JMenuItem("Create new Node");
                    item.addActionListener(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            _mvcViewRef.setStatus(_mvcModelRef.createNode());
                            _mvcViewRef.rebuildTreeByModel();
                        }
                    });
                    menu.add(item);
                }
                else if (curT.toString().startsWith("Node"))
                {
                    final String rank = curT.toString().substring(5);
                    item = new JMenuItem("Create new Logic Process");
                    item.addActionListener(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String s = JOptionPane.showInputDialog(null, "Input the process name");
                            if (s==null || s.trim().isEmpty()) return;
                            _mvcViewRef.setStatus(_mvcModelRef.createProcess(s.trim(), rank));
                            _mvcViewRef.rebuildTreeByModel();
                        }
                    });
                    menu.add(item);
                }
                else if (curT.toString().startsWith("Process"))
                {
                    final String name = curT.toString().substring(8);
                    final String rank = curT.getParent().toString().substring(5);
                    item = new JMenuItem("Create new Agent");
                    item.addActionListener(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String s = JOptionPane.showInputDialog(null, "Input the agent name");
                            if (s==null || s.trim().isEmpty()) return;
                            _mvcViewRef.setStatus( _mvcModelRef.createAgent(s, name, rank));
                            _mvcViewRef.rebuildTreeByModel();
                        }
                    });
                    menu.add(item);
                    item = new JMenuItem("Rename process");
                    item.addActionListener(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String s = JOptionPane.showInputDialog(null, "Input new process name");
                            if (s==null || s.trim().isEmpty()) return;
                            _mvcViewRef.setStatus(_mvcModelRef.renameProcess(name, rank, s));
                            _mvcViewRef.rebuildTreeByModel();
                        }
                    });
                    menu.add(item);
                    item = new JMenuItem("Delete process");
                    item.addActionListener(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (JOptionPane.showConfirmDialog(null, "Are you sure") > 0) return;
                            _mvcViewRef.setStatus(_mvcModelRef.deleteProcess(name, rank));
                            _mvcViewRef.rebuildTreeByModel();
                        }
                    });
                    menu.add(item);
                }
                menu.show(_mvcViewRef.getTree(), e.getX(), e.getY());
                break;
            case "variables":
                final String curS =  _mvcViewRef.getVariablesListbox().getSelectedValue().toString();
                item = new JMenuItem("Add variable");
                item.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String s = JOptionPane.showInputDialog(null, "Input the variable definition");
                        if (s==null || s.trim().isEmpty()) return;
                        _mvcViewRef.setStatus(_mvcModelRef.addVariable(s));
                        _mvcViewRef.refreshVariables();
                    }
                });
                menu.add(item);
                if (!curS.startsWith("<"))
                {
                    item = new JMenuItem("Change variable");
                    item.addActionListener(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String s = JOptionPane.showInputDialog(null, "Input new variable definition");
                            if (s==null || s.trim().isEmpty()) return;
                            _mvcViewRef.setStatus("Not implemented"); //TODO
                            _mvcViewRef.refreshVariables();
                        }
                    });
                    menu.add(item);
                    item = new JMenuItem("Delete variable");
                    item.addActionListener(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (JOptionPane.showConfirmDialog(null, "Are you sure") > 0) return;
                            _mvcViewRef.setStatus(_mvcModelRef.deleteVariable(curS));
                            _mvcViewRef.refreshVariables();
                        }
                    });
                    menu.add(item);
                }
                menu.show(_mvcViewRef.getVariablesListbox(), e.getX(), e.getY());
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
        JOptionPane.showMessageDialog(null, e);
    }
}
