compile:
	javac Main.java turing/*.java

jar: compile
	jar cmvf META-INF/MANIFEST.MF Simulator.jar Main.class turing/*.class examples/*

clean:
	rm -rf *.class turing/*.class
	rm Simulator.jar
