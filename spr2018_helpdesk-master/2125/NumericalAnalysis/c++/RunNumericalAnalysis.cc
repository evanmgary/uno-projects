#include <stdlib.h>
#include <iostream>
#include <string>
#include "NumericalAnalysis.h"
using namespace std;
int main(int argc, char *argv[]){
    if(argc != 3){
        cout << "Usage: " << argv[0] << " low high" << endl;
        exit(EXIT_FAILURE);
    }
    double low = stod(argv[1]);
    double high = stod(argv[2]);
    NumericalAnalysis myTool;
    SomeFunction *func = new SomeFunction();
    double ans = myTool.solveForZero(func, low, high);
    cout << "The answer is: " << ans << endl;
}
