
public class SumFloat {
    public static void main(final String[] args) {
        float result = 0;
        for (String arg : args) {
            result = getSumStr(arg, result);
        }
        System.out.print(result);

    }

    public static float getSumStr(String str, float result){
        int iSave = -1;
        float res = result;
        for (int i = 0; i < str.length(); i++){
            if (Character.isWhitespace(str.charAt(i))){
                if (iSave > -1){
                    res += Float.parseFloat(str.substring(iSave, i));
                    iSave = -1;
                }
            } else {
                if (iSave == -1){
                    iSave = i;
                }
            }
        }
        if (iSave > -1){
            res += Float.parseFloat(str.substring(iSave));
        }

        return res;
    }




}
