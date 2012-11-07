package ru.tomtrix.agentsocks.ui;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.border.EmptyBorder;

import mpi.MPI;

import ru.tomtrix.mpiagent.MPIAgent;

public class GUI extends JFrame
{
	private static final long	serialVersionUID	= 6208515297698018657L;
	private final MVCmodel		_model;

	private JPanel				contentPane;
	private JTextField			textAgentName;
	private JTextField			textVarName;
	private JTextField			textEventFid;
	private JTextField			textTimestamp;
	private JTextField			textArgs;
	private JTextField			textEventMsg;

	/** Create the frame. */
	public GUI(MVCmodel model)
	{
		_model = model;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 354);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textAgentName = new JTextField();
		textAgentName.setBounds(36, 18, 122, 19);
		contentPane.add(textAgentName);
		textAgentName.setColumns(10);

		JButton btnCreateAgent = new JButton("Create agent");
		btnCreateAgent.setBounds(350, 12, 128, 25);
		btnCreateAgent.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String s = textAgentName.getText().trim();
				if (s.isEmpty()) return;
				try
				{
					JOptionPane.showMessageDialog(contentPane, _model.createAgent(s));
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(contentPane, e);
					e.printStackTrace();
				}
			}
		});
		contentPane.add(btnCreateAgent);

		textVarName = new JTextField();
		textVarName.setBounds(36, 50, 122, 19);
		contentPane.add(textVarName);
		textVarName.setColumns(10);

		JButton btnAddVariable = new JButton("Add variable");
		btnAddVariable.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String var = textVarName.getText().trim();
				if (var.isEmpty()) return;
				JOptionPane.showMessageDialog(contentPane, _model.addVariable(var));
			}
		});
		btnAddVariable.setBounds(350, 44, 128, 25);
		contentPane.add(btnAddVariable);

		final JTextArea textCode = new JTextArea();
		textCode.setBounds(12, 92, 466, 80);
		contentPane.add(textCode);

		JButton btnAddFunction = new JButton("Add function");
		btnAddFunction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String code = textCode.getText().trim();
				if (code.isEmpty()) return;
				JOptionPane.showMessageDialog(contentPane, _model.addFunction(code));
			}
		});
		btnAddFunction.setBounds(350, 184, 128, 25);
		contentPane.add(btnAddFunction);

		textEventFid = new JTextField();
		textEventFid.setBounds(36, 227, 122, 19);
		contentPane.add(textEventFid);
		textEventFid.setColumns(10);

		JButton btnAddEvent = new JButton("Add event");
		btnAddEvent.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String fid = textEventFid.getText().trim();
				String time = textTimestamp.getText().trim();
				if (fid.isEmpty() || time.isEmpty()) return;
				try
				{
					Double.parseDouble(time);
				}
				catch (Exception e2)
				{
					JOptionPane.showMessageDialog(contentPane, "Time must have a double format");
				}
				JOptionPane.showMessageDialog(contentPane, _model.addEvent(fid, time));
			}
		});
		btnAddEvent.setBounds(350, 221, 128, 25);
		contentPane.add(btnAddEvent);

		textTimestamp = new JTextField();
		textTimestamp.setBounds(183, 227, 69, 19);
		contentPane.add(textTimestamp);
		textTimestamp.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(46, 0, 70, 15);
		contentPane.add(lblName);

		JLabel lblVariable = new JLabel("Variable code");
		lblVariable.setBounds(46, 37, 112, 15);
		contentPane.add(lblVariable);

		JLabel lblFid_1 = new JLabel("Fid");
		lblFid_1.setBounds(51, 212, 70, 15);
		contentPane.add(lblFid_1);

		JLabel lblTimestamp = new JLabel("Timestamp");
		lblTimestamp.setBounds(176, 212, 82, 15);
		contentPane.add(lblTimestamp);

		JLabel lblCode = new JLabel("Function code");
		lblCode.setBounds(46, 78, 112, 15);
		contentPane.add(lblCode);

		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					_model.run();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(contentPane, ex);
					ex.printStackTrace();
				}
			}
		});
		btnRun.setBounds(126, 258, 117, 25);
		contentPane.add(btnRun);

		textArgs = new JTextField();
		textArgs.setEnabled(false);
		textArgs.setBounds(269, 227, 69, 19);
		contentPane.add(textArgs);
		textArgs.setColumns(10);

		JLabel lblArgs = new JLabel("Args");
		lblArgs.setBounds(285, 212, 70, 15);
		contentPane.add(lblArgs);

		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					_model.stop();
				}
				catch (IllegalAccessException e)
				{
					JOptionPane.showMessageDialog(contentPane, e);
					e.printStackTrace();
				}
			}
		});
		btnStop.setBounds(263, 258, 117, 25);
		contentPane.add(btnStop);

		JButton btnSaveagent = new JButton("SaveAgent");
		btnSaveagent.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					_model.saveAgent();
					JOptionPane.showMessageDialog(contentPane, "The agent is saved successfully");
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(contentPane, e);
					e.printStackTrace();
				}
			}
		});
		btnSaveagent.setBounds(12, 258, 37, 25);
		contentPane.add(btnSaveagent);

		JButton btnLoadagent = new JButton("LoadAgent");
		btnLoadagent.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					_model.loadAgent();
					JOptionPane.showMessageDialog(contentPane, "All correct...\n" + _model.getFullAgentInfo());
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(contentPane, e);
					e.printStackTrace();
				}
			}
		});
		btnLoadagent.setBounds(59, 258, 37, 25);
		contentPane.add(btnLoadagent);

		textEventMsg = new JTextField();
		textEventMsg.setBounds(296, 298, 69, 19);
		contentPane.add(textEventMsg);
		textEventMsg.setColumns(10);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String strs[] = textEventMsg.getText().trim().split(" ");
				byte data[] = new byte[strs.length];
				for (int i = 0; i < strs.length; i++)
					data[i] = Byte.parseByte(strs[i]);
				MPIAgent.getInstance().send(data, MPI.COMM_WORLD.Rank() == 0 ? 1 : 0);
			}
		});
		btnSend.setBounds(396, 295, 82, 25);
		contentPane.add(btnSend);
		setVisible(true);
		setLocationRelativeTo(null);
	}
}
