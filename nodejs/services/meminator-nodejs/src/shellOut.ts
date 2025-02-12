
import { spawn } from 'child_process';


type ProcessOutput = {
    code: number;
    stderr: string;
    stdout: string;
}

/**
 * Run a command in the shell, and listen to its stdout and stderr.
 * 
 * @param commandName name of the command to run in the shell
 * @param args args to pass to it
 * @returns a promise that resolves when the process exits with code 0
 */
export function spawnProcess(commandName: string, args: string[]): Promise<ProcessOutput> {
    return new Promise<ProcessOutput>((resolve, reject) => {
        const process = spawn(commandName, args);
        let stderrOutput = '';
        process.stderr.on('data', (data) => {
            stderrOutput += data;
        });

        let stdout = '';
        process.stdout.on('data', (data) => {
            stdout += data;
        });

        process.on('error', (error) => {
            reject(error);
        });

        process.on('close', (code) => {
            if (code !== 0) {
            } else {
                resolve({ code, stdout, stderr: stderrOutput });
            }
        });
    })
}