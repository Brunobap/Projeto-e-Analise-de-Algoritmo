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

            int[] start = new int[1];
            start[0] = 0;

            double pesoCaminho = double.MaxValue;

            todosOsCaminhos.Add(start);

            int aux = 0;

            // Pegar todos os caminhos possíveis
            while (aux < todosOsCaminhos.Count)
            {
                int[] caminho = (int[]) todosOsCaminhos[aux];
                int ultimo = caminho.Length-1;
                foreach (Aresta a in g.ArestaDe((int) caminho[ultimo]))
                {
                    double auxPeso = 0;
                    for (int i = 0; i < caminho.Length-1; i++)
                    {
                        int origem = caminho[i], destino = caminho[i + 1];
                        auxPeso += g.arestaEntre(origem, destino);
                    }

                    if (!caminho.Contains(a.Destino()) && auxPeso < pesoCaminho)
                    {
                        int[] copia = new int[caminho.Length + 1];
                        caminho.CopyTo(copia, 0);
                        copia[copia.Length-1] = a.Destino();
                        todosOsCaminhos.Insert(aux,copia);
                    }
                }
                if (ultimo < g.NumVertices() - 1)
                    todosOsCaminhos.Remove(caminho);
                else
                {
                    int[] copia = new int[caminho.Length+1];
                    caminho.CopyTo(copia, 0);
                    double auxPeso = 0; 
                    for (int i = 0; i < copia.Length-1; i++)
                    {
                        int origem = copia[i], destino = copia[i + 1];
                        auxPeso += g.arestaEntre(origem, destino);
                    }
                    if (auxPeso < pesoCaminho)
                    {
                        pesoCaminho = auxPeso;
                        todosOsCaminhos[0] = copia;
                        aux = 1;
                    }
                    else
                        todosOsCaminhos.Remove(caminho);             
                }

            }

            // Pegar o peso do 1o caminho na lista, e tirar esse caminho da lista
            int[] arrayCaminho = (int[]) todosOsCaminhos[0];
            todosOsCaminhos.RemoveAt(0);

            // Comparar os caminhos entre si
            foreach (int[] caminho in todosOsCaminhos)
            {
                // Pegar o peso do caminho 
                double pesoComp = 0;
                for (int i = 0; i < g.NumVertices(); i++)
                {
                    int origem = (int)caminho[i], destino = (int)caminho[i + 1];
                    pesoComp += g.arestaEntre(origem, destino);
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
