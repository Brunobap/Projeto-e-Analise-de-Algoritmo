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
        private bool[][] matriz;
        private ArrayList vertices, arestas;
        public Grafo (String path)
        {
            vertices = new ArrayList();
            arestas = new ArrayList();

            String[] entrada = File.ReadAllLines(path);
            numVerts = int.Parse(entrada[0]);

            matriz = new bool[numVerts][];

            for (int i = 0; i < numVerts; i++)
            {
                vertices.Add(i);
                matriz[i] = new bool[numVerts];
            }

            for (int i=1; i<entrada.Length; i++)
            {
                String[] linha = entrada[i].Split(' ');
                int vOrigem = int.Parse(linha[0]);

                for (int j = 1; j < linha.Length; j++)
                {
                    String strDest = linha[j].Substring(0, linha[j].IndexOf("-"));

                    int a = linha[j].IndexOf("-") + 1, b = linha[j].IndexOf(";")-2;
                    String strPeso = linha[j].Substring(a, b);

                    int vDest = int.Parse(strDest);
                    double peso = double.Parse(strPeso);

                    arestas.Add(new Aresta(vOrigem, vDest, peso));

                    matriz[vOrigem][vDest] = true;
                }
            }
        }
        public int NumVertices() { return numVerts; }
        public ArrayList Vertices() { return vertices; }
        public ArrayList Arestas() { return arestas; }
        public int GrauDoVertice(int vertice)
        {
            int grau = 0;

            for (int i = 0; i < numVerts; i++)
                if (matriz[vertice][i]) grau++;

            return grau;
        }
        public ArrayList ArestaDe(int vertice)
        {
            ArrayList saidas = new ArrayList();

            foreach (Aresta a in arestas)
                if (a.Origem() == vertice) saidas.Add(a);

            return saidas;
        }
        public ArrayList AdjacentesDe(int vertice)
        {
            ArrayList saidas = new ArrayList();

            for (int i=0; i<numVerts; i++)
                if (matriz[vertice][i]) saidas.Add(i);

            return saidas;
        }
        public ArrayList arestasEntre(int origem, int destino)
        {
            ArrayList saidas = new ArrayList();

            foreach (Aresta a in arestas)
                if (a.Destino()==destino && a.Origem()==origem) saidas.Add(a);

            return saidas;
        }
    }
}
