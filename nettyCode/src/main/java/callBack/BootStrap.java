package callBack;

import jdk.nashorn.internal.runtime.ECMAException;

public class BootStrap {
    public static void main(String[] args){
        BootStrap bootStrap = new BootStrap();

        // 创建一个工作者
        Worker worker =  bootStrap.newWorker();

        // 初始化一个工作组合者
        WorkerAssembly  workerAssembly =  new WorkerAssembly();
        workerAssembly.setWorker(worker);
        workerAssembly.setInput("原始材料");
        workerAssembly.setCallBack(new CallBack() {
            @Override
            public void result(Object o) {
                System.out.println(Thread.currentThread().getName());
                System.out.println(o);
            }
        });

        // 开始分配任务
        bootStrap.assigningTask(workerAssembly);
        System.out.println(Thread.currentThread().getName()+":已经完成");
    }

    // 管理者开始调度任务，将任务分配给工作者
    private void assigningTask(WorkerAssembly workerAssembly){
        //分配一个线程，开始执行
        new Thread(()->{
            Worker worker = workerAssembly.getWorker();
            String output = worker.action(workerAssembly.getInput());
            workerAssembly.getCallBack().result(output);
        },"工作者线程").start();
    }

    // 创建出一个 worker
    private Worker newWorker(){
        return new Worker() {
            @Override
            public String action(Object input) {
                try{
                    Thread.sleep(1000);
                }catch(Exception ex){
                    ex.printStackTrace();
                }

                return input + ":这个是输入项";
            }
        };
    }
}