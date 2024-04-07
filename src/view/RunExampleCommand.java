package view;

import controller.ControllerInterface;

public class RunExampleCommand extends Command{
    private final ControllerInterface controller;

    public RunExampleCommand(String key, String description, ControllerInterface controller){
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute(){
        try{
            controller.allStepExecution();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ControllerInterface getController(){
        return this.controller;
    }

    public String toString(){
        return this.getKey() + " " + this.getDescription();
    }
}
