package implementacoes;

import java.util.ArrayList;
import grafos.FileManager;
import implementacoes.MatrizAdj;

public class Main {
	public static void main(String[] args) {
		String caminhoTeste = "E:\\Laboratorio\\--Gits--\\Projeto-e-Analise-de-Algoritmo\\2o trabalho - grafos\\Teste.txt";
		
		FileManager file = new FileManager();
		ArrayList<String> entrada = file.stringReader(caminhoTeste);
		
		MatrizAdj mat1 = new MatrizAdj(entrada);
		System.out.println(entrada);
		System.out.println(mat1.print());
	}
}