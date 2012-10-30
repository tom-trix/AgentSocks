package ru.tomtrix.agentsocks.ui;

import java.awt.event.*;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

public class GUI extends JFrame
{
	private static final long	serialVersionUID	= 6208515297698018657L;
	private final Controller	_controller			= new Controller();

	private JPanel				contentPane;
	private JTextField			textAgentName;
	private JTextField			textVarName;
	private JTextField			textEventFid;
	private JTextField			textFidName;
	private JTextField			textTimestamp;
	private JTextField			textInitValue;
	private JTextField textArgs;

	/** Launch the application. */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					GUI frame = new GUI();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				}
				catch (Exception e)
				{
					Logger.getLogger(getClass()).error("Can't run user interface", e);
				}
			}
		});
	}

	/** Create the frame. */
	public GUI()
	{
		setTitle("Modelling system");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 370);
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
					JOptionPane.showMessageDialog(contentPane, _controller.createAgent(s));
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
				String initVal = textInitValue.getText().trim();
				if (var.isEmpty() || initVal.isEmpty()) return;
				try
				{
					Integer.parseInt(initVal);
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(contentPane, "Initial value must be an integer");
				}
				JOptionPane.showMessageDialog(contentPane, _controller.addVariable(var, Integer.parseInt(initVal)));
			}
		});
		btnAddVariable.setBounds(350, 44, 128, 25);
		contentPane.add(btnAddVariable);

		final JTextArea textCode = new JTextArea();
		textCode.setBounds(12, 92, 542, 128);
		contentPane.add(textCode);

		JButton btnAddFunction = new JButton("Add function");
		btnAddFunction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String fid = textFidName.getText().trim();
				String code = textCode.getText().trim();
				if (fid.isEmpty() || code.isEmpty()) return;
				JOptionPane.showMessageDialog(contentPane, _controller.addFunction(code));
			}
		});
		btnAddFunction.setBounds(350, 235, 128, 25);
		contentPane.add(btnAddFunction);

		textEventFid = new JTextField();
		textEventFid.setBounds(36, 278, 122, 19);
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
				JOptionPane.showMessageDialog(contentPane, _controller.addEvent(fid, time));
			}
		});
		btnAddEvent.setBounds(350, 272, 128, 25);
		contentPane.add(btnAddEvent);

		textFidName = new JTextField();
		textFidName.setBounds(36, 241, 122, 19);
		contentPane.add(textFidName);
		textFidName.setColumns(10);

		textTimestamp = new JTextField();
		textTimestamp.setBounds(183, 278, 69, 19);
		contentPane.add(textTimestamp);
		textTimestamp.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(46, 0, 70, 15);
		contentPane.add(lblName);

		JLabel lblVariable = new JLabel("Variable");
		lblVariable.setBounds(46, 37, 70, 15);
		contentPane.add(lblVariable);

		JLabel lblFid = new JLabel("Fid");
		lblFid.setBounds(51, 227, 70, 15);
		contentPane.add(lblFid);

		JLabel lblFid_1 = new JLabel("Fid");
		lblFid_1.setBounds(51, 263, 70, 15);
		contentPane.add(lblFid_1);

		JLabel lblTimestamp = new JLabel("Timestamp");
		lblTimestamp.setBounds(176, 263, 82, 15);
		contentPane.add(lblTimestamp);

		JLabel lblCode = new JLabel("Code");
		lblCode.setBounds(46, 78, 70, 15);
		contentPane.add(lblCode);

		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					_controller.run();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(contentPane, ex);
					ex.printStackTrace();
				}
			}
		});
		btnRun.setBounds(229, 309, 117, 25);
		contentPane.add(btnRun);

		textInitValue = new JTextField();
		textInitValue.setBounds(183, 50, 69, 19);
		contentPane.add(textInitValue);
		textInitValue.setColumns(10);

		JLabel lblInitialValue = new JLabel("Initial Value");
		lblInitialValue.setBounds(176, 37, 91, 15);
		contentPane.add(lblInitialValue);
		
		textArgs = new JTextField();
		textArgs.setEnabled(false);
		textArgs.setBounds(269, 278, 69, 19);
		contentPane.add(textArgs);
		textArgs.setColumns(10);
		
		JLabel lblArgs = new JLabel("Args");
		lblArgs.setBounds(285, 263, 70, 15);
		contentPane.add(lblArgs);
	}
}
