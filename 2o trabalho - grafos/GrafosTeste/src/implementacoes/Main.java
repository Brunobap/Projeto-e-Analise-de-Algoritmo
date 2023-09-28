package implementacoes;

import java.util.ArrayList;
import grafos.FileManager;
import grafos.*;
import implementacoes.*;

public class Main {
	public static void main(String[] args) {
		String caminhoTeste = "..\\Teste.txt";
		
		FileManager file = new FileManager();
		ArrayList<String> entrada = file.stringReader(caminhoTeste);
		//System.out.println(entrada);
		
		MatrizAdj mat1 = new MatrizAdj(entrada);
		//System.out.println(mat1.print());
		
		MatrizInc mat2 = new MatrizInc(entrada);
		System.out.println(mat2.print());
		
		try {
			ArrayList<Vertice> v = mat2.vertices();
			ArrayList<Aresta> a = mat2.arestasEntre(v.get(0), v.get(1));
			System.out.println(mat2.print());
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}