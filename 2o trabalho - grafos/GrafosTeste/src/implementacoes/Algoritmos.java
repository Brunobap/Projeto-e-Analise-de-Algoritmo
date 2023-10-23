package implementacoes;

import java.util.ArrayList;
import java.util.Collection;

import grafos.*;

public class Algoritmos implements AlgoritmosEmGrafos{

	private int tempo;
	
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
						v.setDist(aux.getDist()+1);
						v.setPi(aux);
						arvRes.add(new Aresta(aux,v));
						Q.add(v);
					}
				aux.setCor('p');
			}
			
			return arvRes;
			
		} catch (Exception e) {
			System.err.println(e+e.getMessage());
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
		ArrayList<Aresta> arvFin = new ArrayList<Aresta>();
		
		for (Vertice v : g.vertices())
			v.setCor('b');
		
		tempo = 0;					
		
		for(Vertice v : g.vertices())
			if (v.getCor() == 'b')
				arvFin.addAll(BEP_Visit(v, g));
	
		return arvFin;
	}
	private ArrayList<Aresta> BEP_Visit(Vertice u, Grafo g) {
		try {
			ArrayList<Aresta> arvRes = new ArrayList<Aresta>();
			
			u.setCor('c');
			
			tempo++;
			u.setD(tempo);
			
			for (Vertice adj : g.adjacentesDe(u))
				if (adj.getCor() == 'b') {
					arvRes.add(new Aresta(u, adj));
					arvRes.addAll(BEP_Visit(adj, g));
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
		for (Vertice v : g.vertices())
			v.setCor('b');
		
		tempo = 0;					
		
		for(Vertice v : g.vertices())
			if (v.getCor() == 'b') BEP_Visit(v, g);
			else if (v.getCor() == 'c')  return true;
	
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
	 * Modelos de achaCiclioNegativo-BellmanFord:
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
	 * 
	 */ 
	 /* 
	 * Modelo de caminhoMinimo-Dijikstra:
	 *   Inicialização: 
	 *       N = {A} 
	 *        para todos os nós v 
	 *              se v é adjacente a  A
	 *                  então D(v) = c(A,v)
	 *              	senão D(v) = infinito
	 *              
	 *   Loop 
	 *   	ache w  N tal que D(w) é mínimo.
	 *   	acrescente w  a N
	 *   	atualize D(v) para todo v adjacente a w e não em N:
	 *   	D(v) = min( D(v), D(w) + c(w,v) )
	 *   	** novo custo para v é o custo anterior para v ou o menor **
	 *   	** custo de caminho conhecido para w mais o custo de w a v **
	 *   até que todos os nós estejam em N 
	 */
	/*
	 * Modelo VetorDistancia:
	 * 		Inicialização
	 * 		... para uma origem X:
	 * 		
	 * 		para todos os nós adjacentes v:
	 * 			Dx(*,v) = infinito		// o operador * significa p/ todas as colunas //
	 * 			Dx(v,v) = c(X,v)
	 * 		para todos os destinos, y
	 * 			envia min Dxw(y,w) para cada vizinho	// w sobre todos os vizinhos de X //
	 * 
	 * 
	 * 
	 * 		if (c(C,V) muda por d)
	 * 			// muda o custo para todos os destinos via vizinho v por d //
	 *			// NOTA: d pode ser positivo ou negativo //
	 *			para todos os destinos y: Dx(y,X) = Dx(y,V)+d
	 *
	 *		else if (atualização recebida de V sobre destino Y)
	 *			// caminho mais curto de V para algum Y mudou //
	 *			// V enviou um novo valor para seu min Dvw(Y,w) //
	 *			// chame este novo valor recebido "newval" //
	 *			para o púnico destino y: Dx(Y,V) = c(X,V) + newval
	 *
	 *		if nós temos um novo min 
	 */
	@Override
	public ArrayList<Aresta> caminhoMaisCurto(Grafo g, Vertice origem, Vertice destino) {
		// Inicialização
		ArrayList<Vertice> N = new ArrayList<Vertice>(), restam = new ArrayList<Vertice>();
		N.add(origem);
		for (Vertice v : g.vertices()) {
			if (v!=origem) restam.add(v);
			double peso = Double.MAX_VALUE;
			try { for (Aresta a : g.arestasEntre(origem, v))
				if (a.peso()<peso) peso = a.peso();
			} catch (Exception e) {
				System.err.println(e);
				return null;
			} finally { v.setD(peso); }
		}
		
		// Loop
		Vertice atual;
		ArrayList<Aresta> caminho = new ArrayList<Aresta>();
		do {
			atual = N.get(N.size()-1);
			Aresta passo = menorPasso(g, atual, restam);
			if (passo == null) N.remove(atual);
			else {
				Vertice v = restam.remove(restam.indexOf(passo.destino()));
				try { 
					if (g.adjacentesDe(v).size()>0) {
						N.add(v); 
						caminho.add(passo);
					}
				} catch (Exception e) {
					// não precisa fazer nada, o passo não foi dado
				}
			} 
			
		} while (atual!=destino && N.size()!=0);
		
		return caminho;
	}
	private static Aresta menorPasso(Grafo g, Vertice s, ArrayList<Vertice> adjs) {
		try {
			inicializa(g, s);
			
			ArrayList<Aresta> passosPossiveis = new ArrayList<Aresta>();
			
			
			for (int i=0; i<g.vertices().size()-1; i++)
				for (Aresta a : g.getArrayAres())
					if (adjs.contains(a.destino()) && g.adjacentesDe(s).contains(a.destino()) && !passosPossiveis.contains(a)) {
						relaxa(a.origem(), a.destino(), a);
						passosPossiveis.add(a);
					}
			
			Aresta a = passosPossiveis.remove(0);
			for (Aresta b : passosPossiveis) 
				if (b.destino().getD() < a.destino().getD()) a = b;
			
			return a;	
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
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
		try {
			Exception e = new Exception("Não é um caminho entre origem e destino de g!");
			if (!g.getArrayAres().containsAll(arestas)) throw e;
			double custo = 0;
			
			Vertice aux = origem;
			for (int i=0; i<arestas.size()-2; i++) {
				Aresta a = arestas.get(i), b = arestas.get(i+1);
				if (a.destino()==b.origem()) {
					custo += a.peso();
					aux = a.destino();
				} else throw e;
			}
			Aresta ultimoPasso = arestas.get(arestas.size()-1);
			if (ultimoPasso.origem()==aux && ultimoPasso.destino()==destino) {
				custo += ultimoPasso.peso();
			}
			
			return custo;
		}catch (Exception e) {
			System.err.println(e);
			return 0;
		}
	}

	@Override
	public boolean ehCaminho(ArrayList<Aresta> arestas, Vertice origem, Vertice destino) {
		Vertice aux = origem;
		for (int i=0; i<arestas.size(); i++) {
			Aresta a = arestas.get(i);
			Aresta b;
			if (i != arestas.size()-1) {
				b = arestas.get(i+1);
				if (aux == a.origem() && a.destino() == b.origem()) {
					aux = a.destino();
				} else return false;
				
			} else {
				if (aux == a.origem() && a.destino() == destino) {
					aux = a.destino();
				} else return false;
			}				
		}
		return true;
	}

	
	@Override
	public Collection<Aresta> arestasDeArvore(Grafo g) {
		ArrayList<Aresta> arvFin = new ArrayList<Aresta>();
		
		for (Vertice v : g.vertices())
			v.setCor('b');
		
		tempo = 0;					
		
		for(Vertice v : g.vertices())
			if (v.getCor() == 'b')
				arvFin.addAll(BEP_Visit_AAr(v, g));
	
		return arvFin;
	}
	private ArrayList<Aresta> BEP_Visit_AAr(Vertice u, Grafo g) {
		try {
			ArrayList<Aresta> arvRes = new ArrayList<Aresta>();
			
			u.setCor('c');
			
			tempo++;
			u.setD(tempo);
			
			for (Vertice adj : g.adjacentesDe(u)) {
				for (Aresta a : g.arestasEntre(u, adj))
					if (adj.getCor() == 'b') {
						arvRes.add(a);
					}
				arvRes.addAll(BEP_Visit(adj, g));
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
	public Collection<Aresta> arestasDeRetorno(Grafo g) {
ArrayList<Aresta> arvFin = new ArrayList<Aresta>();
		
		for (Vertice v : g.vertices())
			v.setCor('b');
		
		tempo = 0;					
		
		for(Vertice v : g.vertices())
			if (v.getCor() == 'b')
				arvFin.addAll(BEP_Visit_ARe(v, g));
	
		return arvFin;
	}
	private ArrayList<Aresta> BEP_Visit_ARe(Vertice u, Grafo g) {
		try {
			ArrayList<Aresta> arvRes = new ArrayList<Aresta>();
			
			u.setCor('c');
			
			tempo++;
			u.setD(tempo);
			
			for (Vertice adj : g.adjacentesDe(u)) {
				for (Aresta a : g.arestasEntre(u, adj))
					if (adj.getCor() == 'c') {
						arvRes.add(a);
					}
				if (adj.getCor() == 'b') arvRes.addAll(BEP_Visit(adj, g));
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
	public Collection<Aresta> arestasDeAvanco(Grafo g) {
		ArrayList<Aresta> buscadas = (ArrayList<Aresta>) this.buscaEmProfundidade(g);
		ArrayList<Aresta> diretas = new ArrayList<Aresta>();
		diretas.addAll(g.getArrayAres());
		
		for (Aresta a : diretas) 
			for (Aresta b : buscadas) 
				if (a.origem()==b.origem() && a.destino()==b.destino()) diretas.remove(b); 
		
		return diretas;
	}

	
	@Override
	public Collection<Aresta> arestasDeCruzamento(Grafo g) {
		// TODO Auto-generated method stub
		return null;
	}
	
}