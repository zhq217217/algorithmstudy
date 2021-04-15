package algorithmstudy.datastructure;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 稀疏数组
 * 概述：棋盘模型，多维数组，如果使用内存比较少时，可以通过稀疏数组方式来进行优化压缩
 * 使用场景：占用内存比较少时
 * 模型：稀疏数组行数不固定，但是列数时固定的，为多维数组维数+1，第一行别记录行 列数  占用的总数  第二行开始分别记录每个占用数所在的行 列 具体数值
 */
public class SpareArray {
    public static void main(String[] args) {
        /**
         * <p>
         *     0 0 0 0 0 0 0 0 0 0 0
         *     0 0 1 0 0 0 0 0 0 0 0
         *     0 0 0 0 2 0 0 0 0 0 0
         *     0 0 0 0 0 0 0 0 0 0 0
         *     0 0 0 0 0 0 0 0 0 0 0
         *     0 0 0 0 0 0 0 0 0 0 0
         *     0 0 0 0 0 0 0 0 0 0 0
         *     0 0 0 0 0 0 0 0 0 0 0
         *     0 0 0 0 0 0 0 0 0 0 0
         *     0 0 0 0 0 0 0 0 0 0 0
         *     0 0 0 0 0 0 0 0 0 0 0
         * </p>
         */
        //用二维数组表示
        int[][] originData = new int[11][11];
        originData[1][2] = 1;
        originData[2][4] = 2;

        //先输出打印一下原始数据
        for (int[] nums : originData) {
            for (int num : nums) {
                System.out.printf("%d\t", num);
            }
            System.out.println();
        }

        //用稀疏数组表示
        //TODO:第一步，先计算出有多少个有效数字
        int total = 0;
        for (int[] nums : originData) {
            for (int num : nums) {
                if (num != 0) total++;
            }
        }
        //TODO:创建稀疏数组，列永远为3
        /**
         * 稀疏数组
         * <p>
         *     11 11 2
         *     1  2  1
         *     2  4  2
         * </p>
         */
        //TODO:第一步：第一行为原数据有多少行  多少列 总共多少个有效数据
        int[][] spareArray = new int[total + 1][3];
        spareArray[0][0] = originData.length;
        spareArray[0][1] = originData[0].length;
        spareArray[0][2] = total;

        //TODO:第二步，将所有有效数据的行 列 值保存
        int spareCount = 0;
        for (int i = 0; i < originData.length; i++) {
            for (int j = 0; j < originData[i].length; j++) {
                if (originData[i][j] != 0) {
                    spareCount++;
                    spareArray[spareCount][0] = i;
                    spareArray[spareCount][1] = j;
                    spareArray[spareCount][2] = originData[i][j];
                }
            }
        }
        //输出一下稀疏数组
        System.out.println();
        System.out.println("以下为转化后的稀疏数组:");
        for (int[] nums : spareArray) {
            for (int num : nums) {
                System.out.printf("%d\t", num);
            }
            System.out.println();
        }

        //TODO:中间穿插一：将稀疏数组保存到本地文件
        try {
            FileWriter fileWriter = new FileWriter("sparearray.txt");
            fileWriter.write(spareArray.length);
            for (int[] nums : spareArray) {
                for (int num : nums) {
                    fileWriter.write(num);
                }
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO:中间穿插二：将本地数组读取到内存中
        int[][] readSpareArr = null;
        try {
            FileReader fileReader = new FileReader("sparearray.txt");
            readSpareArr = new int[fileReader.read()][3];
            for (int j = 0; j < readSpareArr.length; j++) {
                for (int i = 0; i < 3; i++) {
                    readSpareArr[j][i] = fileReader.read();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (null == readSpareArr) return;
        //TODO:将稀疏数组还原回原数据
        int[][] reOriginData = new int[readSpareArr[0][0]][readSpareArr[0][1]];
        for (int i = 1; i < readSpareArr.length; i++) {
            reOriginData[readSpareArr[i][0]][readSpareArr[i][1]] = readSpareArr[i][2];
        }

        System.out.println();
        //输出还原后的原数组
        for (int[] arr : reOriginData) {
            for (int num : arr) {
                System.out.printf("%d\t", num);
            }
            System.out.println();
        }
    }
}
