#include<windows.h>
#include "Timer.h"

// Constructor: Starts the timer
Timer::Timer() {
    start = std::chrono::high_resolution_clock::now();
}

// Destructor: Automatically calculates and prints elapsed time

Timer::~Timer() {
    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double, std::milli> duration = end - start;

    // // Allocate a new console window if needed
    // if (!AttachConsole(ATTACH_PARENT_PROCESS) && !AllocConsole()) {
    //     std::cerr << "Failed to allocate console!" << std::endl;
    //     return;
    // }

    // Redirect stdout and stderr to the console
    FILE* stream;
    freopen_s(&stream, "CONOUT$", "w", stdout);
    freopen_s(&stream, "CONOUT$", "w", stderr);

    // Print output directly to the console
    std::cout << "Execution time: " << duration.count() << " ms" << std::endl;

    // Optional: Detach or free the console if desired
    FreeConsole();
}
