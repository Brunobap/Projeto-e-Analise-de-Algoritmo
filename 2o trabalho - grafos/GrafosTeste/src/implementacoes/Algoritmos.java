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

	/*
	 * Modelo BEL:
	 * BFS(G,s)
	 * 		para cada vertice u e V[G] - {s}
	 * 			cor[u] = BRANCO
	 * 			d[u] = inf
	 * 			pi[u] = null
	 * 		cor[s] = CINZA
	 * 		d[s] = 0
	 * 		pi[s] = null
	 * 		Q = novaFila()
	 * 		ENFILEIRA(Q,s)
	 * 		enquanto !vazia(Q)
	 * 			u = DESINFILEIRA(Q)
	 * 			para cada v = Adj[u]
	 * 				se cor[v] = BRANCO
	 * 					cor[v] = CINZA
	 * 					d[v] = d[u] + 1
	 * 					pi[v] = u
	 * 					ENFILEIRA(Q,v)
	 * 		cor[u] = PRETO
	 */
	@Override
	public Collection<Aresta> buscaEmLargura(Grafo g) {
		try {
			ArrayList<Aresta> arvRes = new ArrayList<Aresta>();
			
			for (Vertice u : g.vertices()) {
				u.setCor('b');
				u.setDist(Integer.MAX_VALUE);
				u.setPi(null);
			}
			
			// Marcar as informações da origem
			Vertice origem = g.vertices().get(0);
			origem.setCor('c');
			origem.setDist(0);
			origem.setPi(null);
			
			// Começar a fila de atendimento
			ArrayList<Vertice> Q = new ArrayList<Vertice>();
			Q.add(origem);
			
			Vertice aux;
			while (!Q.isEmpty()) {
				aux = Q.remove(0);
				for (Vertice v : g.adjacentesDe(aux)) 
					if (v.getCor() == 'b') {
						v.setCor('c');
						v.setDist(aux.getDist()+1.0);
						v.setPi(aux);
						arvRes.add(new Aresta(aux,v));
						Q.add(v);
					}
				aux.setCor('p');
			}
			
			return arvRes;
			
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	/* 
	 * Modelos BEP:
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
					arvRes.addAll(BEP_Visit(adj));
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

	// Usa BEP mas retorna true quando 2 vértices cinza se encontram e false se não se cruzarem
	@Override
	public boolean existeCiclo(Grafo g) {
		this.g = g;
		
		ArrayList<Aresta> arvFin = null;
		
		for (Vertice v : g.vertices())
			v.setCor('b');
		
		tempo = 0;					
		
		for(Vertice v : g.vertices())
			if (v.getCor() == 'b') arvFin = BEP_Visit(v);
			else return true;
		
		// Final liberar a memória do algoritmo
		this.g = null;
	
		return false;
	}

	/*
	 * Modelo AGM_Kruskall:
	 * AGM_Kruskall(G(V,A), w)
	 * 		X = {}
	 * 		para cada vértice v ∈ V faça
	 * 			criarConjunto(v)
	 * 		fim para
	 * 		A' = "ordenar as arestas de A por peso crescente"
	 * 		para cada aresta(u,v) ∈ A' faça
	 * 			se conjuntpDe(u) != conjuntoDe(v) então
	 * 				X = X U {(u,v)}
	 * 				aplicarUnião(u,v)
	 * 			fim se
	 * 		fim para
	 * 		retorne X
	 * fim.   
	 */
	@Override
	public Collection<Aresta> agmUsandoKruskall(Grafo g) {
		// Criar o conjunto de arestas
		ArrayList<Aresta> X = new ArrayList<Aresta>();
		
		// Criar as arvores de cada vértice
		ArrayList<Vertice> V = g.vertices();		
		ArrayList<ArrayList<Vertice>> arvores = new ArrayList<ArrayList<Vertice>>();
		for (Vertice v : V) {
			ArrayList<Vertice> newConj = new ArrayList<Vertice>();
			newConj.add(v);
			arvores.add(newConj);
		}
		
		// Ordenar as arestas por peso
		ArrayList<Aresta> AOrd = arestasOrdenadas(g.getArrayAres());
		
		// Verificação, adição e união
		for (Aresta a : AOrd) {
			Vertice ori = a.origem(), dest = a.destino();
			ArrayList<Vertice> arvOri = null, arvDest = null;
			for (ArrayList<Vertice> arv : arvores) {
				if (arv.contains(ori)) arvOri = arv;
				if (arv.contains(dest)) arvDest = arv;
			}
			if (arvOri != null && arvDest != null && arvOri != arvDest) {
				X.add(a);				
				arvOri.addAll(arvDest);
				arvores.remove(arvDest);
			}		
		}
		
		return X;
	}
	private static ArrayList<Aresta> arestasOrdenadas(ArrayList<Aresta> original){
		ArrayList<Aresta> ordenadas = new ArrayList<Aresta>();
		for (int i=0; i<original.size(); i++) ordenadas.add(null);
		
		for (Aresta a : original) {
			int menores = 0;
			for (Aresta j : original) {
				if (a!=j) {
					double pI = a.peso(), pJ = j.peso();
					if (pI > pJ) menores++;
				}
			}			
			while (ordenadas.get(menores)!=null && ordenadas.get(menores).peso()==a.peso()) menores++;
			ordenadas.remove(menores);
			ordenadas.add(menores, a);
		}
		
		return ordenadas;
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

	/*
	 * Modelos de CaminhoCurto-BellmanFord:
	 * INICIALIZA (G = (V,A), s)
	 * 		para cada v ∈ V
	 * 			d[v] = inf
	 * 			pi[v] = null
	 * 		fim para
	 * 		d[s] = 0
	 * fim
	 * 
	 * RELAXA (u,v,w)
	 * 		se (d[v] < (d[u] + w(u,v)) então
	 * 			d[v] = d[u] + w(u,v)
	 * 			pi[v] = u
	 * 		fim se
	 * fim
	 * 
	 * BELLMAN_FORD (G = (V,A), w, s)
	 * 		INICIALIZA(G,s)
	 * 		para i de 1 a |V|-1
	 * 			para cada aresta (u,v) ∈ A
	 * 				RELAXA (u,v,w)
	 * 			fim para
	 * 		fim para
	 *		para cada aresta (u,v) ∈ A
	 *			se d[v] > d[u] + w[u,v]
	 *				retorna falso
	 *			fim se
	 * 		fim para
	 * 		retorna verdadeiro
	 * fim
	 */
	@Override
	public ArrayList<Aresta> caminhoMaisCurto(Grafo g, Vertice origem, Vertice destino) {
		inicializa(g, origem);
		
		for (int i=0; i<g.vertices().size()-1; i++) {
			for (Aresta a : g.getArrayAres()) 
				relaxa(a.origem(), a.destino(), a);
		}
		
		for (Aresta a : g.getArrayAres()) {
			if (a.destino().getD() > a.origem().getD() + a.peso())
				return null;
		}
		
		return null;
	}
	private static void inicializa (Grafo g, Vertice s) {
		for (Vertice v : g.vertices()) {
			v.setD(Integer.MAX_VALUE);
			v.setPi(null);
		}
		s.setD(0);
	}
	private static void relaxa (Vertice u, Vertice v, Aresta w) {
		if (v.getD() < u.getD() + w.peso()) {
			v.setD(u.getD() + w.peso());
			v.setPi(u);
		}
	}

	@Override
	public double custoDoCaminho(Grafo g, ArrayList<Aresta> arestas, Vertice origem, Vertice destino) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean ehCaminho(ArrayList<Aresta> arestas, Vertice origem, Vertice destino) {
		
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