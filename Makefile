###
#
# Makefile for making "MenschAergereDichNicht"
#
# build/default:   execute javacompiler
# clean:           Remove *.jar-Files
#
###

MAINCLASS=de.lebk.madn.MenschAergereDichNicht
MAINCLASSFILE=de/lebk/madn/MenschAergereDichNicht.java

build:	${MAINCLASSFILE}
		javac ${MAINCLASSFILE}