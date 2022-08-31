package top.sunquest.cycle;

public class CycleSample {

    public static void main(String[] args){
        float random = 0.01f;
        while (random < 1000000){
            random = random + 0.01f;
            System.out.println(random);
        }

        System.out.println("finish");
    }
}