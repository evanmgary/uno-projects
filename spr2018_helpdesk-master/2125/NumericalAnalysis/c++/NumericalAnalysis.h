#include "SomeFunction.h"
class NumericalAnalysis
{
    public:
        NumericalAnalysis ( );
        ~NumericalAnalysis ( );
        double solveForZero(Function* func, double low, double high);
};
