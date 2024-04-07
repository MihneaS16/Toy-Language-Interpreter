package view;

import controller.Controller;
import controller.ControllerInterface;
import exceptions.MyException;
import model.ProgramState;
import model.adt.dictionary.DictionaryInterface;
import model.adt.dictionary.MyDictionary;
import model.adt.heap.MyHeap;
import model.adt.list.MyList;
import model.adt.stack.MyStack;
import model.expressions.*;
import model.statements.*;
import model.statements.files.CloseReadFileStatement;
import model.statements.files.OpenReadFileStatement;
import model.statements.files.ReadFileStatement;
import model.statements.heap.HeapAllocationStatement;
import model.statements.heap.HeapWritingStatement;
import model.types.*;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import model.values.ValueInterface;
import repository.Repository;
import repository.RepositoryInterface;

import java.io.BufferedReader;

public class View {
    public static void main(String[] args) throws MyException {
        TextMenu textMenu = new TextMenu();

        String FOLDER_PATH = "D:\\IntelliJ IDEA Projects\\ToyLanguageInterpreter";

        //Example 1 int v; v=2; Print(v)
        StatementInterface declareV = new VariableDeclarationStatement("v", new IntType());
        StatementInterface assignV = new AssignmentStatement("v", new ValueExpression(new IntValue(2)));
        StatementInterface printV = new PrintStatement(new VariableExpression("v"));


        StatementInterface programExample1 = new CompoundStatement(declareV, new CompoundStatement(assignV, printV));
        try {
            DictionaryInterface<String, TypeInterface> typeEnvironment1 = new MyDictionary<String, TypeInterface>();
            programExample1.typeCheck(typeEnvironment1);
            ProgramState programState1 = new ProgramState(new MyStack<StatementInterface>(), new MyDictionary<String, ValueInterface>(), new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), programExample1);
            RepositoryInterface repository1 = new Repository(FOLDER_PATH + "\\log1.in");
            Controller controller1 = new Controller(repository1);
            controller1.addProgramState(programState1);
            textMenu.addCommand(new RunExampleCommand("1", " int v; v=2; Print(v)", controller1));
        }catch (MyException e) {
            System.out.println("1: " + e.getMessage());
        }

        //Example 2 int a; int b; a=2+3*5; b=a+1; Print(b)
        StatementInterface declareA = new VariableDeclarationStatement("a", new IntType());
        StatementInterface declareB = new VariableDeclarationStatement("b", new BoolType());
        ExpressionInterface multiplyA = new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), 3);
        ExpressionInterface addA = new ArithmeticExpression(multiplyA, new ValueExpression(new IntValue(2)), 1);
        StatementInterface assignA = new AssignmentStatement("a", addA);
        ExpressionInterface addB = new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)), 1);
        StatementInterface assignB = new AssignmentStatement("b", addB);
        StatementInterface printB = new PrintStatement(new VariableExpression("b"));

        StatementInterface programExample2 = new CompoundStatement(declareA, new CompoundStatement(declareB, new CompoundStatement(assignA, new CompoundStatement(assignB, printB))));
        try {
            DictionaryInterface<String, TypeInterface> typeEnvironment2 = new MyDictionary<String, TypeInterface>();
            programExample2.typeCheck(typeEnvironment2);
            ProgramState programState2 = new ProgramState(new MyStack<StatementInterface>(), new MyDictionary<String, ValueInterface>(), new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), programExample2);
            RepositoryInterface repository2 = new Repository(FOLDER_PATH + "\\log2.in");
            Controller controller2 = new Controller(repository2);
            controller2.addProgramState(programState2);
            textMenu.addCommand(new RunExampleCommand("2", "int a; int b; a=2+3*5; b=a+1; Print(b)", controller2));
        }catch (MyException e) {
            System.out.println("2: " + e.getMessage());
        }



        //Example 3 bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        StatementInterface declareA2 = new VariableDeclarationStatement("a", new BoolType());
        StatementInterface declareV2 = new VariableDeclarationStatement("v", new IntType());
        StatementInterface assignA2 = new AssignmentStatement("a", new ValueExpression(new BoolValue(true)));
        StatementInterface assignV1 = new AssignmentStatement("v", new ValueExpression(new IntValue(2)));
        StatementInterface assignV2 = new AssignmentStatement("v", new ValueExpression(new IntValue(3)));
        StatementInterface ifStatement = new IfStatement(new VariableExpression("a"), assignV1, assignV2);
        StatementInterface printV2 = new PrintStatement(new VariableExpression("v"));

        StatementInterface programExample3 = new CompoundStatement(declareA2, new CompoundStatement(declareV2, new CompoundStatement(assignA2, new CompoundStatement(ifStatement, printV2))));
        try {
            DictionaryInterface<String, TypeInterface> typeEnvironment3 = new MyDictionary<String, TypeInterface>();
            programExample3.typeCheck(typeEnvironment3);
            ProgramState programState3 = new ProgramState(new MyStack<StatementInterface>(), new MyDictionary<String, ValueInterface>(), new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), programExample3);
            RepositoryInterface repository3 = new Repository(FOLDER_PATH + "\\log3.in");
            Controller controller3 = new Controller(repository3);
            controller3.addProgramState(programState3);
            textMenu.addCommand(new RunExampleCommand("3", "bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)", controller3));
        }catch (MyException e) {
            System.out.println("3: " + e.getMessage());
        }


        ///Example 4
        StatementInterface stringDeclaration = new VariableDeclarationStatement("varf", new StringType());
        StatementInterface assignment = new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in")));
        StatementInterface open = new OpenReadFileStatement(new VariableExpression("varf"));
        StatementInterface intDeclaration = new VariableDeclarationStatement("varc", new IntType());
        StatementInterface readFile = new ReadFileStatement(new VariableExpression("varf"), "varc");
        StatementInterface print = new PrintStatement(new VariableExpression("varc"));
        StatementInterface close = new CloseReadFileStatement(new VariableExpression("varf"));

        StatementInterface programExample4 = new CompoundStatement(stringDeclaration, new CompoundStatement(assignment, new CompoundStatement(open,
                new CompoundStatement(intDeclaration, new CompoundStatement(readFile, new CompoundStatement(print,
                        new CompoundStatement(readFile, new CompoundStatement(print, close))))))));
        try {
            DictionaryInterface<String, TypeInterface> typeEnvironment4 = new MyDictionary<String, TypeInterface>();
            programExample4.typeCheck(typeEnvironment4);

            ProgramState programState4 = new ProgramState(new MyStack<StatementInterface>(), new MyDictionary<String, ValueInterface>(),
                    new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), programExample4);
            RepositoryInterface repository4 = new Repository(FOLDER_PATH + "\\log4.in");
            Controller controller4 = new Controller(repository4);
            controller4.addProgramState(programState4);
            textMenu.addCommand(new RunExampleCommand("4", "string varf; " +
                    " varf=\"test.in\"; " +
                    " openRFile(varf); " +
                    " int varc; " +
                    " readFile(varf,varc) ;print(varc); " +
                    " readFile(varf,varc); print(varc) " +
                    " closeRFile(varf) ", controller4));
        }catch (MyException e) {
            System.out.println("4: " + e.getMessage());
        }


        /// EXAMPLE 5
        ///int x; x = 56; int y; y = 46; IF (x > y) THEN print(x) ELSE print(y)

        StatementInterface declareX = new VariableDeclarationStatement("x", new IntType());
        StatementInterface assignX = new AssignmentStatement("x", new ValueExpression(new IntValue(56)));
        StatementInterface declareY = new VariableDeclarationStatement("y", new IntType());
        StatementInterface assignY = new AssignmentStatement("y", new ValueExpression(new IntValue(46)));
        ExpressionInterface relationalExpression = new RelationalExpression(new VariableExpression("x"), new VariableExpression("y"), "<");
        StatementInterface printX = new PrintStatement(new VariableExpression("x"));
        StatementInterface printY = new PrintStatement(new VariableExpression("y"));
        StatementInterface ifStatement5 = new IfStatement(relationalExpression, printX, printY);

        StatementInterface programExample5 = new CompoundStatement(declareX, new CompoundStatement(assignX,
                new CompoundStatement(declareY, new CompoundStatement(assignY, ifStatement5))));
        try {
            DictionaryInterface<String, TypeInterface> typeEnvironment5 = new MyDictionary<String, TypeInterface>();
            programExample5.typeCheck(typeEnvironment5);

            ProgramState programState5 = new ProgramState(new MyStack<>(), new MyDictionary<String, ValueInterface>(),
                    new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), programExample5);
            RepositoryInterface repository5 = new Repository(FOLDER_PATH + "\\log5.in");
            Controller controller5 = new Controller(repository5);
            controller5.addProgramState(programState5);
            textMenu.addCommand(new RunExampleCommand("5", "int x; x = 25; int y; y = 30; IF (x < y) THEN print(x) ELSE print(y)", controller5));
        }catch (MyException e) {
            System.out.println("5: " + e.getMessage());
        }




        ///EXAMPLE 6
        ///Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)

        StatementInterface declareV6 = new VariableDeclarationStatement("v", new ReferenceType(new IntType()));
        StatementInterface allocV6 = new HeapAllocationStatement("v", new ValueExpression(new IntValue(20)));
        StatementInterface declareA6 = new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType())));
        StatementInterface allocA6 = new HeapAllocationStatement("a", new VariableExpression("v"));
        ExpressionInterface readV6 = new HeapReadingExpression(new VariableExpression("v"));
        StatementInterface printV6 = new PrintStatement(readV6);
        ExpressionInterface readA6 = new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a")));
        ExpressionInterface add6 = new ArithmeticExpression(readA6, new ValueExpression(new IntValue(5)), 1);
        StatementInterface printA6 = new PrintStatement(add6);

        StatementInterface programExample6 = new CompoundStatement(declareV6, new CompoundStatement(allocV6, new CompoundStatement(declareA6,
                new CompoundStatement(allocA6, new CompoundStatement(printV6, printA6)))));
        try {
            DictionaryInterface<String, TypeInterface> typeEnvironment6 = new MyDictionary<String, TypeInterface>();
            programExample6.typeCheck(typeEnvironment6);

            ProgramState programState6 = new ProgramState(new MyStack<>(), new MyDictionary<String, ValueInterface>(),
                    new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), programExample6);
            RepositoryInterface repository6 = new Repository(FOLDER_PATH + "\\log6.in");
            Controller controller6 = new Controller(repository6);
            controller6.addProgramState(programState6);
            textMenu.addCommand(new RunExampleCommand("6", "Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)", controller6));
        }catch (MyException e) {
            System.out.println("6: " + e.getMessage());
        }



        ///EXAMPLE 7
        ///Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);

        StatementInterface declareV7 = new VariableDeclarationStatement("v", new ReferenceType(new IntType()));
        StatementInterface allocV7 = new HeapAllocationStatement("v", new ValueExpression(new IntValue(20)));
        ExpressionInterface readV7 = new HeapReadingExpression(new VariableExpression("v"));
        StatementInterface printV7 = new PrintStatement(readV7);
        StatementInterface writeV7 = new HeapWritingStatement("v", new ValueExpression(new IntValue(30)));
        ExpressionInterface readV72 = new HeapReadingExpression(new VariableExpression("v"));
        ExpressionInterface add7 = new ArithmeticExpression(readV72, new ValueExpression(new IntValue(5)), 1);
        StatementInterface printV72 = new PrintStatement(add7);

        StatementInterface programExample7 = new CompoundStatement(declareV7, new CompoundStatement(allocV7, new CompoundStatement(printV7,
                new CompoundStatement(writeV7, printV72))));
        try {
            DictionaryInterface<String, TypeInterface> typeEnvironment7 = new MyDictionary<String, TypeInterface>();
            programExample7.typeCheck(typeEnvironment7);

            ProgramState programState7 = new ProgramState(new MyStack<>(), new MyDictionary<String, ValueInterface>(),
                    new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), programExample7);
            RepositoryInterface repository7 = new Repository(FOLDER_PATH + "\\log7.in");
            Controller controller7 = new Controller(repository7);
            controller7.addProgramState(programState7);
            textMenu.addCommand(new RunExampleCommand("7", "Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);", controller7));
        }catch (MyException e) {
            System.out.println("7: " + e.getMessage());
        }




        ///EXAMPLE 8
        ///Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))

        StatementInterface declareV8 = new VariableDeclarationStatement("v", new ReferenceType(new IntType()));
        StatementInterface allocV81 = new HeapAllocationStatement("v", new ValueExpression(new IntValue(20)));
        StatementInterface declareA8 = new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType())));
        StatementInterface allocA8 = new HeapAllocationStatement("a", new VariableExpression("v"));
        StatementInterface allocV82 = new HeapAllocationStatement("v", new ValueExpression(new IntValue(30)));
        ExpressionInterface readA1 = new HeapReadingExpression(new VariableExpression("a"));
        ExpressionInterface readA2 = new HeapReadingExpression(readA1);
        StatementInterface printA8 = new PrintStatement(readA2);

        StatementInterface programExample8 = new CompoundStatement(declareV8, new CompoundStatement(allocV81, new CompoundStatement(declareA8,
                new CompoundStatement(allocA8, new CompoundStatement(allocV82, printA8)))));
        try {
            DictionaryInterface<String, TypeInterface> typeEnvironment8 = new MyDictionary<String, TypeInterface>();
            programExample8.typeCheck(typeEnvironment8);

            ProgramState programState8 = new ProgramState(new MyStack<>(), new MyDictionary<String, ValueInterface>(),
                    new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), programExample8);
            RepositoryInterface repository8 = new Repository(FOLDER_PATH + "\\log8.in");
            ControllerInterface controller8 = new Controller(repository8);
            controller8.addProgramState(programState8);
            textMenu.addCommand(new RunExampleCommand("8", "Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))", controller8));
        }catch (MyException e) {
            System.out.println("8: " + e.getMessage());
        }



        ///EXAMPLE 9
        ///int v; v=4; (while (v>0) print(v);v=v-1);print(v)

        StatementInterface declareV9 = new VariableDeclarationStatement("v", new IntType());
        StatementInterface assignV91 = new AssignmentStatement("v", new ValueExpression(new IntValue(4)));
        ExpressionInterface relExpr9 = new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">");
        StatementInterface printV91 = new PrintStatement(new VariableExpression("v"));
        ExpressionInterface arithmeticV9 = new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), 2);
        StatementInterface assignV92 = new AssignmentStatement("v", arithmeticV9);
        StatementInterface compoundStatementV9 = new CompoundStatement(printV91, assignV92);
        StatementInterface whileStatementV9 = new WhileStatement(relExpr9, compoundStatementV9);
        StatementInterface printV92 = new PrintStatement(new VariableExpression("v"));

        StatementInterface programExample9 = new CompoundStatement(declareV9, new CompoundStatement(assignV91, new CompoundStatement(whileStatementV9, printV92)));
        try {
            DictionaryInterface<String, TypeInterface> typeEnvironment9 = new MyDictionary<String, TypeInterface>();
            programExample9.typeCheck(typeEnvironment9);

            ProgramState programState9 = new ProgramState(new MyStack<>(), new MyDictionary<String, ValueInterface>(),
                    new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), programExample9);
            RepositoryInterface repository9 = new Repository(FOLDER_PATH + "\\log9.in");
            ControllerInterface controller9 = new Controller(repository9);
            controller9.addProgramState(programState9);
            textMenu.addCommand(new RunExampleCommand("9", "int v; v=4; (while (v>0) print(v);v=v-1);print(v)", controller9));
        }catch (MyException e) {
            System.out.println("9: " + e.getMessage());
        }




        // EXAMPLE 10
        StatementInterface declareV10 = new VariableDeclarationStatement("v", new ReferenceType(new IntType()));
        StatementInterface allocV101 = new HeapAllocationStatement("v", new ValueExpression(new IntValue(20)));
        StatementInterface declareA10 = new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType())));
        StatementInterface allocA10 = new HeapAllocationStatement("a", new VariableExpression("v"));
        StatementInterface allocV102 = new HeapAllocationStatement("v", new ValueExpression(new IntValue(30)));
        StatementInterface declareF10 = new VariableDeclarationStatement("f", new ReferenceType(new IntType()));
        StatementInterface allocF101 = new HeapAllocationStatement("f", new ValueExpression(new IntValue(123)));
        StatementInterface allocF102 = new HeapAllocationStatement("f", new ValueExpression(new IntValue(100)));
        StatementInterface printA10 = new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))));

        StatementInterface programExample10 = new CompoundStatement(declareV10, new CompoundStatement(allocV101, new CompoundStatement(declareA10,
                new CompoundStatement(allocA10, new CompoundStatement(allocV102, new CompoundStatement(declareF10, new CompoundStatement(allocF101, new CompoundStatement(allocF102, printA10))))))));
        try {
            DictionaryInterface<String, TypeInterface> typeEnvironment10 = new MyDictionary<String, TypeInterface>();
            programExample10.typeCheck(typeEnvironment10);

            ProgramState programState10 = new ProgramState(new MyStack<>(), new MyDictionary<String, ValueInterface>(),
                    new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), programExample10);
            RepositoryInterface repository10 = new Repository(FOLDER_PATH + "\\log10.in");
            Controller controller10 = new Controller(repository10);
            controller10.addProgramState(programState10);
            textMenu.addCommand(new RunExampleCommand("10", "Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30); Ref int f; new(f, 123); new(f, 100); print(rH(rH(a)))", controller10));
        }catch (MyException e) {
            System.out.println("10: " + e.getMessage());
        }




        ///EXAMPLE 11
        /// int v; Ref int a; v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))
        StatementInterface declareV11 = new VariableDeclarationStatement("v", new IntType());
        StatementInterface declareA11 = new VariableDeclarationStatement("a", new ReferenceType(new IntType()));
        StatementInterface assignV111 = new AssignmentStatement("v", new ValueExpression(new IntValue(10)));
        StatementInterface allocV11 = new HeapAllocationStatement("a", new ValueExpression(new IntValue(22)));
        StatementInterface writeA11 = new HeapWritingStatement("a", new ValueExpression(new IntValue(30)));
        StatementInterface assignV112 = new AssignmentStatement("v", new ValueExpression(new IntValue(32)));
        StatementInterface printV111 = new PrintStatement(new VariableExpression("v"));
        ExpressionInterface readV11 = new HeapReadingExpression(new VariableExpression("a"));
        StatementInterface printV112 = new PrintStatement(readV11);
        StatementInterface fork11 = new ForkStatement(new CompoundStatement(writeA11, new CompoundStatement(assignV112, new CompoundStatement(printV111, printV112))));

        StatementInterface programExample11 = new CompoundStatement(declareV11, new CompoundStatement(declareA11, new CompoundStatement(assignV111,
                new CompoundStatement(allocV11, new CompoundStatement(fork11, new CompoundStatement(printV111, printV112))))));
        try {
            DictionaryInterface<String, TypeInterface> typeEnvironment11 = new MyDictionary<String, TypeInterface>();
            programExample11.typeCheck(typeEnvironment11);

            ProgramState programState11 = new ProgramState(new MyStack<>(), new MyDictionary<String, ValueInterface>(),
                    new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), programExample11);
            RepositoryInterface repository11 = new Repository(FOLDER_PATH + "\\log11.in");
            Controller controller11 = new Controller(repository11);
            controller11.addProgramState(programState11);
            textMenu.addCommand(new RunExampleCommand("11", "int v; Ref int a; v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))", controller11));
        }catch (MyException e) {
            System.out.println("11: " + e.getMessage());
        }



        ///EXEMPLE 12
        ///Ref int a; int counter; while(counter < 10){ fork(fork({new (a, counter); print(rH(a))})); counter++;}
        StatementInterface declareA12 = new VariableDeclarationStatement("a", new ReferenceType(new IntType()));
        StatementInterface declare_counter12 = new VariableDeclarationStatement("counter", new IntType());
        ExpressionInterface relExpr12 = new RelationalExpression(new VariableExpression("counter"), new ValueExpression(new IntValue(10)), "<");
        StatementInterface allocV12 = new HeapAllocationStatement("a", new VariableExpression("counter"));
        ExpressionInterface readV12 = new HeapReadingExpression(new VariableExpression("a"));
        StatementInterface printV12 = new PrintStatement(readV12);
        StatementInterface fork12 = new ForkStatement(new CompoundStatement(allocV12,printV12));
        StatementInterface fork122 = new ForkStatement(fork12);
        ExpressionInterface arithmeticV12 = new ArithmeticExpression(new VariableExpression("counter"), new ValueExpression(new IntValue(1)), 1);
        StatementInterface assignV12 = new AssignmentStatement("counter", arithmeticV12);
        StatementInterface compStmtV12 = new CompoundStatement(fork122, assignV12);
        StatementInterface whileStatementV12 = new WhileStatement(relExpr12, compStmtV12);

        StatementInterface programExample12 = new CompoundStatement(declareA12, new CompoundStatement(declare_counter12, whileStatementV12));
        try {
            DictionaryInterface<String, TypeInterface> typeEnvironment12 = new MyDictionary<String, TypeInterface>();
           programExample12.typeCheck(typeEnvironment12);

            ProgramState programState12 = new ProgramState(new MyStack<>(), new MyDictionary<String, ValueInterface>(),
                    new MyList<ValueInterface>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), programExample12);
            RepositoryInterface repository12 = new Repository(FOLDER_PATH + "\\log12.in");
            Controller controller12 = new Controller(repository12);
            controller12.addProgramState(programState12);
            textMenu.addCommand(new RunExampleCommand("12", "Ref int a; int counter; while(counter < 10) { fork(fork ({new (a, counter); print(rH(a))})); counter++;}", controller12));
        }catch (MyException e) {
            System.out.println("12: " + e.getMessage());
        }


//        try {
//            //controller1.setDisplayFlag(false);
//            controller1.allStepExecution();
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//
//        try {
//            //controller2.setDisplayFlag(false);
//            controller2.allStepExecution();
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//
//        try {
//            //controller3.setDisplayFlag(false);
//            controller3.allStepExecution();
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//
//        try {
//            //controller4.setDisplayFlag(false);
//            controller4.allStepExecution();
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }


        textMenu.show();

    }
}
