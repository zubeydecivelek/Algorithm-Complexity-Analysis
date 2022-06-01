import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

class Main {

    public static int[] inputAxis = {512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251281};
    public static String inputFile;

    public static void main(String args[]) throws IOException {
        inputFile = args[0];

        // I used these arrays to plot graphs.
        float[] xAxis ={512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251281};

        float[] RandomInsertionSortRunningTimes = new float[10];
        float[] SortedInsertionSortRunningTimes = new float[10];
        float[] ReverseInsertionSortRunningTimes = new float[10];
        float[] RandomMergeSortRunningTimes = new float[10];
        float[] SortedMergeSortRunningTimes = new float[10];
        float[] ReverseMergeSortRunningTimes = new float[10];
        float[] RandomCountingSortRunningTimes = new float[10];
        float[] SortedCountingSortRunningTimes = new float[10];
        float[] ReverseCountingSortRunningTimes = new float[10];
        float[] RandomPigeonholeSortRunningTimes = new float[10];
        float[] SortedPigeonholeSortRunningTimes = new float[10];
        float[] ReversePigeonholeSortRunningTimes = new float[10];

        float[][] randomYAxis = {RandomInsertionSortRunningTimes, RandomMergeSortRunningTimes,RandomCountingSortRunningTimes,RandomPigeonholeSortRunningTimes};
        float[][] sortedYAxis = {SortedInsertionSortRunningTimes,SortedMergeSortRunningTimes, SortedCountingSortRunningTimes,SortedPigeonholeSortRunningTimes};
        float[][] reverseYAxis = {ReverseInsertionSortRunningTimes, ReverseMergeSortRunningTimes,ReverseCountingSortRunningTimes,ReversePigeonholeSortRunningTimes};


        String[] sortTypes = {"insertion", "merge", "counting", "pigeonhole"};
        String[] sortModes = {"random", "sorted", "reverse"};

        // All sorting algorithms works here
       for (String type:sortTypes){
            for (String mode:sortModes){
                for (int i= 0;i<10;i++){
                    int[] SubArray = readFile(inputAxis[i]);
                    float runningTime = calculateRunningTime(SubArray,type,mode);
                    if(type.equals("insertion") && mode.equals("random")) RandomInsertionSortRunningTimes[i] = runningTime;
                    else if(type.equals("insertion") && mode.equals("sorted")) SortedInsertionSortRunningTimes[i] = runningTime;
                    else if(type.equals("insertion") && mode.equals("reverse")) ReverseInsertionSortRunningTimes[i] = runningTime;
                    else if(type.equals("merge") && mode.equals("random")) RandomMergeSortRunningTimes[i] = runningTime;
                    else if(type.equals("merge") && mode.equals("sorted")) SortedMergeSortRunningTimes[i] = runningTime;
                    else if(type.equals("merge") && mode.equals("reverse")) ReverseMergeSortRunningTimes[i] = runningTime;
                    else if(type.equals("counting") && mode.equals("random")) RandomCountingSortRunningTimes[i] = runningTime;
                    else if(type.equals("counting") && mode.equals("sorted")) SortedCountingSortRunningTimes[i] = runningTime;
                    else if(type.equals("counting") && mode.equals("reverse")) ReverseCountingSortRunningTimes[i] = runningTime;
                    else if(type.equals("pigeonhole") && mode.equals("random")) RandomPigeonholeSortRunningTimes[i] = runningTime;
                    else if(type.equals("pigeonhole") && mode.equals("sorted")) SortedPigeonholeSortRunningTimes[i] = runningTime;
                    else if(type.equals("pigeonhole") && mode.equals("reverse")) ReversePigeonholeSortRunningTimes[i] = runningTime;
                    System.out.println(type + " " + mode + " sort " + "size: " + inputAxis[i] + " running time: " + runningTime);
                }
            }
        }

        showAndSaveChart("Test on Random Data", xAxis, randomYAxis);
        showAndSaveChart("Test on Sorted Data", xAxis, sortedYAxis);
        showAndSaveChart("Test on Reverse Sorted Data", xAxis, reverseYAxis);

    }

    public static int[] readFile(int size) throws IOException {
        int[] inputArray = new int[size];
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        String line = bufferedReader.readLine();
        for (int i = 0;i < size;i++){
            line = bufferedReader.readLine();
            inputArray[i] = Integer.parseInt(line.split(",")[7]);
        }
        return inputArray;
    }

    public static float calculateRunningTime(int[] array, String sortType, String mode) throws IOException {
        float runningTime= 0;
        for (int i= 0;i<10;i++){
            if(mode.equals("sorted")){
                mergeSort(array,0, array.length-1);
            }
            else if(mode.equals("reverse")) {
                reverseMergeSort(array, 0, array.length - 1);
            }

            long startTime = System.currentTimeMillis();

            if (sortType.equals("insertion")) insertionSort(array);
            else if (sortType.equals("merge")) mergeSort(array,0, array.length-1);
            else if (sortType.equals("counting")) countingSort(array);
            else if (sortType.equals("pigeonhole")) pigeonholeSort(array, array.length);

            long endTime = System.currentTimeMillis();

            runningTime += endTime - startTime;
            array = readFile(array.length);
        }

        return runningTime/10;
    }


    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }


    public static void countingSort(int[] arr) {
        int max = Arrays.stream(arr).max().getAsInt();
        int min = Arrays.stream(arr).min().getAsInt();
        int range = max - min + 1;
        int count[] = new int[range];
        int output[] = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            count[arr[i] - min]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            output[count[arr[i] - min] - 1] = arr[i];
            count[arr[i] - min]--;
        }

        for (int i = 0; i < arr.length; i++) {
            arr[i] = output[i];
        }
    }


    public static void merge(int array[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] left = new int[n1];
        int[] right = new int[n2];

        for (int i = 0; i < n1; ++i)
            left[i] = array[l + i];
        for (int j = 0; j < n2; ++j)
            right[j] = array[m + 1 + j];

        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (left[i] <= right[j]) {
                array[k] = left[i];
                i++;
            }
            else {
                array[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = left[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = right[j];
            j++;
            k++;
        }
    }

    //                                 l = 0  r = length -1
    public static void mergeSort(int array[], int l, int r) {
        if (l < r) {
            int m =l+ (r-l)/2;

            mergeSort(array, l, m);
            mergeSort(array, m + 1, r);

            merge(array, l, m, r);
        }
    }


    //                                   n = arr.length
    public static void pigeonholeSort(int arr[], int n) {
        int min = arr[0];
        int max = arr[0];
        int range, i, j, index;

        for(int a=0; a<n; a++)
        {
            if(arr[a] > max)
                max = arr[a];
            if(arr[a] < min)
                min = arr[a];
        }

        range = max - min + 1;
        int[] phole = new int[range];
        Arrays.fill(phole, 0);

        for(i = 0; i<n; i++)
            phole[arr[i] - min]++;


        index = 0;

        for(j = 0; j<range; j++)
            while(phole[j]-->0)
                arr[index++]=j+min;

    }

    public static void reverseMerge(int arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];


        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] >= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    public static void reverseMergeSort(int arr[],int l, int r) {

        if (l < r) {
            // Find the middle point
            int m =l+ (r-l)/2;

            // Sort first and second halves
            reverseMergeSort(arr,l, m);
            reverseMergeSort(arr,m + 1, r);

            // Merge the sorted halves
            reverseMerge(arr,l, m, r);
        }
    }


    public static void showAndSaveChart(String title, float[] xAxis, float[][] yAxis) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Insertion", xAxis, yAxis[0]);
        chart.addSeries("Merge", xAxis, yAxis[1]);
        chart.addSeries("Counting", xAxis, yAxis[2]);
        chart.addSeries("Pigeonhole", xAxis, yAxis[3]);

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }
}


