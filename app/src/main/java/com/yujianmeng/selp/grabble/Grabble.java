package com.yujianmeng.selp.grabble;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by YuJianmeng on 2016/12/1.
 */
public class Grabble {

    private char[] input = new char[7];
    private int inputpoint = 0;
    private int[] letters = new int[26];
    private HashMap<Character,Integer> scoreSG = new HashMap<Character, Integer>() {{
        put('a',3 );put('b',20);put('c',13);put('d',10);put('e',1 );
        put('f',15);put('g',18);put('h',9 );put('i',5 );put('j',25);
        put('k',22);put('l',11);put('m',14);put('n',6 );put('o',4 );
        put('p',19);put('q',24);put('r',8 );put('s',7 );put('t',2 );
        put('u',12);put('v',21);put('w',17);put('x',23);put('y',16);
        put('z',26);
    }};

    //TODO read from asset
    private ArrayList<String> dictionary = new ArrayList<>(Arrays.asList("harambe","drzaius"));

    public Grabble(int[] letters){
        this.letters = letters;
        //Arrays.fill(letters,2);
    }

    public char[] getInput(){
        return input;
    }

    public boolean isWord(){
        String outcome = String.valueOf(input);
        if (inputpoint == 7){
            return dictionary.contains(outcome);
        }
        return false;
    }

    public int getAmount(char c){
        return letters[charToInt(c)];
    }

    public int[] getAll() {return letters;}

    public int getScore(){
        int score = 0;
        int i = inputpoint;
        while (i > 0){
            i--;
            score = score + scoreSG.get(input[i]);
        }
        return score;
    }

    public int completeWord(){
        if(isWord()){
            int score = getScore();
            inputpoint = 0;
            return score;
        }
        return 0;
    }

    public boolean enterLetter(char a){
        //Return FALSE if the letter is successfully entered
        int i = charToInt(a);
        if (inputpoint < 7 && letters[i] > 0) {
            input[inputpoint] = intToChar(i);
            inputpoint++;
            letters[i]--;
            return false;
        }
        return true;
    }

    public void discardLetter(){
        if(inputpoint > 0){
            inputpoint--;
            letters[charToInt(input[inputpoint])]++;
        }
    }

    public void discardAll(){
        while (inputpoint > 0){
            discardLetter();
        }
    }

    public static char intToChar(int i){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        return alphabet.charAt(i);
    }

    public static int charToInt(char c){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        char[] out = alphabet.toCharArray();
        for (int i = 0; i < 26; i++){
            if (c == out[i]){
                return i;
            }
        }
        return -1;
    }

    public int getPoint(){
        return inputpoint;
    }

    //Used to get image resource int of the character
    public int getImage(char c){
        if (c == 'a'){return R.drawable.scrabble_button_a;}
        if (c == 'b'){return R.drawable.scrabble_button_b;}
        if (c == 'c'){return R.drawable.scrabble_button_c;}
        if (c == 'd'){return R.drawable.scrabble_button_d;}
        if (c == 'e'){return R.drawable.scrabble_button_e;}
        if (c == 'f'){return R.drawable.scrabble_button_f;}
        if (c == 'g'){return R.drawable.scrabble_button_g;}
        if (c == 'h'){return R.drawable.scrabble_button_h;}
        if (c == 'i'){return R.drawable.scrabble_button_i;}
        if (c == 'j'){return R.drawable.scrabble_button_j;}
        if (c == 'k'){return R.drawable.scrabble_button_k;}
        if (c == 'l'){return R.drawable.scrabble_button_l;}
        if (c == 'm'){return R.drawable.scrabble_button_m;}
        if (c == 'n'){return R.drawable.scrabble_button_n;}
        if (c == 'o'){return R.drawable.scrabble_button_o;}
        if (c == 'p'){return R.drawable.scrabble_button_p;}
        if (c == 'q'){return R.drawable.scrabble_button_q;}
        if (c == 'r'){return R.drawable.scrabble_button_r;}
        if (c == 's'){return R.drawable.scrabble_button_s;}
        if (c == 't'){return R.drawable.scrabble_button_t;}
        if (c == 'u'){return R.drawable.scrabble_button_u;}
        if (c == 'v'){return R.drawable.scrabble_button_v;}
        if (c == 'w'){return R.drawable.scrabble_button_w;}
        if (c == 'x'){return R.drawable.scrabble_button_x;}
        if (c == 'y'){return R.drawable.scrabble_button_y;}
        if (c == 'z'){return R.drawable.scrabble_button_z;}
        return R.drawable.scrabble_button;
    }

    /* TODO use this to test further, or remove it
    public static void main(String[] args){
        Grabble grabble = new Grabble();
        grabble.enterLetter('a');
        grabble.enterLetter('a');
        grabble.enterLetter('a');
        char[] input = grabble.getInput();
        System.out.print(String.valueOf(input));
        System.out.print('\n');
        grabble.discardAll();
        grabble.enterLetter('h');
        grabble.enterLetter('a');
        grabble.enterLetter('r');
        grabble.enterLetter('a');
        grabble.enterLetter('m');
        grabble.enterLetter('b');
        grabble.enterLetter('e');
        input = grabble.getInput();
        for (int i = 0; i < 7; i++){
            System.out.print(input[i]);
        }
        System.out.print('\n');
        System.out.print(grabble.completeWord());
    }*/
}
