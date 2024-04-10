#include "NumericalAnalysis.h"
#include <stdlib.h>
#include <cmath>
#include <iostream>
//using namespace std;

NumericalAnalysis::NumericalAnalysis( ){ }

NumericalAnalysis::~NumericalAnalysis( ){ }

double NumericalAnalysis::solveForZero(Function* func, double low, double high)
{
    double epsilon = 1;
    double mid = 0;
    while(fabs(epsilon) > 0.0000001)
    {
        mid = low + ((high - low) / 2.0);
        epsilon = func->f(mid);
        if(epsilon > 0){
            high = mid;
        } else if (epsilon < 0){
            low = mid;
        }
    }
    return mid;
}
