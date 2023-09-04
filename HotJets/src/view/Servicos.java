package view;

import java.awt.EventQueue;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.DAO;
import utils.Validador;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.Font;

public class Servicos extends JDialog {
	
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtOS;
	private JTextField txtData;
	private JTextField txtTipo;
	private JTextField txtAdd;
	private JButton btnEditar;
	private JButton btnAdicionar;
	private JButton btnExcluir;
	private JButton btnBuscar;
	private JButton btnLimpar;
	private JTextField txtCliente;
	private JTextField txtID;
	private JList<String> listCliente;
	private JScrollPane scrollPaneCliente;
	private JLabel lblNewLabel;
	private JTextField txtValor;
	private JButton btnOS;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servicos dialog = new Servicos();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Servicos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Servicos.class.getResource("/img/carwash64.png")));
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneCliente.setVisible(false);
			}
		});
		setTitle("Serviços ");
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);
		
		btnOS = new JButton("");
		btnOS.setBorderPainted(false);
		btnOS.setContentAreaFilled(false);
		btnOS.setIcon(new ImageIcon(Servicos.class.getResource("/img/os64.png")));
		btnOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnOS.setBounds(343, 502, 48, 48);
		getContentPane().add(btnOS);
		
		JLabel lblOS = new JLabel("OS:");
		lblOS.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOS.setBounds(88, 80, 46, 14);
		getContentPane().add(lblOS);
		
		JLabel lblData = new JLabel("Data:");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblData.setBounds(88, 145, 46, 14);
		getContentPane().add(lblData);
		
		JLabel lblBrinquedo = new JLabel("Tipo de Lavagem:");
		lblBrinquedo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBrinquedo.setBounds(55, 233, 114, 41);
		getContentPane().add(lblBrinquedo);
		
		JLabel lblDefeito = new JLabel("Adicional:");
		lblDefeito.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDefeito.setBounds(89, 285, 80, 14);
		getContentPane().add(lblDefeito);
		
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValor.setBounds(90, 342, 46, 14);
		getContentPane().add(lblValor);
		
		txtOS = new JTextField();
		txtOS.setEditable(false);
		txtOS.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtOS.setBounds(119, 79, 86, 26);
		getContentPane().add(txtOS);
		txtOS.setColumns(10);
		
		txtData = new JTextField();
		txtData.setEditable(false);
		txtData.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtData.setBounds(129, 142, 132, 26);
		getContentPane().add(txtData);
		txtData.setColumns(10);
		
		txtTipo = new JTextField();
		txtTipo.setBounds(175, 238, 370, 26);
		getContentPane().add(txtTipo);
		txtTipo.setColumns(10);
		
		txtAdd = new JTextField();
		txtAdd.setBounds(175, 282, 370, 26);
		getContentPane().add(txtAdd);
		txtAdd.setColumns(10);
		txtAdd.setDocument(new Validador(200));
		
		btnAdicionar = new JButton("");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setIcon(new ImageIcon(Servicos.class.getResource("/img/adicionar.png")));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setBounds(145, 502, 48, 48);
		getContentPane().add(btnAdicionar);
		
		btnEditar = new JButton("");
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setIcon(new ImageIcon(Servicos.class.getResource("/img/editar.png")));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarServico();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setBounds(244, 502, 48, 48);
		getContentPane().add(btnEditar);
		
		btnExcluir = new JButton("");
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setIcon(new ImageIcon(Servicos.class.getResource("/img/excluir.png")));
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirServico();
			}
		});
		btnExcluir.setBounds(447, 502, 48, 48);
		getContentPane().add(btnExcluir);
		
		btnBuscar = new JButton("");
		btnBuscar.setBorderPainted(false);
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setIcon(new ImageIcon(Servicos.class.getResource("/img/search.png")));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(216, 77, 48, 48);
		getContentPane().add(btnBuscar);
		
		btnLimpar =new JButton("");
		btnLimpar.setBorderPainted(false);
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setIcon(new ImageIcon(Servicos.class.getResource("/img/eraser.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setBounds(540, 502, 48, 48);
		getContentPane().add(btnLimpar);
		
		getRootPane().setDefaultButton(btnBuscar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(377, 82, 230, 93);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		scrollPaneCliente = new JScrollPane();
		scrollPaneCliente.setVisible(false);
		scrollPaneCliente.setBounds(9, 47, 210, 32);
		panel.add(scrollPaneCliente);
		
		listCliente = new JList<String>();
		listCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarCliente();
			}
		});
		scrollPaneCliente.setViewportView(listCliente);
		
		txtCliente = new JTextField();
		txtCliente.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarCliente();
			}
		});
		txtCliente.setBounds(10, 21, 210, 26);
		panel.add(txtCliente);
		txtCliente.setColumns(10);
		
		txtID = new JTextField();
		txtID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtID.setEditable(false);
		txtID.setBounds(34, 52, 86, 20);
		panel.add(txtID);
		txtID.setColumns(10);
		
		JLabel lblID = new JLabel("ID:");
		lblID.setBounds(10, 55, 46, 14);
		panel.add(lblID);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(new Color(255, 128, 255));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBounds(0, 486, 784, 75);
		getContentPane().add(lblNewLabel);
		
		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtValor.setBounds(132, 339, 86, 26);
		getContentPane().add(txtValor);
		txtValor.setColumns(10);

	}
	
	
	/**
	 * METODO LIMPAR CAMPOS
	 */
	private void limparCampos() {
		txtData.setText(null);
		txtAdd.setText(null);
		txtTipo.setText(null);
		txtID.setText(null);
		txtOS.setText(null);
		txtValor.setText(null);
		btnBuscar.setEnabled(true);
		txtCliente.setText(null);		
	}
	
	/**
	 * METODO BUSCAR
	 */
	private void buscar() {
		
		
		String numOS = JOptionPane.showInputDialog("Número da OS");
		
		
		String read = "select * from servicos where os = ?";
		
		try {
			
			con = dao.conectar();

			
			pst = con.prepareStatement(read);
			
			pst.setString(1, numOS);
			
			rs = pst.executeQuery();
			
			if (rs.next()) {
				txtOS.setText(rs.getString(1));
				txtData.setText(rs.getString(2));
				txtTipo.setText(rs.getString(3)); 
				txtAdd.setText(rs.getString(4));
				txtValor.setText(rs.getString(5));
				txtID.setText(rs.getString(6));
				
				
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
			} else {
				
				JOptionPane.showMessageDialog(null, "serviço inexistente");
				btnAdicionar.setEnabled(true);
				
			}

		} catch (Exception e) {
			System.out.print(e);
		}
	}
	
	private void adicionar() {
		
		if  (txtTipo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Tipo de Lavagem do Cliente");
			txtTipo.requestFocus();
		}else if(txtAdd.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Adicional da Lavagem do Cliente");
			txtAdd.requestFocus();
		} else {
			
			
			String create = "insert into servicos (tipo,adicional,valor,idcli) value (?, ?, ?, ?)";
			
			try {
				 
				con = dao.conectar();
				
				pst = con.prepareStatement(create);
				pst.setString(1, txtTipo.getText());
				pst.setString(2, txtAdd.getText());
				pst.setString(3, txtValor.getText());
				pst.setString(4, txtID.getText());
				
				
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Ordem de serviço adicionado");  
				limparCampos();
				
			} catch (Exception e) {
				System.out.print(e);
			}
			}
	}
	private void excluirServico() {
		

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste serviço ?", "Atenção !",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_NO_OPTION) {
			
			String delete = "delete from servicos where OS=?";
			
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(delete);
				
				pst.setString(1, txtOS.getText());
				
				pst.executeUpdate();
				
				limparCampos();
				
				JOptionPane.showMessageDialog(null, " Serviço excluido");
				
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	private void editarServico() {
		

		
		if (txtTipo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Tipo de Lavagem do Serviço");
			txtTipo.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Valor de Serviço");
			txtValor.requestFocus();
		} else if (txtAdd.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Adicional do Serviço");
			txtAdd.requestFocus();
		} else {

			
			String update = "update servicos set os=?, tipo=?, adicional=?, valor=? where idcli=?";
			
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(update);
				pst.setString(1, txtOS.getText());
				pst.setString(2, txtTipo.getText());
				pst.setString(3, txtAdd.getText());
				pst.setString(4, txtValor.getText());
				pst.setString(5, txtID.getText());
				
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Dados do serviço editado com sucesso");
				
				limparCampos();
				
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	private void listarCliente() {
		
		DefaultListModel<String> modelo = new DefaultListModel<>();
		
		listCliente.setModel(modelo);
		
		String readLista = "select* from clientes where nome like '" + txtCliente.getText() + "%'" + "order by nome";
		try {
			
			con = dao.conectar();

			pst = con.prepareStatement(readLista);

			rs = pst.executeQuery();

			
			while (rs.next()) {
				
				scrollPaneCliente.setVisible(true);
				
				modelo.addElement(rs.getString(2));
				
				if (txtCliente.getText().isEmpty()) {
					scrollPaneCliente.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	private void buscarCliente() {
		
		int linha = listCliente.getSelectedIndex();
		if (linha >= 0) {
			
			String readListaUsuario =  "select * from clientes where nome like '" + txtCliente.getText() + "%'" + "order by nome";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {
					
					scrollPaneCliente.setVisible(false);
					
					txtID.setText(rs.getString(1));
					txtCliente.setText(rs.getString(2));
				}

			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			
			scrollPaneCliente.setVisible(false);
		}
	}
	
	
	private void imprimirOS() {
		
		Document document = new Document();
		
		if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o ID do Cliente");
			txtID.requestFocus();
		} else if (txtOS.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Selecione a OS");
			txtOS.requestFocus();
		}
		else {
		try {
			
			PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
			
			document.open();
			String readOS = "Select * from servicos where os = ?";
			
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(readOS);
				pst.setString(1, txtOS.getText());
				
				rs = pst.executeQuery();
				
				if (rs.next()) {					
					
					Paragraph os = new Paragraph ("OS: " + rs.getString(1));
					os.setAlignment(Element.ALIGN_RIGHT);
					document.add(os);
						
					Paragraph usuario = new Paragraph ("Tipo de Lavagem: " + rs.getString(3));
					usuario.setAlignment(Element.ALIGN_LEFT);
					document.add(usuario);
					
					Paragraph defeito = new Paragraph ("Adicional: " + rs.getString(4));
					defeito.setAlignment(Element.ALIGN_LEFT);
					document.add(defeito);
					
					Paragraph data = new Paragraph ("Data: " + rs.getString(2));
					data.setAlignment(Element.ALIGN_LEFT);
					document.add(data);
					
					Paragraph valor = new Paragraph ("Valor: " + rs.getString(6));
					valor.setAlignment(Element.ALIGN_LEFT);
					document.add(valor);
				
				
				
					
					Image imagem = Image.getInstance(Servicos.class.getResource("/img/carwash128.png"));
					imagem.scaleToFit(192,148);
					imagem.setAbsolutePosition(20, 300);
					document.add(imagem);					
				}
				
				con.close();
				} catch (Exception e) {
					System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		document.close();
		
		try {
			Desktop.getDesktop().open(new File("os.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	}
}