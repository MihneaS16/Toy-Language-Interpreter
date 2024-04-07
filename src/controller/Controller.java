package controller;

import exceptions.MyException;
import model.ProgramState;
import model.adt.dictionary.DictionaryInterface;
import model.adt.heap.HeapInterface;
import model.adt.stack.StackInterface;
import model.statements.StatementInterface;
import model.values.ReferenceValue;
import model.values.ValueInterface;
import repository.RepositoryInterface;

import java.sql.Ref;
import java.util.Collection;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements ControllerInterface {

    private final RepositoryInterface repository;
    private ExecutorService executor;
    private boolean displayFlag;

    public Controller(RepositoryInterface repository) {
        this.repository = repository;
        this.displayFlag = true;
    }

    public void oneStepForAllPrograms(List<ProgramState> programStatesList) throws InterruptedException {
        programStatesList.forEach(p -> {
            try {
                this.repository.logPrgStateExec(p);
            } catch (MyException e) {
                e.printStackTrace();
            }
        });
        List<Callable<ProgramState>> callList = programStatesList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(() -> p.oneStepExecution()))
                .collect(Collectors.toList());

        List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch(ExecutionException | InterruptedException ex) {
                        System.out.println(ex.toString());
                        executor.shutdownNow();
                    }
                    return null;
                })
                .filter(p -> p!=null)
                .collect(Collectors.toList());

        programStatesList.addAll(newProgramList);
        Collection<ValueInterface> addresses = programStatesList.stream().
                flatMap(program -> program.getSymbolTable().getAllValues().stream())
                .collect(Collectors.toList());

        programStatesList.get(0).getHeap().setContent(this.safeGarbageCollector(this.getAddressesFromSymTable(addresses),
                this.getAddressesFromHeap(programStatesList.get(0).getHeap().getContent()), programStatesList.get(0)));
        programStatesList.forEach(p -> {
            try {
                if(displayFlag) {
                    System.out.println(p);
                }
                this.repository.logPrgStateExec(p);
            } catch (MyException e) {
                e.printStackTrace();
            }
        });

        repository.setProgramList(programStatesList);
    }

    @Override
    public void allStepExecution() throws RuntimeException{
        executor = Executors.newFixedThreadPool(2);

        List<ProgramState> programList = removeCompletedProgram(this.repository.getProgramList());
        while (programList.size() > 0) {

            try {
                oneStepForAllPrograms(programList);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            programList = removeCompletedProgram(repository.getProgramList());
        }
        executor.shutdownNow();

        repository.setProgramList(programList);
    }

    @Override
    public List<ProgramState> removeCompletedProgram(List<ProgramState> inProgramList) {
        return inProgramList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    @Override
    public void addProgramState(ProgramState newProgramState) {
        repository.addProgramState(newProgramState);
    }

    public void setDisplayFlag(boolean value) {
        this.displayFlag = value;
    }

//    private Map<Integer, ValueInterface> unsafeGarbageCollector(List<Integer> symTableAddresses, Map<Integer, ValueInterface> heap) {
//        return heap.entrySet().stream()
//                .filter(e -> symTableAddresses.contains(e.getKey()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//    }

    private Map<Integer, ValueInterface> safeGarbageCollector(List<Integer> symTableAddresses, List<Integer> heapReferencedAddresses, ProgramState currentProgram) {
        HeapInterface<Integer, ValueInterface> heap = currentProgram.getHeap();
        return heap.getContent().entrySet().stream()
                .filter(elem -> symTableAddresses.contains(elem.getKey()) || heapReferencedAddresses.contains(elem.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddressesFromHeap(Map<Integer, ValueInterface> heap) {
        return heap.values().stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getHeapAddress();})
                .collect(Collectors.toList());
    }

    private List<Integer> getAddressesFromSymTable(Collection<ValueInterface> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getHeapAddress();})
                .collect(Collectors.toList());
    }
}

//    @Override
//    public ProgramState oneStepExecution(ProgramState state) throws MyException {
//        StackInterface<StatementInterface> stack = state.getExecutionStack();
//        if(stack.isEmpty()) throw new MyException("ProgramState stack is empty!");
//        StatementInterface currentStatement = stack.pop();
//        return currentStatement.execute(state);
//    }

//    @Override
//    public void allStepExecution() throws MyException {
//        ProgramState programState = repository.getCurrentProgramState();
//        repository.logPrgStateExec(programState);
//        DictionaryInterface<String, ValueInterface> symbolTable = programState.getSymbolTable();
//        StackInterface<StatementInterface> stack = programState.getExecutionStack();
//        HeapInterface<Integer, ValueInterface> heap = programState.getHeap();
//        while(!stack.isEmpty()) {
//            oneStepExecution(programState);
//            if (displayFlag) {
//                System.out.println(programState.toString());
//            }
//            repository.logPrgStateExec(programState);
//            heap.setContent(safeGarbageCollector(getAddressesFromSymTable(symbolTable.getAllValues()), getAddressesFromHeap(heap.getContent()), programState));
//        }
//        if (!displayFlag) {
//            System.out.println(programState.getOutput().toString());
//        }
//    }
