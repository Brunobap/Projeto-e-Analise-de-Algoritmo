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
         * Modelo de "Problemas de Satisfação de Restrições": (não usado, até o momento)
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
                for (int a=0; a<g.numVerts; a++)
                {
                    if (g.matriz[caminho[ultimo]][a] > 0)
                    {
                        double auxPeso = 0;
                        for (int i = 0; i < caminho.Length - 1; i++)
                        {
                            int origem = caminho[i], destino = caminho[i + 1];
                            auxPeso += g.matriz[origem][destino];
                        }
                        if (!caminho.Contains(a) && auxPeso < pesoCaminho)
                        {
                            int[] copia = new int[caminho.Length + 1];
                            caminho.CopyTo(copia, 0);
                            copia[copia.Length - 1] = a;
                            todosOsCaminhos.Insert(todosOsCaminhos.IndexOf(caminho), copia);
                        }
                    }
                }
                if (caminho.Length < g.numVerts)
                    todosOsCaminhos.Remove(caminho);
                else
                {
                    int[] copia = new int[caminho.Length+1];
                    caminho.CopyTo(copia, 0);
                    double auxPeso = 0; 
                    for (int i = 0; i < copia.Length-1; i++)
                    {
                        int origem = copia[i], destino = copia[i + 1];
                        auxPeso += g.matriz[origem][destino];
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
                for (int i = 0; i < g.numVerts; i++)
                {
                    int origem = (int)caminho[i], destino = (int)caminho[i + 1];
                    pesoComp += g.matriz[origem][destino];
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

        /*
         * Modelo Algoritmo-Genetico:
         *      BEGIN
         *          INICIALISE population with random candidate solutions;
         *          EVALUATE each candidate;
         *          REPEAT UNTIL (TERMINATION CONDITION is satisfied) DO
         *              1 SELECT parents;
         *              2 RECOMBINE pairs of parents;
         *              3 MUTATE the resulting offspring;
         *              4 EVALUATE new candidates;
         *              5 SELECT individuals for the next generation;
         *          END REPEAT
         *      END BEGIN
         */
        public static object[] AlgoritmoGenetico(Grafo g)
        {
            Random r = new Random();

            #region INICIO Inicializar uma população random com respostas possíveis e calcular os seus valores;
            ArrayList pop = new ArrayList();
            ArrayList fit = new ArrayList();
            for (int i = 0; i < 6; i++)
            {
                int[] cam = new int[g.numVerts+1];
                double peso = 0;
                for (int j = 1; j < g.numVerts;)
                {
                    int aux = r.Next(1, g.numVerts);
                    if (!cam.Contains(aux))
                    {
                        cam[j] = aux;
                        peso += g.matriz[cam[j-1]][aux];
                        j++;
                    }
                }
                pop.Add(cam); fit.Add(peso);
            }
            #endregion

            // Evoluir por nE+6 gerações
            for (int i = 0; i < g.numVerts*1E2; i++)
            {
                #region 1 Selecionar os pais/sobreviventes da geração antiga
                for (int j = 0; j < 6; j++)
                {
                    int menores = 0;
                    foreach (double peso in fit)
                        if ((double)fit[j] > peso) menores++;
                    int[] cam = (int[])pop[j];
                    double pesoCam = (double)fit[j];
                    pop.RemoveAt(j);
                    fit.RemoveAt(j);
                    pop.Insert(menores, cam);
                    fit.Insert(menores, pesoCam);
                }
                pop.RemoveRange(2, 4);
                #endregion

                #region 2 Recombinar os genes dos pais;
                int[] pai = (int[])pop[0], mae = (int[])pop[1];
                int[] filho00 = (int[])pai.Clone(), filho01 = (int[])pai.Clone(), filho10 = (int[])mae.Clone(), filho11 = (int[])mae.Clone();

                    // Recortar os genes
                int corte1 = r.Next(1, g.numVerts), corte2 = r.Next(1, g.numVerts);
                int aux = 1;
                while (aux <= corte1) filho00[aux++] = 0;
                aux = g.numVerts - 1;
                while (aux >= corte1) filho01[aux--] = 0;
                aux = 1;
                while (aux <= corte2) filho10[aux++] = 0;
                aux = g.numVerts - 1;
                while (aux >= corte2) filho11[aux--] = 0;

                    // Colocar as sequências dos parceiros
                aux = 1;
                for (int v = 1; v < g.numVerts; v++) if (!filho00.Contains(mae[v])) filho00[aux++] = mae[v];
                aux = g.numVerts - 1;
                for (int v = g.numVerts - 1; v > 0; v--) if (!filho01.Contains(mae[v])) filho01[aux--] = mae[v];
                aux = 1;
                for (int v = 1; v < g.numVerts; v++) if (!filho10.Contains(pai[v])) filho10[aux++] = pai[v];
                aux = g.numVerts - 1;
                for (int v = g.numVerts - 1; v > 0; v--) if (!filho11.Contains(pai[v])) filho11[aux--] = pai[v];

                    // Colocar os filhos na população
                pop.Add(filho00);   pop.Add(filho01);   pop.Add(filho10);   pop.Add(filho11);
                #endregion

                #region 3 Mutar os genes dos filhos (troca de posição dos genes)
                corte1 = r.Next(1, g.numVerts);
                corte2 = r.Next(1, g.numVerts);
                aux = filho00[corte1];
                filho00[corte1] = filho00[corte2];
                filho00[corte2] = aux;

                corte1 = r.Next(1, g.numVerts);
                corte2 = r.Next(1, g.numVerts);
                aux = filho01[corte1];
                filho01[corte1] = filho01[corte2];
                filho01[corte2] = aux;

                corte1 = r.Next(1, g.numVerts);
                corte2 = r.Next(1, g.numVerts);
                aux = filho10[corte1];
                filho10[corte1] = filho10[corte2];
                filho10[corte2] = aux;

                corte1 = r.Next(1, g.numVerts);
                corte2 = r.Next(1, g.numVerts);
                aux = filho11[corte1];
                filho11[corte1] = filho11[corte2];
                filho11[corte2] = aux;
                #endregion

                #region 4 Avaliar a nova população
                fit = new ArrayList();
                for (int j = 0; j < 6; j++)
                {
                    int[] cam = (int[])pop[j];
                    double peso = 0;
                    for (int k = 1; k < g.numVerts; k++) peso += g.matriz[cam[k - 1]][cam[k]];
                    fit.Add(peso);
                }
                #endregion
            }

            #region FIM Pegar o menor caminho feito para exibir na tela
            Object[] saida = new Object[2];
            int[] menorCam = (int[])pop[0];
            double menorPeso = (double)fit[0];
            foreach (double peso in fit)
                if (peso < menorPeso)
                {
                    menorPeso = peso;
                    menorCam = (int[])pop[fit.IndexOf(peso)];
                }
            saida[0] = menorPeso;
            saida[1] = menorCam;
            return saida;
            #endregion
        }
    }
}
