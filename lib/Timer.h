#ifndef TIMER_H
#define TIMER_H

#if defined(_WIN32) || defined(_WIN64)  // Windows platform
    #ifdef BUILD_DLL
        #define EXPORT_SYMBOL __declspec(dllexport)
    #else
        #define EXPORT_SYMBOL __declspec(dllimport)
    #endif
#else  // GCC/Clang (Linux/Unix)
    #define EXPORT_SYMBOL __attribute__((visibility("default")))
#endif

#include <chrono>
#include <iostream>

class EXPORT_SYMBOL Timer {
    private:
        std::chrono::high_resolution_clock::time_point start;

    public:
        Timer();  // Constructor
        ~Timer(); // Destructor
};

#endif // TIMER_H