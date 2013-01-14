package ru.tomtrix.agentsocks.modeleditor;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.tree.TreeNode;

/**
 * fwer
 */
public class GUIController implements MouseListener
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
        if (!SwingUtilities.isRightMouseButton(e)) return;
        JPopupMenu menu = new JPopupMenu();
        final TreeNode cur = (TreeNode) _mvcViewRef.getTree().getLastSelectedPathComponent();
        if (cur == null) return;
        else if (cur.toString().startsWith("<"))
        {
            JMenuItem item = new JMenuItem("Create new Model");
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
        else if (cur.toString().startsWith("Model"))
        {
            JMenuItem item = new JMenuItem("Create new Node");
            item.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    _mvcViewRef.setStatus(_mvcModelRef.createNode());
                    _mvcViewRef.rebuildTreeByModel();
                }
            });
            menu.add(item);
        }
        else if (cur.toString().startsWith("Node"))
        {
            final String rank = cur.toString().substring(5);
            JMenuItem item = new JMenuItem("Create new Logic Process");
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
        else if (cur.toString().startsWith("Process"))
        {
            final String name = cur.toString().substring(8);
            final String rank = cur.getParent().toString().substring(5);
            JMenuItem item = new JMenuItem("Create new Agent");
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
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
