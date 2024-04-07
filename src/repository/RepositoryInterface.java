package repository;

import exceptions.MyException;
import model.ProgramState;

import java.util.List;

public interface RepositoryInterface {

    //ProgramState getCurrentProgramState() throws MyException;

    void addProgramState(ProgramState newProgramState);

    void logPrgStateExec(ProgramState program) throws MyException;

    void clearFile() throws MyException;

    List<ProgramState> getProgramList();

    void setProgramList(List<ProgramState> newProgramList);
}
