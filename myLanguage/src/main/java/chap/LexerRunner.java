package chap;

import stone.CodeDialog;
import stone.Lexer;
import stone.ParseException;
import stone.Token;

public class LexerRunner {
    public static void main(String[] args) throws ParseException {
        Lexer l = new Lexer(new CodeDialog());
        int i = 0;


        if(i < 0){
            i =10;
        }else if(i < 10){
            i =20;
        }else if(i > 100){
            i =30;
        }else{
            i =0;
        }




        if(i < 0){
            i =10;
        }else {
            if (i < 10) {
                i = 20;
            } else {
                if (i > 100) {
                    i = 30;
                } else {
                    i = 0;
                }
            }
        }





        for (Token t; (t = l.read()) != Token.EOF; )
            System.out.println("=> " + t.getText());
    }
}
