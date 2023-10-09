from shapely.geometry import Polygon

with open("input.txt") as arq:
    strIni = arq.readline()
    nCaso = 1

    while ((strIni != "0 0") and (strIni != "0 0\n")):
        numsIni = strIni.split(" ")
        numsIni = [int(numsIni[0]),int(numsIni[1])]        

        # Recebe as entradas do 1o grupo
        grpA = []
        for i in range(numsIni[0]):
            strNovo = arq.readline()
            numsNovo = strNovo.split(" ")
            numsNovo = [int(numsNovo[0]),int(numsNovo[1]), int(numsNovo[2]),int(numsNovo[3])]
            p1,p2,p3,p4 = [(numsNovo[0],numsNovo[1]), (numsNovo[0],numsNovo[3]), (numsNovo[2],numsNovo[3]), (numsNovo[2],numsNovo[1])]
            novaBarraca = Polygon([p1,p2,p3,p4])
            grpA.append(novaBarraca)
            
        # Recebe as entradas do 2o grupo
        grpB = []
        for i in range(numsIni[1]):
            strNovo = arq.readline()
            numsNovo = strNovo.split(" ")
            numsNovo = [int(numsNovo[0]),int(numsNovo[1]), int(numsNovo[2]),int(numsNovo[3])]
            p1,p2,p3,p4 = [(numsNovo[0],numsNovo[1]), (numsNovo[0],numsNovo[3]), (numsNovo[2],numsNovo[3]), (numsNovo[2],numsNovo[1])]
            novaBarraca = Polygon([p1,p2,p3,p4])
            grpB.append(novaBarraca)

        # Forma a área maior de A
        vertsA = []
        for poly in grpA:
            vertsA += poly.exterior.coords
        areaA = Polygon(vertsA).convex_hull              

        # Forma a área maior de B
        vertsB = []
        for poly in grpB:
            vertsB += poly.exterior.coords
        areaB = Polygon(vertsB).convex_hull

        # Verifica se houve sobreposição, completa ou parcial, das áreas
        if areaA.covers(areaB) or areaA.overlaps(areaB): print(f"Caso {nCaso}: Não é possível separar os dois grupos!")
        else: print(f"Caso {nCaso}: É possível separar os dois grupos!")
        
        strIni = arq.readline()                 
        nCaso = nCaso + 1
        