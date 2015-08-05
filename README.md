# AbgabeThamarVainstain
Ein Plugin, um geheime Tunnel und ein Item-Glücksrad zu erschaffen.
Geheimer Tunnel:
Wenn der Spieler eine Eisen-Spitzhacke in Händen hält, und /Tunnel eingibt, entsteht ein Tunnel unter dem Spieler in Blickrichtung,
in die er sieht, und ein Ausgang am anderen Ende. 

Item-Glücksrad:
Aufgrund diverser Probleme mit der Tatsache, dass man einem Item keine Position geben kann, und ItemRahmen am Rad nicht hielten, für die ich keine Funktionierende Lösung finden konnte drehen sich die Items leider nicht.
Bei der Eingabe des  /FortuneWheel wird ein Glücksrad gebaut. Klickt man den Obsidianblock in der mitte des Rades, so
erhält man ein zufälliges Item. Bis man das nächste mal drehen darf, muss man warten (derzeit 2 Stunden). Man darf auch an mehreren 
Glücksrädern nur einmal pro 2 Stunden drehen.

Um die InteliJ Datei zu nutzen, muss auf folgende Art und Weise ein Jar File erstellt werden:
Öffne InteliJ, öffne in InteliJ den heruntergeladenen Dateiornder ThamarVainstainAbgabe 
gehe dann auf File->Project Structure->Libraries
gehe auf das grüne Plus, dann auf Java, und wähle die Spigot-Library aus und klicke auf Apply
gehe dann in Project-Structure auf Artifacts. Auch dort auf das Grüne Plus->Jar->from Modules with dependencies
in der Mitte müsste nun AbgabeThamarVainstain.Jar stehen. wähle rechts mit einem Doppelklick sowohl AbgabeThamarVainstain-compile output als auch spigot aus.
Nun auf ok, und unter Build->Build Artifacts auswählen.
Im Dateiordner sollte sich nun ein Ordner out befinden, in dem sich die fertige Jar-datei befindet.
