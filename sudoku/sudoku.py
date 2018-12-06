MAX_Zahl = 9

# zu lösendes Feld
feld = [
    [8, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 3, 6, 0, 0, 0, 0, 0],
    [0, 7, 0, 0, 9, 0, 2, 0, 0],
    [0, 5, 0, 0, 0, 7, 0, 0, 0],
    [0, 0, 0, 0, 4, 5, 7, 0, 0],
    [0, 0, 0, 1, 0, 0, 0, 3, 0],
    [0, 0, 1, 0, 0, 0, 0, 6, 8],
    [0, 0, 8, 5, 0, 0, 0, 1, 0],
    [0, 9, 0, 0, 0, 0, 4, 0, 0]
]

feld254 = [
    [0, 1, 0, 5, 0, 0, 0, 0, 0],
    [9, 0, 4, 0, 0, 0, 0, 0, 8],
    [0, 0, 0, 4, 0, 0, 6, 7, 0],
    [0, 0, 0, 6, 9, 0, 0, 0, 0],
    [5, 8, 0, 0, 0, 0, 0, 9, 7],
    [0, 0, 0, 0, 4, 7, 0, 0, 0],
    [0, 5, 3, 0, 0, 4, 0, 0, 0],
    [2, 0, 0, 0, 0, 0, 3, 0, 6],
    [0, 0, 0, 0, 0, 1, 0, 8, 0]
]


# ==============
# Prüfungen
def pruefeDuplikate(zahlen, zahl):
    counter = 0
    # Für jedes Element
    for i in zahlen:
        if i == zahl:
            counter+=1
        if counter == 2:
            return False
    return True


def pruefeKaestchen(feld, zeile, spalte):
    # Kaestchen = Array der länge 9
    zahlenKaestchen = [0]*MAX_Zahl

    # Identifiziere Kaestchen in 3x3 matrix
    kZeileStart = int(zeile / 3) * 3
    kZeileEnde = int(kZeileStart) + 3
    kSpalteStart = int(spalte / 3) * 3
    kSpalteEnde = int(kSpalteStart) + 3

    # Kopiere Kaestchen - Zahlen
    i = 0
    for z in range(kZeileStart, kZeileEnde):
        for s in range(kSpalteStart, kSpalteEnde):
            zahlenKaestchen[i] = feld[z][s]
            i += 1
    return pruefeDuplikate(zahlenKaestchen, feld[zeile][spalte])


def pruefeZeile(feld, zeile, spalte):
    # Zu pruefende Zahl
    zahl = feld[zeile][spalte]

    # Zeile = Array der länge 9
    zahlenZeile = [0]*MAX_Zahl

    # Kopiere Zeile
    i = 0
    while i < MAX_Zahl:
        zahlenZeile[i] = feld[zeile][i]
        i += 1
    return pruefeDuplikate(zahlenZeile, zahl)


def pruefeSpalte(feld, zeile, spalte):
    # Zu pruefende Zahl
    zahl = feld[zeile][spalte]

    # Spalte = Array der länge 9
    zahlenSpalte = [0]*MAX_Zahl

    # Kopiere Spalte
    i = 0
    while i < MAX_Zahl:
        zahlenSpalte[i] = feld[i][spalte]
        i+=1
    return pruefeDuplikate(zahlenSpalte, zahl)


#================
# SudokuRegeln
# Pruefe Zahl an Stelle(zeile, spalte)
def pruefeZahl(feld, zeile, spalte):
    # Alle Regeln durch UND verknüpft
    return pruefeZeile(feld, zeile, spalte) and pruefeSpalte(feld, zeile, spalte) and pruefeKaestchen(feld, zeile, spalte)


#========================================
# Brute Force = Wir testen alle Zahlen für jedes Feld
# Wir benötigen das Feld als Matrix und geben ein
def loeseSudoku(feld):
    zeile = 0
    while zeile < MAX_Zahl:
        spalte = 0
        while spalte < MAX_Zahl:
            if feld[zeile][spalte] == 0:
                # Falls nicht gelöst: Teste alle Zahlen
                testZahl = 1
                while testZahl <= MAX_Zahl:

                    feld[zeile][spalte] = testZahl
                    # Der eigentliche test mit pruefeZahl()
                    # Tricky! Wenn Valid dann loeseSudoku() erneut ausführen mit bereits gelöstem Zahlenfeld.REKURSION!
                    if pruefeZahl(feld, zeile, spalte) and loeseSudoku(feld):
                        return True
                    testZahl += 1
                    # falls keine valide Zahl möglich:
                    feld[zeile][spalte] = 0
                return False
            spalte += 1
        zeile += 1

    # Richtige Teillösung
    return True


# =========================
# Output
def feldPrint(feld):
    zeile = 0
    spalte = 0
    while zeile < MAX_Zahl:
        while spalte < MAX_Zahl:
            print(feld[zeile][spalte], end='')
            spalte += 1
        print('')
        zeile += 1
        spalte = 0
    print("======================================")


# ======================
# RUN
feldPrint(feld254)
loeseSudoku(feld254)
feldPrint(feld254)

# ENDE
