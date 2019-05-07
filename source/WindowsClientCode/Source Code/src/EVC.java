import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Label;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class EVC extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EVC frame = new EVC();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EVC() {
		getContentPane().addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 432, 30);
		getContentPane().add(menuBar);
		
		JMenu mnProject = new JMenu("project");
		menuBar.add(mnProject);
		
		JMenuItem mntmCurrentProject = new JMenuItem("Current project");
		mntmCurrentProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
		 
			}
		});
		mnProject.add(mntmCurrentProject);
		
		JMenuItem projecthistory = new JMenuItem("Project history");
		mnProject.add(projecthistory);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnProject.add(mntmExit);
		
		JMenu mnProperties = new JMenu("Properties");
		menuBar.add(mnProperties);
		
		JMenuItem mntmConfiguration = new JMenuItem("Configuration");
		mnProperties.add(mntmConfiguration);
		
		JMenuItem mntmStaticAttributes = new JMenuItem("Static Attributes");
		mnProperties.add(mntmStaticAttributes);
		
		JMenuItem mntmRandomAttributes = new JMenuItem("Random Attributes");
		mnProperties.add(mntmRandomAttributes);
		
		JMenuItem mntmClientPerformance = new JMenuItem("Client performance");
		mnProperties.add(mntmClientPerformance);
		
		JMenuItem mntmLogs = new JMenuItem("Logs");
		mnProperties.add(mntmLogs);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmManual = new JMenuItem("Manual");
		mnHelp.add(mntmManual);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		JLabel lblProjectInformation = new JLabel(" Project Information  ");
		lblProjectInformation.setBounds(20, 43, 153, 30);
		getContentPane().add(lblProjectInformation);
		
		JTextPane txtpnKlhadlhalhsdlhalhdlhlahdshlahdslhalsdlhalhdlkalkdhlahldhlahdl = new JTextPane();
		txtpnKlhadlhalhsdlhalhdlhlahdshlahdslhalsdlhalhdlkalkdhlahldhlahdl.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtpnKlhadlhalhsdlhalhdlhlahdshlahdslhalsdlhalhdlkalkdhlahldhlahdl.setEditable(false);
		txtpnKlhadlhalhsdlhalhdlhlahdshlahdslhalsdlhalhdlkalkdhlahldhlahdl.setText("abt aslk  asdpja;sdj kasdkljaskl kladsfasf \r\ndsfsdfffffffff sdfsdfsfs dsdfsdfsdfsfsdf\r\ndsfsdf sdfsd fs dfdsfsdfdsd dsdsdfsfddfs  ds\r\nkhdslkhlhdslk dsjlhls;lkl klhlln;lsnds \r\nnlklk dsldslds lslnlsn dslndslks ;ds\r\nkjlndlkl kjdslslklsls;ls;j k;ads;k; sk;\r\ndsdsdssdsdsdfssd sddsdsfsddfdsfss\r\nsddfdsk;lhj dklhlhllhlhll ");
		txtpnKlhadlhalhsdlhalhdlhlahdshlahdslhalsdlhalhdlkalkdhlahldhlahdl.setBounds(20, 86, 213, 126);
		getContentPane().add(txtpnKlhadlhalhsdlhalhdlhlahdshlahdslhalsdlhalhdlkalkdhlahldhlahdl);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\index.jpg"));
		lblNewLabel.setBounds(255, 50, 165, 162);
		getContentPane().add(lblNewLabel);
	}
}
