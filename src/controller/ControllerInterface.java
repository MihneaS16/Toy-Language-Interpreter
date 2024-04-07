package controller;

import exceptions.MyException;
import model.ProgramState;
import repository.RepositoryInterface;

import java.util.List;

public interface ControllerInterface {

    //ProgramState oneStepExecution(ProgramState state) throws MyException;

    void allStepExecution() throws RuntimeException;

    void addProgramState(ProgramState newProgramState);

    List<ProgramState> removeCompletedProgram(List<ProgramState> inProgramList);

    void oneStepForAllPrograms(List<ProgramState> programStatesList) throws InterruptedException;

}