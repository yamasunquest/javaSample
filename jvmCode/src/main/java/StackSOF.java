/**
 *
 */

public class StackSOF {

private int stackLength = -1;

    public void stackLeak(){
        stackLength ++;
        stackLeak();
    }

    public static void main(String args[]){
        StackSOF ssf = new StackSOF();

        try{
            ssf.stackLeak();
        }catch (Throwable ex){
            System.out.println(ssf.stackLength);
            throw ex;
        }

    }
}
