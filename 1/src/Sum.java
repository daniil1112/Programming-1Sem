public class Sum {
	public static void main(final String[] args) {
        int result = 0;
        for (String arg : args) {
            result = getSumStr(arg, result);
        }
		System.out.println(Math.max(1, 2));


        System.out.print(result);

	}


    public static int getSumStr(String str, int result){
		int iSave = -1;
		int res = result;

        for (int i = 0; i < str.length(); i++){
            if (Character.isWhitespace(str.charAt(i))){
                if (iSave > -1){
                    res += Integer.parseInt(str.substring(iSave, i));
                    iSave = -1;
                }
            } else {
                if (iSave == -1){
                    iSave = i;
                }
            }
        }
        if (iSave > -1){
            res += Integer.parseInt(str.substring(iSave));
        }
		
        return res;
    }
}
