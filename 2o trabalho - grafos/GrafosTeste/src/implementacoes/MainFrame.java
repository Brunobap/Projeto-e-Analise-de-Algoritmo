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
import javax.swing.JComponent;
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
import java.awt.Panel;
import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import java.awt.Window.Type;
import javax.swing.JCheckBox;
import javax.swing.SpinnerNumberModel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private Grafo g;
	private TipoDeRepresentacao tipo;
	private Algoritmos alg;
	private String endArq;
	/**
	 * @wbp.nonvisual location=498,39
	 */
	private final ErrorFrame errorFrame = new ErrorFrame();
	/**
	 * @wbp.nonvisual location=494,89
	 */
	private final ResultFrame resultFrame = new ResultFrame();

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
		errorFrame.setType(Type.UTILITY);
		errorFrame.getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
		// Valor do exemplo inicial
		this.endArq = "Teste.txt";			
		
		setTitle("Simulador de grafos - Bruno Batista");
		setFont(new Font("Arial", Font.PLAIN, 12));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 779);
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
					errorFrame.setLblErro("Erro ao carregar o arquivo especificado.\nCertifique-se de que o nome/endereço dele foi digitado corretamente.");
					errorFrame.setVisible(true);
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
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					resultFrame.setTitle("\"Ver AGM de Krukall\"");
					ArrayList<Aresta> AGM = (ArrayList<Aresta>) alg.agmUsandoKruskall(g);
					String result = "Arestas da árvore mínima gerada:\n";
					for (Aresta a : AGM)
						result += "   "+a.origem().id()+" --"+a.peso()+"-> "+a.destino().id()+"\n";
					
					result += "\n\nCusto da AGM: "+alg.custoDaArvoreGeradora(g, AGM);
					resultFrame.setTxtResult(result);
					resultFrame.setVisible(true);
				} catch (Exception err) {
					
				}
			}
		});
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 12));		

		JCheckBox chckPeso = new JCheckBox("Usar o peso das arestas para o caminho");

		JSpinner numOri = new JSpinner();
		numOri.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		numOri.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JSpinner numDest = new JSpinner();
		numDest.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		numDest.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton btnNewButton_2 = new JButton("Ver o caminho mínimo entre");
		btnNewButton_2.setFont(new Font("Arial", Font.PLAIN, 12));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					resultFrame.setTitle("\"Ver o caminho mínimo entre\"");
					ArrayList<Aresta> caminho;
					String result = "Arestas do caminho:\n";
					int nOri = Integer.parseInt(numOri.getValue().toString());
					int nDest = Integer.parseInt(numDest.getValue().toString());
					Vertice ori = g.vertices().get(nOri), dest = g.vertices().get(nDest);
					
					// Se é pra usar os pesos
					if (chckPeso.isSelected()) {
						result += " * considerando o peso das arestas\n\n";
						caminho = alg.caminhoMaisCurto(g, ori, dest);
					} else {
						result += " * NÃO considerando o peso das arestas\n\n";
						caminho = alg.menorCaminho(g, ori, dest);
					}
					
					if (caminho == null) result += "   ** Não há nenhum caminho entre esses 2 vértices **";
					else {
						for (Aresta a : caminho)
							result += "   "+a.origem().id()+" -"+a.peso()+"-> "+a.destino().id()+'\n';
						
						result += "\nPeso do caminho percorrido: "+alg.custoDoCaminho(g, caminho, ori, dest);
					}
					resultFrame.setTxtResult(result);
					resultFrame.setVisible(true);
					
				} catch (Exception err) {
					errorFrame.setLblErro("Erro ao procurar os vértices.\nCertifique-se de que ambos os vértices digitados pertençam ao\nintervalo do grafo.");
					errorFrame.setVisible(true);
				}
			}
		});
		
		JLabel lblNewLabel_2_1 = new JLabel("De:");
		lblNewLabel_2_1.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Para:");
		lblNewLabel_2_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton btnPrintGrafo = new JButton("Visualizar a representação do grafo");
		btnPrintGrafo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultFrame.setTitle("\"Visualizar a representação do grafo\"");
				String representacao = g.toString();				

				representacao += "\n\nO grafo possui "+g.numeroDeVertices()+" vértices e "+g.numeroDeArestas()+" arestas atualmente.";
				
				resultFrame.setTxtResult(representacao);
				resultFrame.setVisible(true);
			}
		});
		btnPrintGrafo.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton btnNewButton_1_2 = new JButton("Ver as arestas do grafo e seus tipos");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultFrame.setTitle("\"Ver as arestas do grafo e seus tipos\"");
				
				ArrayList<Aresta> aresAva, aresArv, aresRet, aresCruz;
				aresAva = (ArrayList<Aresta>) alg.arestasDeAvanco(g);
				aresArv = (ArrayList<Aresta>) alg.arestasDeArvore(g);
				aresRet = (ArrayList<Aresta>) alg.arestasDeRetorno(g);
				aresCruz = (ArrayList<Aresta>) alg.arestasDeCruzamento(g);
				String result = "Arestas de árvore:\n";
				for (Aresta a : aresArv)
					result += "   "+a.origem().id()+" --"+a.peso()+"-> "+a.destino().id()+'\n';
				
				result += "\n\nArestas de avanço:\n";
				for (Aresta a : aresAva)
					result += "   "+a.origem().id()+" --"+a.peso()+"-> "+a.destino().id()+'\n';
				
				result += "\n\nArestas de retorno:\n";
				for (Aresta a : aresRet)
					result += "   "+a.origem().id()+" --"+a.peso()+"-> "+a.destino().id()+'\n';
				
				result += "\n\nArestas de cruzamento:\n";
				for (Aresta a : aresCruz)
					result += "   "+a.origem().id()+" --"+a.peso()+"-> "+a.destino().id()+'\n';
				
				result += "\n\nO grafo possui "+g.numeroDeArestas()+" arestas atualmente.";
				
				resultFrame.setTxtResult(result);
				resultFrame.setVisible(true);
			}
		});
		btnNewButton_1_2.setFont(new Font("Arial", Font.PLAIN, 12));
				
		chckPeso.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JSpinner numPesoAdd = new JSpinner();
		numPesoAdd.setModel(new SpinnerNumberModel(Double.valueOf(0), Double.valueOf(0), null, Double.valueOf(1)));
		numPesoAdd.setEnabled(false);
		
		JCheckBox chckbxAdicionarUmPeso = new JCheckBox("Adicionar um peso a aresta");
		chckbxAdicionarUmPeso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxAdicionarUmPeso.isSelected()) numPesoAdd.setEnabled(true);
				else  numPesoAdd.setEnabled(false);
			}
		});
		chckbxAdicionarUmPeso.setFont(new Font("Arial", Font.PLAIN, 12));

		JSpinner numOriAdd = new JSpinner();
		numOriAdd.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		numOriAdd.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JSpinner numDestAdd = new JSpinner();
		numDestAdd.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		numDestAdd.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton btnNewButton_2_1 = new JButton("Adicionar aresta no grafo");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					resultFrame.setTitle("\"Adicionar aresta no grafo\"");
					int nOri = (int) numOriAdd.getValue(), nDest = (int) numDestAdd.getValue();
					Vertice ori = g.vertices().get(nOri), dest = g.vertices().get(nDest);
					
					if (chckbxAdicionarUmPeso.isSelected()) g.adicionarAresta(ori, dest, (double)numPesoAdd.getValue());
					else g.adicionarAresta(ori, dest);
					
					String result = "Arestas salvas atualmente:\n\n";
					for (Aresta a : g.getArrayAres())
						result += "   "+a.origem().id()+" -"+a.peso()+"-> "+a.destino().id()+'\n';
					result += "     /\\\n     |\n     |\n  nova aresta";
					
					result += "\n\nO grafo possui "+g.numeroDeArestas()+" arestas atualmente.";
					
					resultFrame.setTxtResult(result);
					resultFrame.setVisible(true);
					
				} catch (Exception err) {
					errorFrame.setLblErro("Erro ao criar uma aresta nova.\nCertifique-se de que ambos os vértices pertençam ao intervalo do grafo, e\nque o peso digitado é um número válido.");
					errorFrame.setVisible(true);
				}
			}
		});
		btnNewButton_2_1.setFont(new Font("Arial", Font.PLAIN, 12));
		
		
		JLabel lblNewLabel_2_1_1_1 = new JLabel("Para:");
		lblNewLabel_2_1_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblNewLabel_2_1_2 = new JLabel("De:");
		lblNewLabel_2_1_2.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblNewLabel_2_1_1_1_1 = new JLabel("Peso:");
		lblNewLabel_2_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		
		numPesoAdd.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton btnNewButton_2_1_1 = new JButton("Adicionar vértice entre");
		btnNewButton_2_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JSpinner numOriAdd_1 = new JSpinner();
		numOriAdd_1.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblNewLabel_2_1_2_1 = new JLabel("De:");
		lblNewLabel_2_1_2_1.setFont(new Font("Arial", Font.PLAIN, 12));

		JSpinner numVertGrau = new JSpinner();
		numVertGrau.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		numVertGrau.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton btnNewButton_2_1_2 = new JButton("Ver grau e adjacentes do vértice");
		btnNewButton_2_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					resultFrame.setTitle("\"Ver grau e adjacentes do vértice:\"");
					
					int nVert = (int)numVertGrau.getValue(), grau;
					Vertice alvo = g.vertices().get(nVert);
					
					grau = g.grauDoVertice(alvo);
					String result = "O vértice "+nVert+" é do grau "+grau+"\nEle é adjacente a:";
					
					if (grau!=0) for (Vertice v : g.adjacentesDe(alvo)) result += "  "+v.id();
					else result += "  **ninguém**";
					
					resultFrame.setTxtResult(result);
					resultFrame.setVisible(true);
					
				} catch (Exception err) {
					errorFrame.setLblErro("Erro ao encontrar o vértice.\nCertifique-se de que o vértice digitado pertença ao intervalo do grafo.");
					errorFrame.setVisible(true);
				}
			}
		});
		btnNewButton_2_1_2.setFont(new Font("Arial", Font.PLAIN, 12));

		JSpinner numOriSet = new JSpinner();
		numOriSet.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		numOriSet.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JSpinner numPesoSet = new JSpinner();
		numPesoSet.setModel(new SpinnerNumberModel(Double.valueOf(0), Double.valueOf(0), null, Double.valueOf(1)));
		numPesoSet.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JSpinner numDestSet = new JSpinner();
		numDestSet.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		numDestSet.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton btnNewButton_2_1_3 = new JButton("Setar peso da aresta");
		btnNewButton_2_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					resultFrame.setTitle("\"Setar peso da aresta\"");
					int nOri = (int) numOriSet.getValue(), nDest = (int) numDestSet.getValue();
					double peso = (double) numPesoSet.getValue();
					Vertice ori = g.vertices().get(nOri), dest = g.vertices().get(nDest);
					Aresta alvo = g.arestasEntre(ori, dest).get(0);
					
					String result = "O peso da aresta foi alterado com sucesso.\n\n  Peso anterior: "+alvo.peso();
					g.setarPeso(ori, dest, peso);
					result += "\n  Peso atual: "+alvo.peso();
					
					resultFrame.setTxtResult(result);
					resultFrame.setVisible(true);
					
				} catch (Exception err) {
					errorFrame.setLblErro("Erro ao mudar o valor da aresta.\nCertifique-se de que ambos os vértices digitados pertençam ao intervalo do\ngrafo, e que eles possuam pelo menos uma aresta entre si.");
					errorFrame.setVisible(true);
				}
			}
		});
		btnNewButton_2_1_3.setFont(new Font("Arial", Font.PLAIN, 12));

		JLabel lblNewLabel_2_1_2_2 = new JLabel("De:");
		lblNewLabel_2_1_2_2.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblNewLabel_2_1_1_1_2 = new JLabel("Para:");
		lblNewLabel_2_1_1_1_2.setFont(new Font("Arial", Font.PLAIN, 12));
		
		
		JLabel lblNewLabel_2_1_1_1_1_1 = new JLabel("Peso:");
		lblNewLabel_2_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblNewLabel_3 = new JLabel("* Atenção: caso hajam arestas paralelas, só a 1ª salva será afetada.");
		lblNewLabel_3.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton btnNewButton_2_1_4 = new JButton("Fazer busca em largura");
		btnNewButton_2_1_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultFrame.setTitle("\"Fazer busca em largura\"");
				ArrayList<Aresta> buscadas = (ArrayList<Aresta>) alg.buscaEmLargura(g);
				String result = "Resultados da busca em largura:\n\nVértices:";
				
				for (Vertice v : g.vertices()) {
					result += "   "+v.id()+" - Cor: "+v.getCor()+" - Distancia da origem: "+v.getDist()+ " - Pai do vértice: ";
					if (v.getPi() != null) result += v.getPi().id();
					else result += "null";
					result += '\n';
				}
				
				result += "\n\nArestas:\n";
				for (Aresta a : buscadas)
					result += "   "+a.origem().id()+" ---> "+a.destino().id()+'\n';

				resultFrame.setTxtResult(result);
				resultFrame.setVisible(true);
			}
		});
		btnNewButton_2_1_4.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton btnNewButton_2_1_4_1 = new JButton("Fazer busca em profundidade");
		btnNewButton_2_1_4_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultFrame.setTitle("\"Fazer busca em profundidade\"");
				ArrayList<Aresta> buscadas = (ArrayList<Aresta>) alg.buscaEmProfundidade(g);
				String result = "Resultados da busca em profundidade:\n\nVértices:";
				
				for (Vertice v : g.vertices()) {
					result += "   "+v.id()+" - Cor: "+v.getCor()+"  -  Tempo de descoberta: "+v.getD()+ "  -  Tempo de finalização: "+v.getF()+'\n';
				}
				
				result += "\n\nArestas:\n";
				for (Aresta a : buscadas)
					result += "   "+a.origem().id()+" ---> "+a.destino().id()+'\n';

				resultFrame.setTxtResult(result);
				resultFrame.setVisible(true);
			}
		});
		btnNewButton_2_1_4_1.setFont(new Font("Arial", Font.PLAIN, 12));
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnPrintGrafo)))
					.addContainerGap(178, Short.MAX_VALUE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNewButton_1)
					.addContainerGap(268, Short.MAX_VALUE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNewButton_1_2)
					.addContainerGap(174, Short.MAX_VALUE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(193)
							.addComponent(lblNewLabel_2_1_2_1, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(btnNewButton_2_1_1, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(numOriAdd_1, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(147, Short.MAX_VALUE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNewButton_2_1_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(numVertGrau, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(144, Short.MAX_VALUE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(chckPeso)
							.addContainerGap())
						.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel_2.createSequentialGroup()
								.addComponent(chckbxAdicionarUmPeso, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_2.createSequentialGroup()
											.addGap(193)
											.addComponent(lblNewLabel_2_1_2, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
											.addGap(6)
											.addComponent(lblNewLabel_2_1_1_1, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel_2.createSequentialGroup()
											.addComponent(btnNewButton_2_1, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
											.addGap(6)
											.addComponent(numOriAdd, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
											.addGap(23)
											.addComponent(numDestAdd, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel_2_1_1_1_1, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
										.addComponent(numPesoAdd, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
									.addContainerGap())
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel_2_1, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
										.addComponent(numOri, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addComponent(numDest, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_2_1_1, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
									.addGap(76))))))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(193)
							.addComponent(lblNewLabel_2_1_2_2, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(lblNewLabel_2_1_1_1_2, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(lblNewLabel_2_1_1_1_1_1, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(btnNewButton_2_1_3, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(numOriSet, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addGap(23)
							.addComponent(numDestSet, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addGap(27)
							.addComponent(numPesoSet, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_3)
					.addContainerGap(40, Short.MAX_VALUE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNewButton_2_1_4, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_2_1_4_1, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
					.addGap(27))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(11)
					.addComponent(lblNewLabel_2)
					.addGap(4)
					.addComponent(btnPrintGrafo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_1_2)
					.addGap(11)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNewLabel_2_1_1)
							.addGap(26))
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnNewButton_2)
							.addComponent(numOri, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(numDest, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNewLabel_2_1)
							.addGap(26)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckPeso)
					.addGap(12)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2_1_2)
								.addComponent(lblNewLabel_2_1_1_1))
							.addGap(3)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton_2_1)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGap(1)
									.addComponent(numOriAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGap(1)
									.addComponent(numDestAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNewLabel_2_1_1_1_1)
							.addGap(4)
							.addComponent(numPesoAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxAdicionarUmPeso)
					.addGap(12)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_2_1_2)
						.addComponent(numVertGrau, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2_1_2_2)
						.addComponent(lblNewLabel_2_1_1_1_2)
						.addComponent(lblNewLabel_2_1_1_1_1_1))
					.addGap(3)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_2_1_3)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(1)
							.addComponent(numOriSet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(1)
							.addComponent(numDestSet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(1)
							.addComponent(numPesoSet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_2_1_4)
						.addComponent(btnNewButton_2_1_4_1))
					.addGap(676)
					.addComponent(lblNewLabel_2_1_2_1)
					.addGap(3)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_2_1_1)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(1)
							.addComponent(numOriAdd_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 405, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
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
			System.err.println("Eita carai, não era pra ter chegado aqui");
		}
	}
}
