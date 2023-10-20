package implementacoes;

import java.util.ArrayList;

import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;

public class MatrizInc implements Grafo {
		
	private int numVerts;
	private int numAres;
	private double[][] matAres;
	private ArrayList<Vertice> arrayVerts;
	private ArrayList<Aresta> arrayAres;

	// Getters e Setters
	public int getNumAres() {
		return numAres;
	}
	public void setNumAres(int numAres) {
		this.numAres = numAres;
	}
	public double[][] getmatAres() {	
		return matAres;
	}
	public int getNumVerts() {	
		return numVerts;
	}
	public void setNumVerts(int numVerts) {	
		this.numVerts = numVerts;
	}	
	public void setmatAres(double[][] matAres) {	
		this.matAres = matAres;
	}	
	public ArrayList<Vertice> getArrayVerts() {
		return arrayVerts;
	}
	public void setArrayVerts(ArrayList<Vertice> arrayVerts) {
		this.arrayVerts = arrayVerts;
	}	
	public ArrayList<Aresta> getArrayAres() {
		return arrayAres;
	}
	public void setArrayAres(ArrayList<Aresta> arrayAres) {
		this.arrayAres = arrayAres;
	}
	
	// Função para testes no console
	public String print() {
		String saida = "";
		
		for (int i=0; i<this.numVerts; i++) {
			for (int j=0; j<this.numAres; j++) {
				saida += " ";
				if (this.matAres[i][j] < 10) saida += "0";
				saida += this.matAres[i][j];
			} saida += "\n";
		}
		
		return saida;
	}
	
	// ctor da representação
	public MatrizInc(ArrayList<String> entrada) {
		this.numVerts = Integer.parseInt(entrada.get(0));
		
		this.numAres = 0;
		for (int i=1; i<entrada.size(); i++)
			this.numAres += entrada.get(i).split(" ").length-1;
		
		this.matAres = new double[this.numVerts][this.numAres];
		this.arrayAres = new ArrayList<Aresta>();
		this.arrayVerts = new ArrayList<Vertice>();
		int aux = 0;
		for (int i=1; i<=this.numVerts; i++) {			
			String[] linha = entrada.get(i).split(" ");
			// Ver se o vértice já existe
			Vertice vOrigem = null;
			for (Vertice v : this.arrayVerts) 
				if (v.id() == Integer.parseInt(linha[0])) {
					vOrigem = v;
					break;
				}
			// Se não existir, criar um novo
			if (vOrigem == null) {
				vOrigem = new Vertice(Integer.parseInt(linha[0]));
				this.arrayVerts.add(vOrigem);
			}
			
			for (int j=1; j<linha.length; j++) {
				String strDest = linha[j].substring(0,linha[j].indexOf('-'));
				String strPeso = linha[j].substring(linha[j].indexOf('-')+1, linha[j].indexOf(';'));
				this.matAres[Integer.parseInt(strDest)][aux] = Integer.parseInt(strPeso);
				aux++;
				
				// Ver se o vértice já existe
				Vertice vDest = null;
				for (Vertice v : this.arrayVerts) 
					if (v.id() == Integer.parseInt(strDest)) {
						vDest = v;
						break;
					}
				// Se não existir, criar um novo
				if (vDest == null) {
					vDest = new Vertice(Integer.parseInt(strDest));
					this.arrayVerts.add(vDest);
				}
				
				// Criar uma aresta nova
				this.arrayAres.add(new Aresta(vOrigem, vDest, Integer.parseInt(strPeso)));
			}
		}
	}

	// 10 de 10 feitos
	@Override
	public int grauDoVertice(Vertice vertice) throws Exception {
		int grau = 0;

		for (int i=0; i<this.numVerts; i++)
			if (this.matAres[vertice.id()][i] > 0) grau++;
				
		return grau;
	}
	@Override
	public int numeroDeVertices() {
		return this.numVerts;
	}	
	@Override
	public int numeroDeArestas() {		
		return this.arrayAres.size();
	}
	@Override
	public void adicionarAresta(Vertice origem, Vertice destino) throws Exception {
		this.numAres++;
		double[][] newArray = new double[this.numVerts][this.numAres];
		
		for (int i=0; i<this.numVerts; i++)
			for (int j=0; j<this.numAres-1; j++)
				newArray[i][j] = this.matAres[i][j];

		this.matAres = newArray;
		this.matAres[destino.id()][this.numAres-1] = 1;
		this.arrayAres.add(new Aresta(origem, destino));
	}
	@Override
	public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
		this.numAres++;
		double[][] newArray = new double[this.numVerts][this.numAres];
		
		for (int i=0; i<this.numVerts; i++)
			for (int j=0; j<this.numAres-1; j++)
				newArray[i][j] = this.matAres[i][j];

		this.matAres = newArray;
		this.matAres[destino.id()][this.numAres-1] = peso;
		this.arrayAres.add(new Aresta(origem, destino, peso));
	}
	@Override
	public boolean existeAresta(Vertice origem, Vertice destino) {
		for (Aresta a: this.arrayAres)
			if (a.origem() == origem && a.destino() == destino) return true;
		return false;
	}	
	@Override
	public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception {
		ArrayList<Vertice> listVerts = new ArrayList<Vertice>();
		
		for (Aresta a: this.arrayAres)
			if (a.origem() == vertice) listVerts.add(a.destino());
				
		return listVerts;
	}
	@Override
	public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
		try {
			int vO = this.arrayVerts.indexOf(origem);
			int vD = this.arrayVerts.indexOf(destino);
			if (vO == -1 || vD == -1) vO = 1/0;
			for (int i=0; i<this.numAres; i++) {
				Aresta a = this.arrayAres.get(i);
				if (a.destino() == destino && a.origem() == origem) {
					a.setarPeso(peso);
					this.matAres[destino.id()][i] = peso;
					break;
				}
			}		
		} catch (Exception e) {
			System.err.println(e);
		}
		this.matAres[origem.id()][destino.id()] = peso;
		
	}
	@Override
	public ArrayList<Vertice> vertices() {
		return this.arrayVerts;
	}		
	@Override
	public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception {
		ArrayList<Aresta> listAres = new ArrayList<Aresta>();
		
		for (Aresta a : this.arrayAres)
			if (a.destino() == destino && a.origem() == origem) listAres.add(a);
		
		return listAres;
	}
}