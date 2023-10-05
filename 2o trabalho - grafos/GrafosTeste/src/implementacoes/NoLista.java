package implementacoes;

import grafos.Vertice;

public class NoLista {
	public double peso;
	public Vertice destino;
	
	public NoLista(Vertice destino, double peso) {
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