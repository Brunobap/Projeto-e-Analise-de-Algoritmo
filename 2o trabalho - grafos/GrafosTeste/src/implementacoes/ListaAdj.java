package implementacoes;

import java.util.ArrayList;

import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;

public class ListaAdj implements Grafo {
		
	private int numVerts;
	private ArrayList<ArrayList<ArrayList<NoLista>>> listVerts;
	private ArrayList<Vertice> arrayVerts;
	private ArrayList<Aresta> arrayAres;
	
	// Getters e Setters
	public ArrayList<ArrayList<NoLista>> getlistVerts() {	
		return this.listVerts;
	}
	public int getNumVerts() {	
		return numVerts;
	}
	public void setNumVerts(int numVerts) {	
		this.numVerts = numVerts;
	}	
	public void setlistVerts(ArrayList<ArrayList<NoLista>> listVerts) {	
		this.listVerts = listVerts;
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
			for (int j=0; j<this.numVerts; j++) {
				saida += " ";
				if (this.listVerts[i][j] < 10) saida += "0";
				saida += this.listVerts[i][j];
			} saida += "\n";
		}
		
		return saida;
	}
	
	// ctor da representação
	public ListaAdj(ArrayList<String> entrada) {
		this.setNumVerts(Integer.parseInt(entrada.get(0)));
		
		this.listVerts = new double[this.numVerts][this.numVerts];
		this.arrayAres = new ArrayList<Aresta>();
		this.arrayVerts = new ArrayList<Vertice>();
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
				this.listVerts[Integer.parseInt(strDest)][i-1] = Integer.parseInt(strPeso);
				
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
	public void adicionarAresta(Vertice origem, Vertice destino) throws Exception {
		this.listVerts[destino.id()][origem.id()] = 1;
		this.arrayAres.add(new Aresta(origem, destino));
	}
	@Override
	public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
		this.listVerts[origem.id()][destino.id()] = peso;
		this.arrayAres.add(new Aresta(origem, destino, peso));	
	}
	@Override
	public boolean existeAresta(Vertice origem, Vertice destino) {
		if (this.listVerts[origem.id()][destino.id()] > 0) return true;
		else return false;
	}
	@Override
	public int grauDoVertice(Vertice vertice) throws Exception {
		int grau = 0;

		for (int i=0; i<this.numVerts; i++)
			if (this.listVerts[vertice.id()][i] > 0) grau++;
				
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
	public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception {
		ArrayList<Vertice> listVerts = new ArrayList<Vertice>();
		
		for (int i=0; i<this.numVerts; i++)
			if (this.listVerts[vertice.id()][i] > 0) listVerts.add(this.arrayVerts.get(i));
				
		return listVerts;
	}
	@Override
	public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
		this.listVerts[origem.id()][destino.id()] = peso;
		
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