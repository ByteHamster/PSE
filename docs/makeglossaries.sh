#!/bin/bash

rm Pflichtenheft.glg
rm Pflichtenheft.glo
rm Pflichtenheft.gls
rm Pflichtenheft.glsdefs

pdflatex Pflichtenheft
makeglossaries Pflichtenheft
pdflatex Pflichtenheft
pdflatex Pflichtenheft

echo -e "\n\nPress enter to close."
read ignore
