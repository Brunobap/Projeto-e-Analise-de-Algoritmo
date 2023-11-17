using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CaixeiroViajante
{
    internal class Resolucoes
    {
        /*
         * Modelo de "Problemas de Satisfação de Restrições":
         *      Backtrack-DFS (A, k, Sk)
         *          if A = (a1, a2, ..., aN) is a solution then report it.
         *          else
         *              k = k + 1
         *              compute Sk
         *              while Sk is not empty
         *                  aK = an element from Sk
         *                  Sk = Sk - {aK}
         *                  Backtrack-DFS (A, k ,Sk)
         *              k = k - 1
         */
        public static object[] TentativaErro(Grafo g)
        {
            Object[] saida = new Object[2];
            double pesoCaminho = 0;
            int[] arrayCaminho = new int[g.NumVertices()];


            saida[0] = pesoCaminho;
            saida[1] = arrayCaminho;
            return saida;
        }
        public static object[] AlgoritmoGenetico(Grafo g)
        {
            Object[] saida = new Object[2];
            double pesoCaminho = 0;
            int[] arrayCaminho = new int[g.NumVertices()];
            ArrayList faltam = (ArrayList) g.Vertices().Clone();
            faltam.RemoveAt(0);

            saida[0] = pesoCaminho;
            saida[1] = arrayCaminho;
            return saida;
        }
    }
}
