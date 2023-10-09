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
     * d: marcador do instante que o vértice c foi descoberto;
     * f: marcador do instante que o fecho transitivo do vértice c foi totalmente visitado (considerado então finalizado).
     */
    private int d, f;
    
    // 
    
    public Vertice( int v ){
        this.vertice = v;
        this.setCor('v');
        this.d = -1;
        this.f = -1;
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

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}
    
}
