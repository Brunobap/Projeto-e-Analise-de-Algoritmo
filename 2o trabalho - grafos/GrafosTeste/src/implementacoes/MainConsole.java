package implementacoes;

import java.util.ArrayList;
import java.util.Collection;

import grafos.*;

public class MainConsole {
	public static void mainConsole(String[] args) {
		String caminhoTeste = "Teste2.txt";
		
		/* 
		 * Parte 1: Representações "cruas"
		FileManager file = new FileManager();
		ArrayList<String> entrada = file.stringReader(caminhoTeste);
		System.out.println(entrada);
		
		MatrizAdj mat1 = new MatrizAdj(entrada);
		System.out.println(mat1.print());
		
		MatrizInc mat2 = new MatrizInc(entrada);
		System.out.println(mat2.print());
		
		ListaAdj lista = new ListaAdj(entrada);
		System.out.println(lista.print());
		*/
		
		/* Parte 2: Representação com grafo, e seus algoritmos */
		Algoritmos alg = new Algoritmos();
		Grafo g;
		try {
			// Carregar o grafo
			g = alg.carregarGrafo(caminhoTeste, TipoDeRepresentacao.LISTA_DE_ADJACENCIA);
			
			// 1o Algoritmo: Busca em profundidade
			Collection<Aresta> DFS = alg.buscaEmProfundidade(g);
			System.out.println(DFS);		
			
			//2o Algoritmo: Busca em Largura
			Collection<Aresta> BFS = alg.buscaEmLargura(g);
			System.out.println(BFS);
			
			//3o Algoritmo: AGM de Kruskall
			Collection<Aresta> AGM = alg.agmUsandoKruskall(g);
			System.out.println(AGM);
			
			// 4o Algoritmo: Caminho mais curto (com peso)
			Vertice origem = g.vertices().get(0), destino = g.vertices().get(10);
			ArrayList<Aresta> CMC = alg.caminhoMaisCurto(g, origem, destino);
			System.out.println(CMC);
			
			// 5o Algoritmo: "É caminho?"
			if (alg.ehCaminho(CMC, origem, destino)) 
				System.out.println("Esse caminho é válido");
			else System.out.println("Esse caminho não é válido");
			
			// 6o Algoritmo: Custo do caminho
			double custoCMC = alg.custoDoCaminho(g, CMC, origem, destino);
			System.out.println("Menor custo do caminho 0->10: "+custoCMC);
		
			// 7o Algoritmo: Custo da AGM
			double custoAGM = alg.custoDaArvoreGeradora(g, AGM);
			System.out.println("Custo da AGM: "+custoAGM);
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}