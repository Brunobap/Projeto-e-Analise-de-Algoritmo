package implementacoes;

import grafos.Vertice;

public class NoLista {
	public int peso;
	public Vertice destino;
	
	public NoLista(Vertice destino, int peso) {
		this.destino = destino;
		this.peso = peso;
	}

	public NoLista(Vertice destino) {
		this.destino = destino;
		this.peso = 1;
	}
	
	public String print() {
		return "{v: "+this.destino+"; p: "+this.peso+"}";
	}
}