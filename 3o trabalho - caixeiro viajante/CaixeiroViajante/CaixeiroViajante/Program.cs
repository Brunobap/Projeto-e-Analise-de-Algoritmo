
using CaixeiroViajante;
using System.Collections;

Grafo g = new Grafo("D:\\Programacao\\Projeto-e-Analise-de-Algoritmo\\3o trabalho - caixeiro viajante\\CaixeiroViajante\\CaixeiroViajante\\Teste.txt");

Object[] solucaoTentErr = Resolucoes.TentativaErro(g);
Console.Write("Solução por Tentativa e Erro:\n - Peso do caminho encontrado: " + solucaoTentErr[0] + "\n - Ordem do caminho encontrado:");
foreach (int v in (ArrayList)solucaoTentErr[1]) Console.Write(" "+v);

Console.WriteLine("\n\n\n*****************************              acabou aqui                        *************************************\n\n\n");
