RunNumericalAnalysis : RunNumericalAnalysis.o NumericalAnalysis.o SomeFunction.o
	g++ SomeFunction.o NumericalAnalysis.o RunNumericalAnalysis.o -o RunNumericalAnalysis

SomeFunction.o : SomeFunction.cc SomeFunction.h
	g++ -Wall -c SomeFunction.cc

NumericalAnalysis.o : NumericalAnalysis.cc NumericalAnalysis.h SomeFunction.h
	g++ -Wall -c NumericalAnalysis.cc

clean:
	rm RunNumericalAnalysis SomeFunction.o NumericalAnalysis.o RunNumericalAnalysis.o

