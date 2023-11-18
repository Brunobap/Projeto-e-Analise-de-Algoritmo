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

            ArrayList todosOsCaminhos = new ArrayList();

            ArrayList start = new ArrayList();
            start.Add(0);

            todosOsCaminhos.Add(start);

            int aux = 0;

            // Pegar todos os caminhos possíveis
            while (aux < todosOsCaminhos.Count)
            {
                ArrayList caminho = (ArrayList)todosOsCaminhos[aux];
                int ultimo = caminho.Count-1;
                foreach (Aresta a in g.ArestaDe((int)caminho[ultimo]))
                {
                    if (!caminho.Contains(a.Destino()))
                    {
                        ArrayList clone = (ArrayList) caminho.Clone();
                        clone.Add(a.Destino());
                        todosOsCaminhos.Add(clone);
                    }
                }
                if (ultimo < g.NumVertices() - 1)
                    todosOsCaminhos.Remove(caminho);
                else
                {
                    aux++;
                    caminho.Add(0);
                }
            }

            // Pegar o peso do 1o caminho na lista, e tirar esse caminho da lista
            double pesoCaminho = 0;
            ArrayList arrayCaminho = (ArrayList)todosOsCaminhos[0];
            for (int i = 0; i < g.NumVertices(); i++)
            {
                int origem = (int)arrayCaminho[i], destino = (int)arrayCaminho[i + 1]; 
                Aresta passo = (Aresta)g.arestasEntre(origem,destino)[0];
                pesoCaminho += passo.Peso();
            }
            todosOsCaminhos.RemoveAt(0);

            // Comparar os caminhos entre si
            foreach (ArrayList caminho in todosOsCaminhos)
            {
                // Pegar o peso do caminho 
                double pesoComp = 0;
                for (int i = 0; i < g.NumVertices(); i++)
                {
                    int origem = (int)caminho[i], destino = (int)caminho[i + 1];
                    Aresta passo = (Aresta)g.arestasEntre(origem, destino)[0];
                    pesoComp += passo.Peso();
                }

                // Achou um caminho menor, ele agora é o escolhido
                if (pesoComp < pesoCaminho)
                {
                    arrayCaminho = caminho;
                    pesoCaminho = pesoComp;
                }
            }


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
