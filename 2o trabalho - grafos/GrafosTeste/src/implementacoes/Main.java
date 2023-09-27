package implementacoes;

import java.util.ArrayList;
import grafos.FileManager;
import grafos.Vertice;
import implementacoes.MatrizAdj;

public class Main {
	public static void main(String[] args) {
		String caminhoTeste = "..\\Teste.txt";
		
		FileManager file = new FileManager();
		ArrayList<String> entrada = file.stringReader(caminhoTeste);
		System.out.println(entrada);
		
		MatrizAdj mat1 = new MatrizAdj(entrada);
		System.out.println(mat1.print());
		
		MatrizInc mat2 = new MatrizInc(entrada);
		System.out.println(mat2.print());
	}
}