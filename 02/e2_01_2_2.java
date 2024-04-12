//Naoya Iida
public class e2_01_2_2 {
    public static void main(String[] args) {
        int count = 100000;
        long startTime = System.currentTimeMillis();
        String resultWithStringBuilder = catWithSB(count);
        long endTime = System.currentTimeMillis();
        System.out.println("catWithSB: " + (endTime - startTime) + " milliseconds");
        startTime = System.currentTimeMillis();
        String resultWithPlusOperator = catWithPlus(count);
        endTime = System.currentTimeMillis();
        System.out.println("catWithPlus: " + (endTime - startTime) + " milliseconds");


    }

    public static String catWithSB(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("Hello").append("World").append("!");
        }
        return sb.toString();
    }
    public static String catWithPlus(int count) {
        String result = "";
        for (int i = 0; i < count; i++) {
            result += "Hello" + "World" + "!";
        }
        return result;
    }
}

/*
catWithSB: 9 milliseconds
catWithPlus: 4284 milliseconds

Stringクラスはimmutableクラス(不変)なクラスであり、
文字を追加してもメモリ領域が広がるのではなく、
また新しくStringオブジェクトを生成しているためJVMに過剰に負荷がかかり処理速度が遅くなっている。

StringBuilderクラスはmutableクラス(可変)なクラスであり、
文字を追加するとメモリ領域が広がり、Stringオブジェクトを生成しなくても、
文字を追加できるようになっているため、処理が速い。
 */