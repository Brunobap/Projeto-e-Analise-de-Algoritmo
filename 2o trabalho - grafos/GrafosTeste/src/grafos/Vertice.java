/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package grafos;

/**
 *
 * @author humberto e douglas
 */
public class Vertice {
	// Identificador do vértice
    private int vertice;
    
    /* 
     * Cores dos vértices:
     * v - Vermelho - vértice acabou de ser gerado, não foi usado
     * b - Branco - Não visitado
     * c - Cinza - Visitado, mas não finalizado
     * p´- Preto - Visitado e finalizado
     */
    private char cor;
    
    /* 
     * Informações para Busca em Profundidade
     * d: marcador do instante que o vértice c foi descoberto;
     * f: marcador do instante que o fecho transitivo do vértice c foi totalmente visitado (considerado então finalizado).
     */
    private double d, f;
    
    /*
     * Informações para Busca em Largura
     * pi - Vértice que é pai desse
     * dist - Distância desde a origem (em arestas)
     */
    private Vertice pi;
    private double dist;
    
    
    public Vertice( int v ){
    	// Info geral
        this.vertice = v;
        this.cor = 'v';
        
        // Info Busca em Prof
        this.d = -1;
        this.f = -1;
        
        // Info Busca em Larg
        this.pi = null;
        this.dist = -1;
        
    }

    public int id() {
        return vertice;
    }
    public void setarVertice(int vertice) {
        this.vertice = vertice;
    }

	public char getCor() {
		return cor;
	}
	public void setCor(char cor) {
		this.cor = cor;
	}

	public double getD() {
		return d;
	}

	public void setD(double e) {
		this.d = e;
	}

	public double getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public Vertice getPi() {
		return pi;
	}

	public void setPi(Vertice pi) {
		this.pi = pi;
	}

	public double getDist() {
		return dist;
	}

	public void setDist(double e) {
		this.dist = e;
	}
    
}
