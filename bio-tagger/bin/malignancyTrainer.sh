#!/bin/sh

java  -Xmx1800m -server \
-classpath "output/biotagger-0.5.jar:\
lib/trove.jar:lib/mallet-no_gnu.jar:lib/mallet-deps.jar:\
lib/maxent.jar:lib/opennlp-tools-1.0.0.jar" \
edu.upenn.cis.taggers.malignancy.MalignancyTrainer train $1 $2 $3

gzip $3

