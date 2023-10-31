package implementacoes;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import grafos.*;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private Grafo g;
	private TipoDeRepresentacao tipo;
	private Algoritmos alg;
	private String endArq;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		// Valor do exemplo inicial
		this.endArq = "Teste2.txt";			
		
		setTitle("Simulador de grafos - Bruno Batista");
		setFont(new Font("Arial", Font.PLAIN, 12));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 668);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		
		JTextPane txtEndArq = new JTextPane();
		txtEndArq.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				endArq = txtEndArq.getText();
			}
		});
		txtEndArq.setText(this.endArq);
		txtEndArq.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblNewLabel = new JLabel("Nome do arquivo a ser carregado:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JTextArea txtContArq = new JTextArea();
		
		JButton btnCarregarGrafo = new JButton("Carregar novo grafo");
		btnCarregarGrafo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					g = alg.carregarGrafo(endArq, tipo);
					FileManager file = new FileManager();
					ArrayList<String> entrada = file.stringReader(endArq);
					txtContArq.setText("");
					for (String linha : entrada) 
						txtContArq.setText(txtContArq.getText()+linha+"\n");
				} catch (Exception err) {
					// TODO: handle exception
				}
			}
		});
		btnCarregarGrafo.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JComboBox comboTipo = new JComboBox();
		comboTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipo = (TipoDeRepresentacao) comboTipo.getSelectedItem();
			}
		});
		comboTipo.setModel(new DefaultComboBoxModel(TipoDeRepresentacao.values()));
		comboTipo.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblTipoDeRepresentao = new JLabel("Tipo de representação do grafo:");
		lblTipoDeRepresentao.setFont(new Font("Arial", Font.PLAIN, 12));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(txtEndArq, GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblTipoDeRepresentao, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboTipo, 0, 212, Short.MAX_VALUE))
						.addComponent(btnCarregarGrafo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(txtEndArq, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTipoDeRepresentao, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboTipo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCarregarGrafo)
					.addContainerGap())
		);
		gl_panel.setAutoCreateContainerGaps(true);
		gl_panel.setAutoCreateGaps(true);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		
		JLabel lblNewLabel_1 = new JLabel("Texto carregado do arquivo:");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 12));
		
		
		FileManager file = new FileManager();
		ArrayList<String> entrada = file.stringReader(this.endArq);
		txtContArq.setText("");
		for (String linha : entrada) 
			txtContArq.setText(txtContArq.getText()+linha+"\n");
		txtContArq.setEditable(false);
		txtContArq.setFont(new Font("Arial", Font.PLAIN, 12));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setAutoCreateContainerGaps(true);
		gl_panel_1.setAutoCreateGaps(true);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(txtContArq, GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addContainerGap(111, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(4)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtContArq, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		
		JLabel lblNewLabel_2 = new JLabel("Operações disponíveis:");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton btnNewButton_1 = new JButton("Ver AGM de Krukall");
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton btnNewButton_2 = new JButton("Ver o caminho mínimo entre");
		btnNewButton_2.setFont(new Font("Arial", Font.PLAIN, 12));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JLabel lblNewLabel_2_1 = new JLabel("Vértice 1:");
		lblNewLabel_2_1.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Vértice 2:");
		lblNewLabel_2_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton btnPrintGrafo = new JButton("Visualizar a representação do grafo");
		btnPrintGrafo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: função que mostra o print da representação
			}
		});
		btnPrintGrafo.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton btnNewButton_1_2 = new JButton("Ver as arestas do grafo e seus tipos");
		btnNewButton_1_2.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JSpinner spinner = new JSpinner();
		spinner.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setFont(new Font("Arial", Font.PLAIN, 12));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(btnPrintGrafo)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(btnNewButton_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnNewButton_1, Alignment.LEADING))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel_2_1, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
										.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_2_1_1, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnNewButton_1_2)))
					.addContainerGap(76, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNewLabel_2_1_1)
							.addGap(26))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNewLabel_2)
							.addGap(4)
							.addComponent(btnPrintGrafo)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(btnNewButton_1)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnNewButton_2)
										.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(lblNewLabel_2_1)
									.addGap(26)))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton_1_2)
					.addContainerGap(100, Short.MAX_VALUE))
		);
		gl_panel_2.setAutoCreateContainerGaps(true);
		gl_panel_2.setAutoCreateGaps(true);
		panel_2.setLayout(gl_panel_2);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_2, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(panel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 415, Short.MAX_VALUE)
							.addComponent(panel_1, Alignment.LEADING, 0, 0, Short.MAX_VALUE)))
					.addContainerGap(4, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
					.addGap(54))
		);
		gl_contentPane.setAutoCreateContainerGaps(true);
		gl_contentPane.setAutoCreateGaps(true);
		contentPane.setLayout(gl_contentPane);
		

		// Carregar as informações finais antes de mostrar a tela
		this.tipo = (TipoDeRepresentacao) comboTipo.getSelectedItem();
		this.alg = new Algoritmos();
		try {
			this.g = alg.carregarGrafo(endArq, tipo);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
