# Generierung von Code und JavaDoc

## Codegenerierung
Hinweis: Die zu wechselnden Pfade wurden nicht explizit angegeben.
- git clone git://git.code.sf.net/p/dia2code/git
- git apply constructor-include-throw.patch
- git apply interface-generation.patch
- git apply questionmark-removal.patch
- dia2code class-diagram.dia -t java --buildtree

## Beheben des Codes
- Löschen der Dateien im Hauptverzeichnis, die ohne Package generiert wurden
- Beheben von Imports und anderen kleineren Fehlern im generierten Code (mit IDE)
- hinzufügen von Funktionen zu Enums. Das wird von dia2code ignoriert, ist aber in Java möglich

## JavaDoc
- Download von TeXDoclet (http://doclet.github.io)
- javadoc -docletpath TeXDoclet.jar -doclet org.stfm.texdoclet.TeXDoclet -title "OSIP - Dokumentation" -subtitle "OPC UA Simulator for Industrial Plants" -hr -noinherited -nosummaries -nopackagetoc -subpackages edu.kit.pse.osip
- lualatex docs.tex
  - pdflatex hat ein zu niedriges memory limit, das man aufwändig umstellen muss. Einfacher Fix: LuaLaTeX
