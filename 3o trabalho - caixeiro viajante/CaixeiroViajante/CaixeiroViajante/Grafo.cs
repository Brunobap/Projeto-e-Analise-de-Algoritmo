using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;

namespace CaixeiroViajante
{
    internal class Grafo
    {
        public int numVerts;
        public double[][] matriz;
        
        public Grafo (String path)
        {

            String[] entrada = File.ReadAllLines(path);
            numVerts = int.Parse(entrada[0]);

            matriz = new double[numVerts][];

            for (int i = 0; i < numVerts; i++)
                matriz[i] = new double[numVerts];

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
        
        public ArrayList AdjacentesDe(int vertice)
        {
            ArrayList saidas = new ArrayList();

            for (int i=0; i<numVerts; i++)
                if (matriz[vertice][i] > 0) saidas.Add(i);

            return saidas;
        }
    }
}
