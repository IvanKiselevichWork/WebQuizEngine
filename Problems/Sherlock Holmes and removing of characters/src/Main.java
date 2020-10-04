import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str1 = scanner.nextLine().toLowerCase();
        String str2 = scanner.nextLine().toLowerCase();
        Map<Integer, Integer> treeMap1 = new TreeMap<>();
        Map<Integer, Integer> treeMap2 = new TreeMap<>();
        putCharsInMap(treeMap1, str1);
        putCharsInMap(treeMap2, str2);

        treeMap1.entrySet().stream().forEach(integerIntegerEntry -> {
            int treeMap2Val = treeMap2.getOrDefault(integerIntegerEntry.getKey(), 0);
            int diff = Math.abs(treeMap2Val - integerIntegerEntry.getValue());
            treeMap2.put(integerIntegerEntry.getKey(), diff);
        });

        int count = treeMap2.entrySet().stream().map(Map.Entry::getValue).mapToInt(integer -> integer).sum();

        System.out.println(count);
    }

    private static void putCharsInMap(Map<Integer, Integer> map, String str) {
        str.chars().forEach(i -> {
            Integer integer = map.get(i);
            if (integer == null) {
                map.put(i, 1);
            } else {
                map.put(i, integer + 1);
            }
        });
    }
}