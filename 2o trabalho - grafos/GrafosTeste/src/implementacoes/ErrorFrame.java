package implementacoes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;

public class ErrorFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextArea txtErro;

	public void setLblErro(String erro) {
		txtErro.setText(erro);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ErrorFrame dialog = new ErrorFrame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ErrorFrame() {
		setTitle("Erro na execução:");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setFont(new Font("Arial", Font.PLAIN, 12));
		setBounds(100, 100, 450, 122);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		txtErro = new JTextArea();
		txtErro.setBackground(SystemColor.menu);
		txtErro.setFont(new Font("Arial", Font.PLAIN, 12));
		txtErro.setText("** texto do erro vai aqui **");
		txtErro.setEditable(false);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(txtErro, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(txtErro, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
		);
		contentPanel.setLayout(gl_contentPanel);
	}
}
