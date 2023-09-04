package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;
import utils.Validador;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("unused")
public class Login extends JFrame {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	Principal principal = new Principal();

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JButton btnAcessar;
	private JLabel lblStatus;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/carwash64.png")));
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 264);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(30, 32, 39, 14);
		contentPane.add(lblNewLabel);

		txtLogin = new JTextField();
		txtLogin.setBounds(75, 29, 201, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);
	
		txtLogin.setDocument(new Validador(15));

		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(25, 69, 46, 14);
		contentPane.add(lblNewLabel_1);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(75, 66, 201, 20);
		
		txtSenha.setDocument(new Validador(250));
		contentPane.add(txtSenha);

		btnAcessar = new JButton("Acessar");
		btnAcessar.setForeground(new Color(0, 0, 0));
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnAcessar.setBounds(25, 111, 89, 23);
		contentPane.add(btnAcessar);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Login.class.getResource("/img/carwash64.png")));
		lblLogo.setBounds(310, 145, 75, 75);
		contentPane.add(lblLogo);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dbloff.png")));
		lblStatus.setBounds(306, 32, 48, 48);
		contentPane.add(lblStatus);

		
		getRootPane().setDefaultButton(btnAcessar);

	}

	
	private void status() {
		try {
			
			con = dao.conectar();
			if (con == null) {
				
				lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dbloff.png")));
			} else {
				
				lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dblon.png")));
			}
			
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
	private void logar() {
		
		String capturaSenha = new String(txtSenha.getPassword());
		
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			txtSenha.requestFocus();
		} else {
			
			String read = "select * from usuarios where login=? and senha=md5(?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturaSenha);
				rs = pst.executeQuery();
				if (rs.next()) {
					
					String perfil = rs.getString(5);
					if (perfil.equals("admin")) {
						
						principal.setVisible(true);
						
						principal.lblUsuario.setText(rs.getString(2));
						
						principal.btnRelatorios.setEnabled(true);
						principal.btnUsuarios.setEnabled(true);
						
						this.dispose();
						
					} else {
						
						principal.setVisible(true);
						
						principal.panelRodape.setBackground(Color.DARK_GRAY);
						
						principal.lblUsuario.setText(rs.getString(2));
						
						this.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "usuário e/ou senha inválido(s)");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}