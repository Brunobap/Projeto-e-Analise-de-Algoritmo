package implementacoes;

import java.util.ArrayList;

import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;

public class ListaAdj implements Grafo {
		
	private int numVerts;
	private ArrayList<ArrayList<NoLista>> listVerts;
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
	@Override
	public String toString() {
		String saida = "";
		
		for (int i=0; i<this.numVerts; i++) {
			saida += this.arrayVerts.get(i).id();
			for (int j=0; j<this.listVerts.get(i).size(); j++) {
				NoLista n = this.listVerts.get(i).get(j);
				saida += " -> " + n.destino.id() + " - " + n.peso;
			} saida += "\n";
		}
		
		return saida;
	}
	
	// ctor da representação
	public ListaAdj(ArrayList<String> entrada) {
		this.setNumVerts(Integer.parseInt(entrada.get(0)));
		entrada.remove(0);
		
		this.listVerts = new ArrayList<ArrayList<NoLista>>();
		this.arrayAres = new ArrayList<Aresta>();
		this.arrayVerts = new ArrayList<Vertice>();
		
		for (int i=0; i<this.numVerts; i++) {
			this.listVerts.add(new ArrayList<NoLista>());
			this.arrayVerts.add(new Vertice(i));
		}
		
		for (String l : entrada) {		
			String[] linha = l.split(" ");
			Vertice vOrigem = this.arrayVerts.get(Integer.parseInt(linha[0]));
			
			for (int j=1; j<linha.length; j++) {
				String strDest = linha[j].substring(0,linha[j].indexOf('-'));
				String strPeso = linha[j].substring(linha[j].indexOf('-')+1, linha[j].indexOf(';'));
				
				// Pegar o vérice de destino
				Vertice vDest = this.arrayVerts.get(Integer.parseInt(strDest));
				
				// Criar uma aresta nova
				this.arrayAres.add(new Aresta(vOrigem, vDest, Integer.parseInt(strPeso)));
				
				// Botar na lista
				NoLista n = new NoLista(vDest, Integer.parseInt(strPeso));
				this.listVerts.get(vOrigem.id()).add(n);
			}
		}
	}

	// 10 de 10 feitos
	@Override
	public void adicionarAresta(Vertice origem, Vertice destino) throws Exception {
		NoLista n = new NoLista(destino, 1);
		int aux = findVert(origem);

		this.listVerts.get(aux).add(n);
		this.arrayAres.add(new Aresta(origem, destino));
	}
	@Override
	public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {NoLista n = new NoLista(destino, peso);
		int aux = findVert(origem);

		this.listVerts.get(aux).add(n);
		this.arrayAres.add(new Aresta(origem, destino, peso));	
	}
	@Override
	public boolean existeAresta(Vertice origem, Vertice destino) {
		int aux = findVert(origem);
		
		for (NoLista n : this.listVerts.get(aux))
			if (n.destino == destino) return true;
		
		return false;
	}
	@Override
	public int grauDoVertice(Vertice vertice) throws Exception {
		int aux = findVert(vertice);				
		return this.listVerts.get(aux).size();
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
		
		int posV = findVert(vertice);
		
		for (NoLista n : this.listVerts.get(posV))
			listVerts.add(n.destino);
				
		return listVerts;
	}
	@Override
	public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
		int aux = findVert(origem);
		
		for (NoLista n : this.listVerts.get(aux))
			if (n.destino == destino) {
				n.peso = peso;
				return;
			}
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

	
	// Função utilitária: pega o indice de um vertice no array de vertices
	private int findVert(Vertice v) {
		for (int i=0; i<this.numVerts; i++) 
			if (this.arrayVerts.get(i) == v) 
				return i;
		
		return -1;
	}
}