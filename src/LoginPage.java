
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.SystemColor;

public class LoginPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	public String client_id;
	public String client_cantname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection=null;

	/**
	 * Create the frame.
	 */
	public LoginPage() {
		getContentPane().setForeground(SystemColor.text);
		getContentPane().setBackground(SystemColor.window);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1010, 610);
		getContentPane().setLayout(null);
		
		connection=PostgresConn.dbConnection();
		
		final String emailPattern="^[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+[.]+[a-zA-Z0-9]+$";
		
		final JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		panel.setBounds(60, 104, 367, 257);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblUserLogin = new JLabel("User LogIn");
		lblUserLogin.setForeground(SystemColor.text);
		lblUserLogin.setBounds(125, 27, 141, 24);
		panel.add(lblUserLogin);
		lblUserLogin.setFont(new Font("Dialog", Font.BOLD, 20));
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(SystemColor.text);
		lblUsername.setBounds(42, 98, 95, 24);
		panel.add(lblUsername);
		lblUsername.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(SystemColor.text);
		lblPassword.setBounds(42, 134, 95, 24);
		panel.add(lblPassword);
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 14));
		
		final JTextField username = new JTextField();
		username.setBounds(142, 99, 183, 24);
		panel.add(username);
		username.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(142, 134, 183, 24);
		panel.add(passwordField);
		
		JButton btnLogin = new JButton("LogIn");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user_email = username.getText();
                String user_pass = (passwordField.getText());
                if (user_email.matches(emailPattern))
                {
                	try {
                    	Connection connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");

                        String query = "SELECT * FROM user_reg WHERE user_email = ? AND user_pass = ?";

                        PreparedStatement statement = connection.prepareStatement(query);

                        statement.setString(1, user_email);

                        statement.setString(2, user_pass);
                        
                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                        	SearchWindow sw=new SearchWindow();
                        	JOptionPane.showMessageDialog(null, "LogIn Successful");
                        	dispose();
            				sw.setVisible(true);
                        } 
                        else 
                        {
                        	JOptionPane.showMessageDialog(null,"Invalid username or password");
                        }
                        statement.close();
                        connection.close();
                    } catch (SQLException e1) 
                    {
                        e1.printStackTrace();
                    }
                }
                else if(!user_email.matches(emailPattern))
                {
                	JOptionPane.showMessageDialog(null, "Email pattern is incorrect");
                }
			}
		});
		btnLogin.setBounds(135, 192, 117, 25);
		panel.add(btnLogin);
		
		final JPanel panel_1 = new JPanel();
		panel_1.setVisible(false);
		panel_1.setBackground(new Color(0, 0, 0));
		panel_1.setLayout(null);
		panel_1.setBounds(60, 104, 367, 257);
		getContentPane().add(panel_1);
		
		JLabel lblClientLogin = new JLabel("Client LogIn");
		lblClientLogin.setForeground(SystemColor.text);
		lblClientLogin.setFont(new Font("Dialog", Font.BOLD, 20));
		lblClientLogin.setBounds(119, 29, 146, 24);
		panel_1.add(lblClientLogin);
		
		JLabel lblUsername_1 = new JLabel("Username:");
		lblUsername_1.setForeground(SystemColor.text);
		lblUsername_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblUsername_1.setBounds(42, 97, 95, 24);
		panel_1.add(lblUsername_1);
		
		JLabel lblPassword_1 = new JLabel("Password:");
		lblPassword_1.setForeground(SystemColor.text);
		lblPassword_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPassword_1.setBounds(42, 133, 95, 24);
		panel_1.add(lblPassword_1);
		
		final JTextField username_1 = new JTextField();
		username_1.setColumns(10);
		username_1.setBounds(142, 98, 183, 24);
		panel_1.add(username_1);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(142, 133, 183, 24);
		panel_1.add(passwordField_1);
		
		JButton btnLogin_1 = new JButton("LogIn");
		btnLogin_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String client_email = username_1.getText();
                String client_pass = passwordField_1.getText();
                if (client_email.matches(emailPattern))
                {
                	try {
                    	Connection connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");

                        String query = "SELECT * FROM client_reg WHERE client_email = ? AND client_pass = ?";

                        PreparedStatement statement = connection.prepareStatement(query);

                        statement.setString(1, client_email);

                        statement.setString(2, client_pass);
                        
                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                        	JOptionPane.showMessageDialog(null, "LogIn Successful");
                        	dispose();
            				ClientWindow ClientPage= new ClientWindow();
            				ClientPage.setVisible(true);
                        } 
                        else 
                        {
                        	JOptionPane.showMessageDialog(null,"Invalid username or password");
                        }
                        statement.close();
                        connection.close();
                    } catch (SQLException e1) 
                    {
                        e1.printStackTrace();
                    }
                }
                else if(!client_email.matches(emailPattern))
                {
                	JOptionPane.showMessageDialog(null, "Email pattern is incorrect");
                }
			}
		});
		btnLogin_1.setBounds(133, 192, 117, 25);
		panel_1.add(btnLogin_1);
		
		JButton btnClientLoginPage = new JButton("Goto Client LogIn");
		btnClientLoginPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(panel.isVisible()) {
					panel.setVisible(false);
					panel_1.setVisible(true);
				}
			}
		});
		btnClientLoginPage.setBounds(269, 385, 158, 25);
		getContentPane().add(btnClientLoginPage);
		
		JButton btnUserLoginPage = new JButton("Goto User LogIn");
		btnUserLoginPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(panel_1.isVisible()) {
					panel_1.setVisible(false);
					panel.setVisible(true);
				}
			}
		});
		btnUserLoginPage.setBounds(60, 385, 150, 25);
		getContentPane().add(btnUserLoginPage);
		
		JButton btnRegister = new JButton("Goto to Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				RegistrationPage RegPage= new RegistrationPage();
				RegPage.setVisible(true);
			}
		});
		btnRegister.setBounds(173, 439, 158, 25);
		getContentPane().add(btnRegister);
		
		JLabel backgroundImg = new JLabel("");
		Image img= new ImageIcon(this.getClass().getResource("bg1.jpg")).getImage();
		backgroundImg.setIcon(new ImageIcon(img));
		backgroundImg.setBounds(0, 0, 1010, 610);
		getContentPane().add(backgroundImg);
	}
}