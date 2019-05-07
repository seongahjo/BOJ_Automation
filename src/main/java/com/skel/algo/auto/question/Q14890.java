package com.skel.algo.auto.question;

import java.util.Scanner;

public class Q14890 implements Question {
     


    @Override
    public String run(String input) {
        int N, L;
        int map[][] = new int[101][101];
         boolean X[] = new boolean[101];
         boolean Y[] = new boolean[101];
         boolean jumpX[][][] = new boolean[101][101][101];

         boolean jumpY[][][] = new boolean[101][101][101];
        Scanner sc = new Scanner(input);
        N = sc.nextInt();
        L = sc.nextInt();
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                map[i][j] = sc.nextInt();
            }
        }

        //  아래 위에서 위에러 아래, 왼 오, 오, 왼

        int road = 0;
        // 위 -> 아래
        for (int j = 1; j <= N; j++) {
            for (int i = 1; i <= N; i++) {
                boolean check = true;
                for (int k = i + 1; k < i + L && k <= N; k++) {
                    if (map[k][j] != map[i][j]) {
                        check = false;
                        break;
                    }
                }
                if (!check) break;
                if (i + L <= N && map[i + L][j] - map[i][j] == 1) {
                    // i+L 이 아래, i가 위
                    jumpX[j][i + L - 1][i + L] = true;
                }
            }
        }

        //아래 -> 위
        for (int j = 1; j <= N; j++) {
            for (int i = N; i >= 1; i--) {
                boolean check = true;
                for (int k = i - 1; k > i - L && k >= 1; k--) {
                    if (map[k][j] != map[i][j]) {
                        check = false;
                        break;
                    }
                }
                if (!check) break;
                if (i - L >= 1 && map[i - L][j] - map[i][j] == 1) {
                    jumpX[j][i - L + 1][i - L] = true;
                }
            }
        }

        //왼 -> 오

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                boolean check = true;
                for (int k = j + 1; k < j + L && k <= N; k++) {
                    if (map[i][j] != map[i][k]) {
                        check = false;
                        break;
                    }
                }
                if (!check) break;
                if (j + L <= N && map[i][j + L] - map[i][j] == 1) {
                    jumpY[i][j + L - 1][j + L] = true;
                }
            }
        }

        // 오 -> 왼
        for (int i = 1; i <= N; i++) {
            for (int j = N; j >= 1; j--) {
                boolean check = true;
                for (int k = j - 1; k > j - L && k >= 1; k--) {
                    if (map[i][k] != map[i][j]) {
                        check = false;
                        break;
                    }
                }
                if (!check) break;
                if (j - L >= 1 && map[i][j - L] - map[i][j] == 1) {
                    jumpY[i][j - L + 1][j - L] = true;
                }
            }
        }


        // 확인하는 부분

        for (int i = 1; i <= N; i++) {
            if (Y[i]) continue;
            int startLevel = map[i][1];
            int j;
            for (j = 2; j < N; j++) {
                if (startLevel == map[i][j] ||
                        ((jumpY[i][j][j-1]  ^ jumpY[i][j][j+1] ) ||
                                (jumpY[i][j-1][j] ^ jumpY[i][j+1][j]))){
                    startLevel = map[i][j];
                    continue;
                } else
                    break;
            }
            if (j == N) {
                Y[i] = true;
                road++;
            }
        }


        //위 -> 아래
        for (int j = 1; j <= N; j++) {
            if (X[j]) continue;
            int startLevel = map[1][j];
            int i;
            for (i = 2; i < N; i++) {
                if (startLevel == map[i][j] ||
                        ((jumpX[j][i][i-1]^jumpX[j][i-1][i]) || (jumpX[j][i][i+1]^jumpX[j][i+1][i]))) {
                    startLevel = map[i][j];
                    continue;
                } else
                    break;
            }
            if (i == N) {
                X[j] = true;
                road++;
            }

        }


        return String.valueOf(road);
    }
}
