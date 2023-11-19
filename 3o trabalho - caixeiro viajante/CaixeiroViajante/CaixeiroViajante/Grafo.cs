using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;

namespace CaixeiroViajante
{
    // O vértice só precisa da informação do seu id, então ele pode ser substituido por um inteiro
    internal class Aresta
    {
        private int origem, destino;
        private double peso;
        public Aresta (int origem, int destino, double peso)
        {
            this.origem = origem;
            this.destino = destino;
            this.peso = peso;
        }
        public int Origem() { return origem; }
        public int Destino() { return destino; }
        public double Peso() { return peso; }
    }
    internal class Grafo
    {
        private int numVerts;
        private double[][] matriz;
        private ArrayList vertices;
        
        public Grafo (String path)
        {
            vertices = new ArrayList();

            String[] entrada = File.ReadAllLines(path);
            numVerts = int.Parse(entrada[0]);

            matriz = new double[numVerts][];

            for (int i = 0; i < numVerts; i++)
            {
                vertices.Add(i);
                matriz[i] = new double[numVerts];
            }

            for (int i=1; i<entrada.Length; i++)
            {
                String[] linha = entrada[i].Split(' ');
                int vOrigem = int.Parse(linha[0]);

                for (int j = 1; j < linha.Length; j++)
                {
                    String strDest = linha[j].Substring(0, linha[j].IndexOf("-"));

                    int a = linha[j].IndexOf("-")+1;
                    String strPeso = linha[j].Substring(a);
                    strPeso = strPeso.Replace(';', '\0');

                    int vDest = int.Parse(strDest);
                    double peso = double.Parse(strPeso);

                    matriz[vOrigem][vDest] = peso;
                }
            }
        }
        
        public int NumVertices() { return numVerts; }
        public ArrayList Vertices() { return vertices; }
        public ArrayList Arestas() { 
            ArrayList arestas = new ArrayList();

            for (int i=0; i<numVerts; i++)
                for (int j=0; j<numVerts; j++)
                    if (matriz[i][j]>0) arestas.Add(new Aresta(i, j, matriz[i][j]));

            return arestas; 
        }
        public ArrayList ArestaDe(int vertice)
        {
            ArrayList saidas = new ArrayList();

            for (int i=0; i<numVerts; i++)
                if (matriz[vertice][i] > 0) saidas.Add(new Aresta(vertice, i, matriz[vertice][i]));

            return saidas;
        }
        public ArrayList AdjacentesDe(int vertice)
        {
            ArrayList saidas = new ArrayList();

            for (int i=0; i<numVerts; i++)
                if (matriz[vertice][i] > 0) saidas.Add(i);

            return saidas;
        }
        public double arestaEntre(int origem, int destino)
        {
            return matriz[origem][destino];
        }
    }
}
