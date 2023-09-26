package implementacoes;

import java.util.ArrayList;
import java.util.HashMap;

import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;

public class MatrizInc implements Grafo {
		
	private int numAres;
	private int numVerts;
	private double[][] matAres;
	
	// Getters e Setters
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
	
	// Função para testes no console
	public String print() {
		String saida = "";
		
		for (int i=0; i<this.numAres; i++) {
			for (int j=0; j<this.numVerts; j++) {
				saida += " "+this.matAres[i][j];
			} saida += "\n";
		}
		
		return saida;
	}
	
	// ctor da representação
	public MatrizInc(ArrayList<String> entrada) {
		this.setNumVerts(Integer.parseInt(entrada.get(0)));
		entrada.remove(0);
		
		this.numAres = 0;
		for (String linha : entrada) 
			this.numAres += linha.split(" ").length-1;
		
		this.matAres = new double[this.numAres][this.numVerts];
		int aux = 0;
		for (String linha : entrada) {
			String[] pedacos = linha.split(" ");
			for (int i=1; i<pedacos.length; i++) {
				String strDestino = pedacos[i].substring(0,pedacos[i].indexOf('-'));
				String strPeso = pedacos[i].substring(pedacos[i].indexOf('-')+1, pedacos[i].indexOf(';'));
				this.matAres[aux][Integer.parseInt(strDestino)] = Double.parseDouble(strPeso);
				aux++;
			}
		}
	}

	// 3 de 10 feitos
	@Override
	public int grauDoVertice(Vertice vertice) throws Exception {
		int grau = 0;

		for (int i=0; i<this.numAres; i++)
			if (this.matAres[i][vertice.id()] > 0) grau++;
				
		return grau;
	}
	@Override
	public int numeroDeVertices() {
		return this.numVerts;
	}	
	@Override
	public int numeroDeArestas() {
		return this.numAres;
	}
	
	@Override
	public void adicionarAresta(Vertice origem, Vertice destino) throws Exception {
		this.matAres[origem.id()][destino.id()] = 1;
	}
	
	@Override
	public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
		this.matAres[origem.id()][destino.id()] = peso;		
	}
	
	@Override
	public boolean existeAresta(Vertice origem, Vertice destino) {
		if (this.matAres[origem.id()][destino.id()] > 0) return true;
		else return false;
	}
		
	@Override
	public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception {
		ArrayList<Vertice> listVerts = new ArrayList<Vertice>();
		
		for (int i=0; i<this.numVerts; i++)
			if (this.matAres[vertice.id()][i] > 0) listVerts.add(new Vertice(i));
				
		return null;
	}
	
	@Override
	public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
		this.matAres[origem.id()][destino.id()] = peso;
		
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