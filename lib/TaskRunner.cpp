#include <iostream>
#include <fstream>
#include <string>
#include <chrono>
#include <algorithm>
#include <cstdlib>
#include <cctype>
// #include "Timer.h"

static void splitPath(const std::string& filePath, std::string& baseNameNoExt, std::string& extension) {
    const std::string::size_type slashPos = filePath.find_last_of("/\\");
    const std::string::size_type nameStart = (slashPos == std::string::npos) ? 0 : slashPos + 1;
    const std::string::size_type dotPos = filePath.find_last_of('.');

    if (dotPos == std::string::npos || dotPos < nameStart) {
        baseNameNoExt = filePath.substr(nameStart);
        extension.clear();
        return;
    }

    baseNameNoExt = filePath.substr(nameStart, dotPos - nameStart);
    extension = filePath.substr(dotPos);
}

void runTaskRunner(const std::string& filePath, const std::string& compileOutDir, const std::string& inputFile, const std::string& outputFile) {
    // Extract file name and extension
    std::string fileBaseNameNoExtension;
    std::string fileExtension;
    splitPath(filePath, fileBaseNameNoExtension, fileExtension);

    std::string compileCommand;
    std::string runCommand;

    // Determine the file type and prepare commands
    if (fileExtension == ".cpp") {
        // Note: ccache on Windows+MinGW is unreliable (known issue). Use g++ directly.
        // For fast compilation, use minimal headers (#include <iostream>, <utility>, <string>) instead of bits/stdc++.h
        compileCommand = "g++ -std=c++17 -O2 -I\"F:\\vault\\CodeLab\\princeton-algorithms-part1\\lib\" -include bits/stdc++.h \"" + filePath + "\" -o \"" + compileOutDir +"\\"+ fileBaseNameNoExtension + ".exe\"";

        runCommand = "\"" + compileOutDir + "\\" + fileBaseNameNoExtension + ".exe\" < \"" + inputFile + "\" > \"" + outputFile + "\"";
    } else if (fileExtension == ".java") {
        compileCommand = "javac \"" + filePath + "\" -d \"" + compileOutDir + "\"";
        // runCommand = "java -Xms16m -Xmx16m -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -XX:+UseSerialGC -cp \"" + compileOutDir + "\" " + fileBaseNameNoExtension + " < \"" + inputFile + "\" > \"" + outputFile + "\"";
        runCommand = "java -cp \"" + compileOutDir + "\" " + fileBaseNameNoExtension + " < \"" + inputFile + "\" > \"" + outputFile + "\"";
    
    } else if (fileExtension == ".py") {
        compileCommand = "";  // Python doesn't need compilation
        runCommand = "python3 \"" + filePath + "\" < \"" + inputFile + "\" > \"" + outputFile + "\"";
    } else if (fileExtension == ".js") {
        compileCommand = "";  // JavaScript doesn't need compilation
        runCommand = "node \"" + filePath + "\" < \"" + inputFile + "\" > \"" + outputFile + "\"";
    }else {
        std::cerr << "Unsupported file type: " << fileExtension << std::endl;
        return;
    }

    // Show commands for transparency
    std::cout << "Compilation command: " << compileCommand << std::endl;
    std::cout << "Execution command:   " << runCommand << std::endl;

        auto captureMs = [](const std::string& cmd) -> std::pair<int, double> {
        if (cmd.empty()) return {0, 0.0};

        auto start = std::chrono::steady_clock::now();
        int code = std::system(cmd.c_str());
        auto end = std::chrono::steady_clock::now();

        double ms = std::chrono::duration_cast<std::chrono::milliseconds>(end - start).count();
        return {code, ms};
        };

    std::pair<int, double> compileResult = captureMs(compileCommand);
    int compileCode = compileResult.first;
    double compileMs = compileResult.second;
    if (compileCode != 0) {
        std::cerr << "Compilation failed (exit code: " << compileCode << ")." << std::endl;
        return;
    }

    std::pair<int, double> runResult = captureMs(runCommand);
    int runCode = runResult.first;
    double runMs = runResult.second;
    if (runCode != 0) {
        std::cerr << "Execution failed (exit code: " << runCode << ")." << std::endl;
        return;
    }

    std::cout << "Compile: " << compileMs << " ms, Run: " << runMs << " ms, Total: " << (compileMs + runMs) << " ms" << std::endl;
}

int main(int argc, char* argv[]) {
    if (argc != 5) {
        std::cerr << "Usage: TaskRunner <filePath> <compileOutDir> <inputFile> <outputFile>" << std::endl;
        return 1;
    }

    std::string filePath = argv[1];
    std::cout << filePath;
    std::string compileOutDir = argv[2];
    std::string inputFile = argv[3];
    std::string outputFile = argv[4];

    std::cout << "Running TaskRunner..." << std::endl;
    std::cout << "File path: " << filePath << std::endl;

    runTaskRunner(filePath, compileOutDir, inputFile, outputFile);

    
    return 0;
}