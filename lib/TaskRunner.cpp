#include <iostream>
#include <fstream>
#include <string>
#include <chrono>
#include <algorithm>
#include <cstdlib>
#include <filesystem>
// #include "Timer.h"

namespace fs = std::filesystem;

void runTaskRunner(const std::string& filePath, const std::string& compileOutDir, const std::string& inputFile, const std::string& outputFile) {
    // Extract file name and extension
    fs::path file(filePath);
    std::string fileBaseNameNoExtension = file.stem().string();
    std::string fileExtension = file.extension().string();

    // Convert to relative paths for ccache compatibility (absolute paths break cache hashing)
    fs::path relFilePath = fs::relative(filePath);
    fs::path relCompileOutDir = fs::relative(compileOutDir);

    std::string compileCommand;
    std::string runCommand;

    // Determine the file type and prepare commands
    if (fileExtension == ".cpp") {
        // Note: ccache on Windows+MinGW is unreliable (known issue). Use g++ directly.
        // For fast compilation, use minimal headers (#include <iostream>, <utility>, <string>) instead of bits/stdc++.h
        compileCommand = "g++ -std=c++17 -O2 -I\"F:\\vault\\CodeLab\\princeton-algorithms-part1\\lib\" -include bits/stdc++.h \"" + relFilePath.string() + "\" -o \"" + relCompileOutDir.string() +"\\"+ fileBaseNameNoExtension + ".exe\"";

        runCommand = "\""+relCompileOutDir.string() +"\\"+ fileBaseNameNoExtension + ".exe\" < \"" + inputFile + "\" > \"" + outputFile + "\"";
    } else if (fileExtension == ".java") {
        compileCommand = "javac \"" + relFilePath.string() + "\" -d \"" + relCompileOutDir.string() + "\"";
        // runCommand = "java -Xms16m -Xmx16m -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -XX:+UseSerialGC -cp \"" + relCompileOutDir.string() + "\" " + fileBaseNameNoExtension + " < \"" + inputFile + "\" > \"" + outputFile + "\"";
        runCommand = "java -cp \"" + relCompileOutDir.string() + "\" " + fileBaseNameNoExtension + " < \"" + inputFile + "\" > \"" + outputFile + "\"";
    
    } else if (fileExtension == ".py") {
        compileCommand = "";  // Python doesn't need compilation
        runCommand = "python3 \"" + relFilePath.string() + "\" < \"" + inputFile + "\" > \"" + outputFile + "\"";
    } else if (fileExtension == ".js") {
        compileCommand = "";  // JavaScript doesn't need compilation
        runCommand = "node \"" + relFilePath.string() + "\" < \"" + inputFile + "\" > \"" + outputFile + "\"";
    }else {
        std::cerr << "Unsupported file type: " << fileExtension << std::endl;
        return;
    }

    // Show commands for transparency
    std::cout << "Compilation command: " << compileCommand << std::endl;
    std::cout << "Execution command:   " << runCommand << std::endl;

    auto captureMs = [](const std::string& cmd) -> std::pair<int, double> {
        std::string ps =
            "powershell -NoLogo -NoProfile -NonInteractive -Command \"$sw=[System.Diagnostics.Stopwatch]::StartNew(); cmd /c '" +
            cmd +
            "'; $code=$LASTEXITCODE; $sw.Stop(); if($code -ne 0){ exit $code }; [int]$sw.ElapsedMilliseconds\"";

#ifdef _WIN32
        FILE* pipe = _popen(ps.c_str(), "r");
#else
        FILE* pipe = popen(ps.c_str(), "r");
#endif
        if (!pipe) return {1, 0.0};
        std::string out; char buffer[256];
        while (fgets(buffer, sizeof(buffer), pipe)) out += buffer;
#ifdef _WIN32
        int code = _pclose(pipe);
#else
        int code = pclose(pipe);
#endif
        // Trim whitespace
        auto isspace_lambda = [](unsigned char c){ return std::isspace(c) != 0; };
        out.erase(out.begin(), std::find_if(out.begin(), out.end(), [&](unsigned char c){return !isspace_lambda(c);}));
        out.erase(std::find_if(out.rbegin(), out.rend(), [&](unsigned char c){return !isspace_lambda(c);}).base(), out.end());
        double ms = 0.0;
        try { if (!out.empty()) ms = std::stod(out); } catch (...) { /* ignore parse error */ }
        return {code, ms};
    };

    auto [compileCode, compileMs] = captureMs(compileCommand);
    if (compileCode != 0) {
        std::cerr << "Compilation failed (exit code: " << compileCode << ")." << std::endl;
        return;
    }

    auto [runCode, runMs] = captureMs(runCommand);
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