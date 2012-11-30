# java -classpath "output/biotagger-0.5.jar:lib/mallet.jar:lib/mallet-deps.jar:lib/maxent.jar:lib/opennlp-tools-1.0.0.jar:lib/vartag.jar:class" -Xmx1800m edu.upenn.cis.taggers.frontend.BatchTagger $1 $2 $3 $4
java -classpath "lib/trove.jar:lib/mallet-no_gnu.jar:lib/mallet-deps.jar:lib/maxent.jar:lib/opennlp-tools-1.0.0.jar:output/biotagger-0.5.jar" -Xmx1800m edu.upenn.cis.taggers.frontend.BatchTagger $1 $2 $3 $4

