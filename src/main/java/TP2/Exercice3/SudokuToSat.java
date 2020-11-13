package TP2.Exercice3;

public class SudokuToSat {
    public static void main(String[] args) throws Exception {
        int n = 2;
        int[][] valid = new int[n * n][n * n];
        valid[0] = new int[]{1, 2, 4, 3}; // 11  12  13  14
        valid[1] = new int[]{4, 3, 1, 2}; // 21  22  23  24
        valid[2] = new int[]{3, 1, 2, 4}; // 31  32  33  34
        valid[3] = new int[]{2, 4, 3, 0}; // 41  42  43  44

        int[][] invalid = new int[n * n][n * n];

        invalid[0] = new int[]{1, 2, 4, 3}; // 11  12  13  14
        invalid[1] = new int[]{4, 3, 0, 0}; // 21  22  23  24
        invalid[2] = new int[]{3, 0, 1, 4}; // 31  32  33  34
        invalid[3] = new int[]{0, 4, 2, 0}; // 41  42  43  44

        int[][] invalid_2 = new int[n * n][n * n];
        invalid_2[0] = new int[]{1, 2, 4, 3}; // 11  12  13  14
        invalid_2[1] = new int[]{4, 3, 1, 2}; // 21  22  23  24
        invalid_2[2] = new int[]{3, 1, 0, 4}; // 31  32  33  34
        invalid_2[3] = new int[]{2, 4, 2, 0}; // 41  42  43  44

        int[][] invalid_3 = new int[n * n][n * n];
        invalid_3[0] = new int[]{0, 1, 4, 3}; // 11  12  13  14
        invalid_3[1] = new int[]{4, 3, 1, 2}; // 21  22  23  24
        invalid_3[2] = new int[]{3, 1, 2, 4}; // 31  32  33  34
        invalid_3[3] = new int[]{2, 4, 3, 0}; // 41  42  43  44

        System.out.println(new Fuck(n, valid).isSolvable());
        System.out.println(new Fuck(n, invalid).isSolvable());
        System.out.println(new Fuck(n, invalid_2).isSolvable());
        System.out.println(new Fuck(n, invalid_3).isSolvable());
    }
}
