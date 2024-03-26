import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClientWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow frame = new ClientWindow();
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
	public ClientWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1010, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCanteenManagementSystem = new JLabel("Order Management System");
		lblCanteenManagementSystem.setFont(new Font("Dialog", Font.BOLD, 20));
		lblCanteenManagementSystem.setBounds(324, 12, 322, 46);
		contentPane.add(lblCanteenManagementSystem);
		
		JButton btnManageMenu = new JButton("Manage menu");
		btnManageMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ManageMenu ManageMenuPage= new ManageMenu();
				ManageMenuPage.setVisible(true);
			}
		});
		btnManageMenu.setBounds(414, 153, 145, 35);
		contentPane.add(btnManageMenu);
		
		JButton btnManageOrder = new JButton("Manage Order");
		btnManageOrder.setBounds(414, 225, 145, 35);
		contentPane.add(btnManageOrder);
		
		JButton btnBackToLogin = new JButton("Back to Login");
		btnBackToLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				LoginPage LogPage= new LoginPage();
				LogPage.setVisible(true);
			}
		});
		btnBackToLogin.setBounds(414, 291, 145, 35);
		contentPane.add(btnBackToLogin);
		
		JLabel backgroundImg = new JLabel("");
		Image img= new ImageIcon(this.getClass().getResource("BG(1).jpg")).getImage();
		backgroundImg.setIcon(new ImageIcon(img));
		backgroundImg.setBounds(0, 0, 1010, 610);
		getContentPane().add(backgroundImg);
	}

}
