package test;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class test2 {

    //定义全局变量
    public static String[] arrB;
    public static int[][] Graph;
    public static int INF = 991;
    public static int[][] dist;

    //随机游走
    public static String randomWalk() {
        Random ran1 = new Random();
        int p1 = ran1.nextInt(arrB.length);
        int u = 0;
        int[] edgeNum = new int[100];
        edgeNum[u] = p1;
        u++;
        String randword = "";
        randword = randword + arrB[p1];
        randword = randword + " ";
        System.out.println(p1);
        while(true)
        {
            int t =0;
            int[] arrNum = new int[100];
            for (int i=0; i<arrB.length;i++) {
                if (Graph[p1][i]!=0) {
                    arrNum[t] = i;
                    t++;
                }
            }
            System.out.println(p1);
            if  (t==0)
                break;

            p1 = arrNum[ran1.nextInt(t)];
            randword = randword +arrB[p1];
            randword = randword + " ";
            edgeNum[u] = p1;
            u++;
            boolean isTrue = false;
            if (u>3)
            {
                for (int j=0; j<u-2; j++) {
                    if (edgeNum[j]==edgeNum[u-2])
                        if(edgeNum[j+1]==edgeNum[u-1])
                            isTrue = true;
                }
                if (isTrue)
                    break;
            }
        }
        for (int l=0;l<u;l++)
        {
            System.out.printf("%d", edgeNum[l]);
        }
        return randword;
    }

    //位置获取函数
    public  static int getPosition(String[] x,String y ) {
        for (int i=0; i<x.length; i++) {
            if (x[i].equals(y))//string类型中为内容是否一致
                return i;
        }
        return -1;
    }

    //桥接词查询函数
    public static String queryBridgeWords(String word1, String word2)
    {
        int p1 = getPosition(arrB, word1);
        int p2 = getPosition(arrB, word2);
        String bridge = "";
        if (p1==-1 && p2==-1)
            return("situation1");
        else if (p1==-1 && p2!=-1)
            return("situation2");
        else if (p1!=-1 && p2==-1)
            return("situation3");
        else
            for (int i=0; i<arrB.length; i++) {
                if (Graph[p1][i] != 0) {
                    for (int j=0; j<arrB.length; j++) {
                        if (Graph[i][j] != 0) {
                            if (arrB[j].equals(word2)){
                                bridge = bridge + arrB[i];
                                bridge = bridge + " ";
                            }
                        }
                    }
                }
            }
        if (bridge.equals(" "))
            return("situation4");
        else
            return(bridge);
    }
    //根据bridge word生成新文本
    public static String generateNewText(String inputText) {
        String[] arrC = inputText.toLowerCase().split("\\s+");
        String[] tempArrC = new String[arrC.length*2];
        int m=0;
        for (int i=0; i<arrC.length-1; i++) {
            tempArrC[m] = arrC[i];
            m++;
            String br = queryBridgeWords(arrC[i], arrC[i+1]);
            boolean n = true;
            if (br.equals("situation1"))
                n = false;
            else if(br.equals("situation2"))
                n = false;
            else if(br.equals("situation3"))
                n = false;
            else if (br.equals("situation4"))
                n = false;
            if (n) {
                String[] arrD = br.split("\\s+");
                tempArrC[m] = arrD[0];
                m++;
            }
        }
        tempArrC[m] = arrC[arrC.length-1];
        System.out.println(Arrays.toString(tempArrC));
        String outputText = "";
        for (int i=0; i<m+1; i++ ) {
            if (tempArrC[i]!= "") {
                outputText = outputText + tempArrC[i];
                outputText = outputText + " ";
            }
        }
        return(outputText);
    }

    //计算最短路径
    public static String calcShortestPath(String word1, String word2) {
        int[][] path = new int[arrB.length][arrB.length];
        dist = new int[arrB.length][arrB.length];
        int p1 = getPosition(arrB,word1);//word1位置
        int p2 = getPosition(arrB,word2);//word2位置
        // 初始化
        for (int i = 0; i < arrB.length; i++) {
            for (int j = 0; j < arrB.length; j++) {
                if (Graph[i][j]==0)
                    dist[i][j] = INF;
                else
                    dist[i][j] = Graph[i][j];
                path[i][j] = -1;
            }
        }
        for (int k = 0; k < arrB.length; k++) {
            for (int i = 0; i <arrB.length; i++) {
                for (int j = 0; j <arrB.length; j++) {
                    // 如果经过下标为k顶点路径比原两点间路径更短，则更新dist[i][j]和path[i][j]
                    int tmp = (dist[i][k]==INF || dist[k][j]==INF) ? INF : (dist[i][k] + dist[k][j]);
                    if (dist[i][j] > tmp) {
                        dist[i][j] = tmp;
                        path[i][j] = k;
                    }
                }
            }
        }
        String shortText ="";
        String shortPath ="";
        while(path[p1][p2]!=p1 && path[p1][p2]!=-1){
            shortText = shortText + arrB[path[p1][p2]]+" ";
            p2 = path[p1][p2];
        }
        String[] shortArray = shortText.split(" ");
        if (shortArray[0].equals("")) {
            shortPath = word1;
        }
        else {
            shortPath = word1 + "->";
        }

        for (int m=shortArray.length-1; m>=0; m--) {
            shortPath += shortArray[m];
            shortPath += "->";
        }
        shortPath += word2;
        return shortPath;
    }

    //主函数
    public static void main(String[] args)
    {
    	String TxtString = "";     //定义字符串
        String fileName = "C:\\Users\\qjj\\Desktop\\test.txt";
        String line = "";//暂时存一行数据
        String str = "";
        try
        {
            BufferedReader in  = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));//将缓冲指定的文件输入
        	//BufferedReader in = new BufferedReader(new FileReader(fileName));
            line = in.readLine();
            while (line != null)
            {
                str += line;
                //System.out.println(line);
                //System.out.println(TxtString);
                line = in.readLine();
            }
            System.out.println(str);
            in.close();
            TxtString = str.replaceAll("[\\pP\\p{Punct}]", " ");  //过滤字符串
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        String[] arrA = TxtString.toLowerCase().split("\\s+"); //字符串写入数组，按空格分割,并大写转小写

//除掉重复元素，得到顶点集
        int t = 0;
        String[] tempArray = new String[arrA.length];
        for (int i=0; i<arrA.length; i++) {
            boolean isTrue = true;
            for (int j=0; j<i; j++) {
                if (arrA[i].equals(arrA[j])) {
                    isTrue = false;
                    break;
                }
            }
            if (isTrue) {
                tempArray[t]=arrA[i];
                t++;
            }
        }
        arrB = new String[t];
        System.arraycopy(tempArray,0,arrB,0,t);

//邻接矩阵构建有向图
        Graph = new int[arrB.length][arrB.length];
        for (int i=0; i<arrA.length-1; i++) {
            int j;
            int m;
            j = getPosition(arrB, arrA[i]);
            m = getPosition(arrB, arrA[i+1]);
            if (Graph[j][m]==0)
                Graph[j][m]=1;
            else
                Graph[j][m]++;
        }

//功能选择区
        Scanner scan1 = new Scanner(System.in);
        Scanner scan2 = new Scanner(System.in);
        System.out.println("1.展示有向图：");
        System.out.println("2.查询桥接词：");
        System.out.println("3.根据bridge word生成新文本：");
        System.out.println("4.计算最短路径：");
        System.out.println("5.逐一显示最短路径：");
        System.out.println("6.随机游走：");
        System.out.println("0.结束：");
        while (true)
        {
            int user_choice = scan1.nextInt();//读取数字符
            if (user_choice==0) //输入0，结束循环
                break;
            if (user_choice==1) //功能二  展示有向图
            {
                for (int i=0; i<arrB.length; i++) {
                    for (int j=0; j<arrB.length;j++) {
                        System.out.printf("%d", Graph[i][j]);
                    }
                    System.out.printf("\n");
                }
                System.out.println(Arrays.toString(arrA));  //输出原始数组
                System.out.println(Arrays.toString(arrB));  //输出顶点数组
                File file1 = new File("c:/GRAPH","Graph.dot" );
                try{
                    file1.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                try {
                    FileWriter NewFile = new FileWriter(file1);
                    NewFile.write("digraph abc{\r\n\tnode [shape=\"record\"];\r\n\t");
                    for(int i = 0;i<arrB.length;i++)
                        NewFile.write(arrB[i]+";\r\n\t");
                    for(int i = 0;i<arrB.length;i++){
                        for(int j = 0;j<arrB.length;j++){
                            if(Graph[i][j]!=0)
                                NewFile.write(arrB[i]+"->"+arrB[j]+"[label = "+Graph[i][j]+"]"+";"+"\r\n\t");
                        }
                    }
                    NewFile.write("}");
                    NewFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String shell = "dot -Tgif -o output.gif Graph.dot";
                try {
                    Runtime.getRuntime().exec(shell,null,new File("c:/GRAPH"));
                    //Runtime.getRuntime().exec("output.gif",null,new File("c:/GRAPH"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(user_choice==2) //功能三 查询桥接词
            {
                System.out.println("请按先后顺序输入你想查询的两个节点：");
                System.out.println("输入word1：");
                String word1 = scan2.nextLine();
                System.out.println("输入word2：");
                String word2 = scan2.nextLine();
                if(queryBridgeWords(word1, word2).equals("situation1"))
                    System.out.printf("No “%s” and “%s” in the graph!",word1,word2);
                else if(queryBridgeWords(word1, word2).equals("situation2"))
                    System.out.printf("No “%s” in the graph!",word1);
                else if(queryBridgeWords(word1, word2).equals("situation3"))
                    System.out.printf("No “%s” in the graph!",word2);
                else if(queryBridgeWords(word1, word2).equals("situation4"))
                    System.out.printf("No bridge words from “%s” to “%s” !",word1,word2);
                else{
                    String bridgeword = queryBridgeWords(word1, word2);
                    System.out.printf("The bridge words from “%s” to “%s” is: “%s” !",word1,word2, bridgeword);
                }
                System.out.printf("\n");
            }
            else if (user_choice==3)//根据bridge word生成新文本
            {
                System.out.println("请输入新文本：");
                String TxtString2 = scan2.nextLine();
                String TxtString3 = generateNewText(TxtString2);
                System.out.println(TxtString3);
            }
            else if (user_choice==4) //计算最短路径
            {
                System.out.println("计算最短路径，请输入word1：");
                String word3 = scan2.nextLine();
                System.out.println("请输入word2：");
                String word4 = scan2.nextLine();
                int p1 = getPosition(arrB,word3);
                int p2 = getPosition(arrB,word4);
                String shortword = calcShortestPath(word3, word4);
                if (dist[p1][p2]==INF){
                    System.out.printf("不可达");
                    System.out.printf("\n");
                }
                else {
                    System.out.printf("The shortest path between “%s” and “%s” is %s ,include: %s",word3,word4,dist[p1][p2], shortword);
                    System.out.printf("\n");
                }
            }
            else if (user_choice==5) {
                System.out.println("请输入word5");
                String word5 = scan2.nextLine();
                int p3 = getPosition(arrB, word5);
                for (int i=0; i<arrB.length; i++) {
                    String ss = calcShortestPath(word5,arrB[i]);
                    if (dist[p3][i] == INF) {
                        System.out.printf("From “%s” to “%s” 不可达", word5,arrB[i]);
                        System.out.printf("\n");
                    }
                    else {
                        System.out.printf("The shortest path between “%s” and “%s” is %s ,include: %s",word5,arrB[i],dist[p3][i],ss);
                        System.out.printf("\n");
                    }
                }
            }
            else if (user_choice==6)
            {
                String randword = randomWalk();
                System.out.println(randword);
                System.out.println(Arrays.toString(arrB));
            }
            else {
                System.out.println("输入错误！");
            }
        }
        scan1.close();
        scan2.close();
    }


}