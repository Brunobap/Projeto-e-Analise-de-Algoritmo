package implementacoes;

import java.util.ArrayList;
import java.util.Collection;

import grafos.*;

public class Algoritmos implements AlgoritmosEmGrafos{

	private int tempo;
	private Grafo g;
	
	@Override
	public Grafo carregarGrafo(String path, TipoDeRepresentacao t) throws Exception {
		FileManager file = new FileManager();
		ArrayList<String> entrada = file.stringReader(path);
		Grafo g;
		switch (t) {
		case MATRIZ_DE_ADJACENCIA: g = new MatrizAdj(entrada); 
			break;

		case MATRIZ_DE_INCIDENCIA: g = new MatrizInc(entrada);
			break;
			
		default: g = new ListaAdj(entrada);
			break;
		}
		return g;
	}

	@Override
	public Collection<Aresta> buscaEmLargura(Grafo g) {
		// TODO Auto-generated method stub
		return null;
	}

	/* Modelos:
	 * DFS(G)
	 * 		para cada vértice u ∈ V[G]
	 * 			cor[u] <- BRANCO
	 * 		tempo <- 0
	 * 		para cada vértice u ∈ V[G]
	 * 			se cor[u] = BRANCO
	 * 				DFS_VISIT(u)
	 * 
	 * DFS_VISIT(u)
	 * 		cor[u] = CINZA
	 * 		tempo = tempo + 1
	 * 		d[u] = tempo
	 * 		para cada vértice v ∈ Adj(u)
	 * 			se cor[v] = BRANCO
	 * 				DFS_VISIT(v)
	 * 		cor[u] = PRETO
	 * 		tempo = tempo + 1
	 * 		f[u] = tempo
	 */
	@Override
	public Collection<Aresta> buscaEmProfundidade(Grafo g) {
		this.g = g;
		
		ArrayList<Aresta> arvFin = null;
		
		for (Vertice v : g.vertices())
			v.setCor('b');
		
		tempo = 0;					
		
		for(Vertice v : g.vertices())
			if (v.getCor() == 'b')
				arvFin = BEP_Visit(v);
		
		// Final liberar a memória do algoritmo
		this.g = null;
	
		return arvFin;
	}
	private ArrayList<Aresta> BEP_Visit(Vertice u) {
		try {
			ArrayList<Aresta> arvRes = new ArrayList<Aresta>();
			
			u.setCor('c');
			
			tempo++;
			u.setD(tempo);
			
			for (Vertice adj : this.g.adjacentesDe(u))
				if (adj.getCor() == 'b') {
					arvRes.add(new Aresta(u,adj));
					ArrayList<Aresta> filhos = BEP_Visit(u);
					arvRes.addAll(filhos);
				}
					
			u.setCor('p');
			
			tempo++;
			u.setF(tempo);
			
			return arvRes;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	@Override
	public ArrayList<Aresta> menorCaminho(Grafo g, Vertice origem, Vertice destino) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existeCiclo(Grafo g) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Aresta> agmUsandoKruskall(Grafo g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double custoDaArvoreGeradora(Grafo g, Collection<Aresta> arestas) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean ehArvoreGeradora(Grafo g, Collection<Aresta> arestas) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Aresta> caminhoMaisCurto(Grafo g, Vertice origem, Vertice destino) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double custoDoCaminho(Grafo g, ArrayList<Aresta> arestas, Vertice origem, Vertice destino) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean ehCaminho(ArrayList<Aresta> arestas, Vertice origem, Vertice destino) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Aresta> arestasDeArvore(Grafo g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Aresta> arestasDeRetorno(Grafo g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Aresta> arestasDeAvanco(Grafo g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Aresta> arestasDeCruzamento(Grafo g) {
		// TODO Auto-generated method stub
		return null;
	}
	
}