
using CaixeiroViajante;
using System.Collections;


String path = ".\\..\\..\\..\\avaliacao18.txt";
Grafo g = new Grafo(path);
Console.WriteLine("Arquivo usado: "+path);

Object[] solucaoTentErr = Resolucoes.TentativaErro(g);
Console.Write("Solução por Tentativa e Erro:\n - Peso do caminho encontrado: " + solucaoTentErr[0] + "\n - Ordem do caminho encontrado:");
foreach (int v in (int[])solucaoTentErr[1]) Console.Write(" " + v);

/*Object[] solucaoHeurist = Resolucoes.AlgoritmoGenetico(g);
Console.Write("\n\nSolução por Algoritmo Genético:\n - Peso do caminho encontrado: " + solucaoHeurist[0] + "\n - Ordem do caminho encontrado:");
foreach (int v in (int[])solucaoHeurist[1]) Console.Write(" " + v);*/

Console.WriteLine("\n\n\n*****************************              acabou aqui                        *************************************\n\n\n");
