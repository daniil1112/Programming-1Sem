import java.io.*;
import java.util.Locale;

public class ScannerFast {
    private final char[] buffer = new char[10000];
    private int bufferLen = 0;
    private int cursor = -1;
    private final Reader reader;
    private int currentInt;
    private String currentHex;
    private String currentWord;
    private int currentLine = 1;
    private int currentIndex = 0;
    private boolean lastSeparator = false;


    public int getLineIndex(){
        return this.currentLine;
    }
    public int getItemIndex(){
        return this.currentIndex;
    }

    public ScannerFast(Reader reader){
        this.reader = reader;
    }
    public ScannerFast(String str){
        this.reader = new StringReader(str);
    }

    //Проверка, существует ли новое число. Перенос курсора на первое место происходит, если закончилось место в буфере
    public boolean hasNext() throws IOException {
        if (this.bufferLen == -1){
            return false;
        }
        if (this.cursor == this.bufferLen-1){
            this.cursor = -1;
            this.readNewBuffer();
            return this.hasNext();
        }
        return true;
    }

    public int getNextInt(){
        return this.currentInt;
    }

    public boolean testNextInt() throws IOException{
        while (hasNext()){
            char item = getNext();
            if (item == '\n'){
                return false;
            }
            if (item == '\r'){
                if (hasNext()){
                    if (getNext() == '\n'){
                        return false;
                    }
                    toPrev();
                }
                return false;
            }

            if (Character.isDigit(item) || item == '-' || item == '+'){
                toPrev();
                return true;
            }
        }
        return false;
    }

    public boolean testNextHex() throws IOException{
        while (hasNext()){
            char item = getNext();
            if (item == '\n'){
                return false;
            }
            if (item == '\r'){
                if (hasNext()){
                    if (getNext() == '\n'){
                        return false;
                    }
                    toPrev();
                }
                return false;
            }

            if (Character.isDigit(item) || item == '-' || item == '+' || iSHexChar(item)){
                toPrev();
                return true;
            }
        }
        return false;
    }

    public boolean toNextInt() throws IOException {
        StringBuilder resInt = new StringBuilder();
        while (this.hasNext()) {
            char currentCh = this.getNext();
            if (Character.isDigit(currentCh) || (currentCh == '+' && resInt.length() == 0) || (currentCh == '-' && resInt.length() == 0)) {
                resInt.append(currentCh);
            } else if (!resInt.toString().equals("-") && !resInt.toString().equals("+") && resInt.length() != 0) {
                this.currentInt = Integer.parseInt(resInt.toString());
                if (currentCh == '\n' || currentCh == '\r'){
                    toPrev();
                }
                return true;
            }
        }
        if (!resInt.toString().equals("-") && !resInt.toString().equals("+") & resInt.length() != 0) {
            this.currentInt = Integer.parseInt(resInt.toString());
            return true;
        }
        return false;
    }

    private boolean iSHexChar(char ch){
        return (Character.isDigit(ch)) || ((int) ch >= (int) 'A' && (int) ch <= (int) 'F') || ((int) ch >= (int) 'a' && (int) ch <= (int) 'f');
    };

    private boolean isWordChar(char ch){
        return Character.isLetter(ch) || ch =='\'' || Character.DASH_PUNCTUATION == Character.getType(ch);
    }

    public boolean toNextHex() throws IOException{
        StringBuilder resHex = new StringBuilder();
        while (this.hasNext()) {
            char currentCh = this.getNext();
            if (this.iSHexChar (currentCh) || (currentCh == '+' && resHex.length() == 0) || (currentCh == '-' && resHex.length() == 0) ) {
                resHex.append(currentCh);
            } else if (!resHex.toString().equals("-") && !resHex.toString().equals("+") && resHex.length() != 0) {
                this.currentHex = resHex.toString();
                if (currentCh == '\n' || currentCh == '\r'){
                    toPrev();
                }
                return true;
            }
        }
        if (!resHex.toString().equals("-") && !resHex.toString().equals("+") & resHex.length() != 0) {
            this.currentHex = resHex.toString();
            return true;
        }
        return false;
    }

    public boolean toNextWord()throws IOException{
        StringBuilder resWord = new StringBuilder();
        while (this.hasNext()) {
            char ch = this.getNext();
            if (System.lineSeparator().length() == 1){
                if (ch == System.lineSeparator().charAt(0)){
                    this.lastSeparator = true;
                }
            } else {
                if (ch == System.lineSeparator().charAt(0)){
                    this.lastSeparator = true;
                    for (int i = 1; i < System.lineSeparator().length(); i++){
                        if (!this.hasNext()){
                            this.lastSeparator = false;
                        } else if (this.getNext() != System.lineSeparator().charAt(i)){
                            this.lastSeparator = false;
                            for (int j = 1; j <= i; j++){
                                toPrev();
                            }
                        }
                    }
                }
            }
            if (this.isWordChar(ch)) {
                if (resWord.length() == 0){
                    if (this.lastSeparator){
                        this.currentIndex = 1;
                        this.currentLine++;
                        this.lastSeparator = false;
                    } else{
                        this.currentIndex++;
                    }

                }
                resWord.append(ch);
            } else if (resWord.length() > 0) {
                this.currentWord = resWord.toString();
                return true;
            }
        }
        if (resWord.length() > 0) {
            this.currentWord = resWord.toString();
            return true;
        }
        return false;
    }

    public int getNextHexConverted(){
        return Integer.parseUnsignedInt(this.currentHex,16);
    }
    public String getNextHex(){
        return this.currentHex;
    }
    public String getNextWord(){
        return this.currentWord.toLowerCase(Locale.ROOT);
    }

    private boolean isWordLetter(char ch){
        return Character.isLetter(ch) || ch =='\'' || Character.DASH_PUNCTUATION == Character.getType(ch);
    }

    public char getNext() throws IOException {
        if (this.bufferLen == 0){
            this.readNewBuffer();
            return this.getNext();
        } else if (this.bufferLen == -1){
            return 0;
        } else if (this.cursor < this.bufferLen-1){
            this.cursor++;
            return buffer[this.cursor];
        } else{
            this.cursor = -1;
            this.readNewBuffer();
            return this.getNext();
        }
//        return 0;
    }

    private void toPrev(){
        this.cursor--;
    }


    public String getNewLine() throws IOException {
        StringBuilder resStr = new StringBuilder();
        boolean isLineSep = false;
        while (this.hasNext()) {
            char ch = this.getNext();
            resStr.append(ch);
            String current = resStr.toString();
            if (ch == System.lineSeparator().charAt(0)){
                isLineSep = true;
                for (int i = 1; i < System.lineSeparator().length(); i++){
                    if (!this.hasNext() || this.getNext() != System.lineSeparator().charAt(i)){
                        isLineSep = false;
                        break;
                    }
                }
            }
            if (isLineSep) {
                return current.substring(0, current.length() - System.lineSeparator().length());
            }
        }

        return resStr.toString();
    }







    private void readNewBuffer() throws IOException {
        this.bufferLen = this.reader.read(this.buffer);
    }

}