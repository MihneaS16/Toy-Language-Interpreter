package repository;

import exceptions.MyException;
import model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements RepositoryInterface {

    private List<ProgramState> programStatesQueue;
    private final String logFilePath;

    public Repository(String logFilePath) throws MyException {
        programStatesQueue = new ArrayList<>();
        this.logFilePath = logFilePath;
            clearFile();

    }

    @Override
    public void addProgramState(ProgramState newProgramState) {
        programStatesQueue.add(newProgramState);
    }

    @Override
    public void logPrgStateExec(ProgramState program) throws MyException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(program.toString());
            logFile.close();
        } catch (IOException e) {
            throw new MyException("Error opening the file!");
        }
    }

    public void clearFile() throws MyException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, false)));
            logFile.append("");
            logFile.close();
        } catch (IOException e) {
            throw new MyException("Error opening the file!");
        }
    }

    public List<ProgramState> getProgramList() {
        return this.programStatesQueue;
    }

    public void setProgramList(List<ProgramState> newProgramList) {
        this.programStatesQueue = newProgramList;
    }
}

//    @Override
//    public ProgramState getCurrentProgramState() throws MyException {
//        int size = this.programStatesQueue.size();
//        if(size == 0){
//            throw new MyException("Empty list!\n");
//        }
//        return this.programStatesQueue.get(size-1);
//    }
