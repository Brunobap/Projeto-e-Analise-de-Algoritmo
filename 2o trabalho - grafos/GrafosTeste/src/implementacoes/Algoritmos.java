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
			case MATRIZ_DE_ADJACENCIA: return new MatrizAdj(entrada);
			case MATRIZ_DE_INCIDENCIA: return new MatrizInc(entrada);
			default: return new ListaAdj(entrada);
		}
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

	// É literalmente o algoritmo de Dijikstra, mas todas as arestas tem peso 1
	@Override
	public ArrayList<Aresta> menorCaminho(Grafo g, Vertice origem, Vertice destino) throws Exception {
		// Inicialização
				// N = {A}
				ArrayList<Vertice> N = new ArrayList<Vertice>();
				N.add(origem);
				
				for (Vertice v : g.vertices()) {
					if (g.existeAresta(origem, v)) {
						v.setD(1);		
						v.setPi(origem);					
					} else { 
						v.setD(Double.MAX_VALUE);
						v.setPi(null);
					}
				}
				origem.setD(0);
				
				// Vértices que ainda não foram "apagados da tabela"
				ArrayList<Vertice> restam = g.vertices();
				restam.remove(origem);
				
				// Loop
				ArrayList<Aresta> passosDados = new ArrayList<Aresta>();
				while (!N.contains(destino)) {
					Aresta passo = menorPasso_SP(g, N, restam, destino);
					if (passo == null) return null;
					passosDados.add(passo);
					Vertice v = restam.remove(restam.indexOf(passo.destino()));
					N.add(v);
					
				} 

				// Final: voltar da origem e ver o caminho que é formado
				ArrayList<Aresta> caminho = new ArrayList<Aresta>();
				Aresta a = passosDados.removeLast();
				caminho.add(a);
				while (caminho.get(0).origem()!=origem && passosDados.size()>0) {
					for (Aresta passo : passosDados)
						if (passo.destino()==caminho.get(0).origem()) {
							passosDados.remove(passo);
							caminho.addFirst(passo);
							break;
						}				
				}
				
				return caminho;
	}
	private static Aresta menorPasso_SP(Grafo g, ArrayList<Vertice> N, ArrayList<Vertice> restam, Vertice destino) {
		try {			
			ArrayList<Aresta> passosPossiveis = new ArrayList<Aresta>();			
			
			for (Aresta a : g.getArrayAres())
				if (N.contains(a.origem()) && restam.contains(a.destino()) && !passosPossiveis.contains(a)) {
					relaxa(a.origem(), a.destino());
					passosPossiveis.add(a);
				}
			
			ArrayList<Aresta> iguais = new ArrayList<Aresta>();
			Aresta a = passosPossiveis.remove(0);
			for (Aresta b : passosPossiveis) 
				if (b.destino().getD() < a.destino().getD()) {
					a = b;
					iguais.removeAll(iguais);
				} else if (b.destino().getD() == a.destino().getD()) iguais.add(b);			
			
			for (Aresta igual : iguais)
				if (igual.destino().equals(destino)) {
					a = igual;
					break;
				}
			
			return a;	
		} catch (Exception e) {
			return null;
		}
	}
	private static void relaxa (Vertice u, Vertice v) {
		if (v.getD() > u.getD() + 1) {
			v.setD(u.getD() + 1);
			v.setPi(u);
		}
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
		double soma = 0;
		ArrayList<Aresta> AGM = (ArrayList<Aresta>) this.agmUsandoKruskall(g);
		
		if (!AGM.equals(arestas)) throw new Exception("A arvore passada não é geradora desse grafo.");
		else for (Aresta a : AGM) soma += a.peso();
		
		return soma;
	}

	@Override
	public boolean ehArvoreGeradora(Grafo g, Collection<Aresta> arestas) {
		ArrayList<Aresta> AGM = (ArrayList<Aresta>) this.agmUsandoKruskall(g);

		if (AGM.equals(arestas)) return true;
		else return false;
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
	@Override
	public ArrayList<Aresta> caminhoMaisCurto(Grafo g, Vertice origem, Vertice destino) {
		// Inicialização
		// N = {A}
		ArrayList<Vertice> N = new ArrayList<Vertice>();
		N.add(origem);
		
		for (Vertice v : g.vertices()) {
			// Caso o vérice não seja adjacente seu peso padrão, D = infinito, não muda
			double peso = Double.MAX_VALUE;
			try { 
				for (Aresta a : g.arestasEntre(origem, v)) {
					// Se exsite uma aresta de peso menor, use ela como parâmetro
					if (a.peso()<peso) peso = a.peso();		
					v.setPi(origem);
				}
			} catch (Exception e) {
				// Esse vértice não é adjacente, D = infinito, Pi = null
				v.setPi(null);
			} finally { 
				v.setD(peso);
			}
		}
		origem.setD(0);
		
		// Vértices que ainda não foram "apagados da tabela"
		ArrayList<Vertice> restam = g.vertices();
		restam.remove(origem);
		
		// Loop
		ArrayList<Aresta> passosDados = new ArrayList<Aresta>();
		while (!N.contains(destino)) {
			Aresta passo = menorPasso(g, N, restam, destino);
			if (passo == null) return null;
			passosDados.add(passo);
			Vertice v = restam.remove(restam.indexOf(passo.destino()));
			N.add(v);
			
		}

		// Final: voltar da origem e ver o caminho que é formado
		ArrayList<Aresta> caminho = new ArrayList<Aresta>();
		Aresta a = passosDados.removeLast();
		caminho.add(a);
		while (caminho.get(0).origem()!=origem) {
			for (Aresta passo : passosDados)
				if (passo.destino()==caminho.get(0).origem()) {
					passosDados.remove(passo);
					caminho.addFirst(passo);
					break;
				}				
		}
		
		return caminho;
	}
	private static Aresta menorPasso(Grafo g, ArrayList<Vertice> N, ArrayList<Vertice> restam, Vertice destino) {
		try {			
			ArrayList<Aresta> passosPossiveis = new ArrayList<Aresta>();			
			
			for (Aresta a : g.getArrayAres())
				if (N.contains(a.origem()) && restam.contains(a.destino()) && !passosPossiveis.contains(a)) {
					relaxa(a.origem(), a.destino(), a);
					passosPossiveis.add(a);
				}
			
			ArrayList<Aresta> iguais = new ArrayList<Aresta>();
			Aresta a = passosPossiveis.remove(0);
			for (Aresta b : passosPossiveis) 
				if (b.destino().getD() < a.destino().getD()) {
					a = b;
					iguais.removeAll(iguais);
				} else if (b.destino().getD() == a.destino().getD()) iguais.add(b);			
			
			for (Aresta igual : iguais)
				if (igual.destino().equals(destino)) {
					a = igual;
					break;
				}
			
			return a;	
		} catch (Exception e) {
			return null;
		}
	}
	private static void relaxa (Vertice u, Vertice v, Aresta w) {
		if (v.getD() > u.getD() + w.peso()) {
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
			for (int i=0; i<arestas.size()-1; i++) {
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
				if (adj.getCor() == 'b') arvRes.addAll(BEP_Visit_AAr(adj, g));
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
				if (adj.getCor() == 'b') arvRes.addAll(BEP_Visit_ARe(adj, g));
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
		ArrayList<Aresta> 
				avancos = new ArrayList<Aresta>(),
				buscadas = (ArrayList<Aresta>) this.buscaEmProfundidade(g);

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
				arvOri.addAll(arvDest);
				arvores.remove(arvDest);
			}		
		}
		
		for (Aresta a : g.getArrayAres()) {
			ArrayList<Vertice> arv1 = null, arv2 = null;
			for (ArrayList<Vertice> arvore : arvores) {
				if (arvore.contains(a.origem())) arv1 = arvore;
				if (arvore.contains(a.destino())) arv2 = arvore;
				if (arv1!=null && arv2!=null) break;
			}
			if (arv1!=null && arv2!=null && arv1==arv2 && a.origem().getD()<a.destino().getD()) {
				for (int i=0; i<buscadas.size(); i++) {
					Aresta b = buscadas.get(i);
					if (a.origem()==b.origem() && a.destino()==b.destino()) break;
					else if (i==buscadas.size()-1) avancos.add(a);		
				}
			}
		}
		
		return avancos;
	}
	
	@Override
	public Collection<Aresta> arestasDeCruzamento(Grafo g) {
		ArrayList<Aresta> cruz = new ArrayList<Aresta>();

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
				arvOri.addAll(arvDest);
				arvores.remove(arvDest);
			}		
		}
		
		for (Aresta a : g.getArrayAres()) {
			ArrayList<Vertice> arv1 = null, arv2 = null;
			for (ArrayList<Vertice> arvore : arvores) {
				if (arvore.contains(a.origem())) arv1 = arvore;
				if (arvore.contains(a.destino())) arv2 = arvore;
				if (arv1!=null && arv2!=null) break;
			}
			if (arv1!=null && arv2!=null && arv1!=arv2) cruz.add(a);			
		}
		
		return cruz;
	}
	
}