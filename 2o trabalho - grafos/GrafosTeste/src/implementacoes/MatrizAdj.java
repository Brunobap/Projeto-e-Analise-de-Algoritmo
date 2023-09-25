package implementacoes;

import java.util.ArrayList;
import java.util.HashMap;

import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;

public class MatrizAdj implements Grafo {
		
	private int numVerts;
	private double[][] matVerts;
	
	// Getters e Setters
	public double[][] getMatVerts() {	
		return matVerts;
	}
	public int getNumVerts() {	
		return numVerts;
	}
	public void setNumVerts(int numVerts) {	
		this.numVerts = numVerts;
	}	
	public void setMatVerts(double[][] matVerts) {	
		this.matVerts = matVerts;
	}
	
	// Função para testes no console
	public String print() {
		String saida = "";
		
		for (int i=0; i<this.numVerts; i++) {
			for (int j=0; j<this.numVerts; j++) {
				saida += " "+this.matVerts[i][j];
			} saida += "\n";
		}
		
		return saida;
	}
	
	// ctor da representação
	public MatrizAdj(ArrayList<String> entrada) {
		this.setNumVerts(Integer.parseInt(entrada.get(0)));
		
		this.matVerts = new double[this.numVerts][this.numVerts];
		for (int i=0; i<this.numVerts; i++) {
			
			String[] linha = entrada.get(i+1).split(" ");
			for (int j=1; j<linha.length; j++) {
				String strDest = linha[j].substring(0,linha[j].indexOf('-'));
				String strPeso = linha[j].substring(linha[j].indexOf('-')+1, linha[j].indexOf(';'));
				this.matVerts[i][Integer.parseInt(strDest)] = Integer.parseInt(strPeso);
			}
		}
	}

	// 9 de 10 feitos
	@Override
	public void adicionarAresta(Vertice origem, Vertice destino) throws Exception {
		this.matVerts[origem.id()][destino.id()] = 1;
	}
	@Override
	public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
		this.matVerts[origem.id()][destino.id()] = peso;		
	}
	@Override
	public boolean existeAresta(Vertice origem, Vertice destino) {
		if (this.matVerts[origem.id()][destino.id()] > 0) return true;
		else return false;
	}
	@Override
	public int grauDoVertice(Vertice vertice) throws Exception {
		int grau = 0;

		for (int i=0; i<this.numVerts; i++)
			if (this.matVerts[vertice.id()][i] > 0) grau++;
				
		return grau;
	}
	@Override
	public int numeroDeVertices() {
		return this.numVerts;
	}
	@Override
	public int numeroDeArestas() {
		int soma = 0;

		for (int i=0; i<this.numVerts; i++)
			for (int j=0; j<this.numVerts; j++)
				if (this.matVerts[i][j] > 0) soma++;
		
		return soma;
	}
	@Override
	public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception {
		ArrayList<Vertice> listVerts = new ArrayList<Vertice>();
		
		for (int i=0; i<this.numVerts; i++)
			if (this.matVerts[vertice.id()][i] > 0) listVerts.add(new Vertice(i));
				
		return null;
	}
	@Override
	public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
		this.matVerts[origem.id()][destino.id()] = peso;
		
	}
	@Override
	public ArrayList<Vertice> vertices() {
		ArrayList<Vertice> listVert = new ArrayList<Vertice>();
		
		for (int i=0; i<this.numVerts; i++)
			listVert.add(new Vertice(i));
		
		return listVert;
	}	
	
	@Override
	public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
}