import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/resources/input.txt")));
        String line;
        Map<String, Long[]> map = new HashMap<>();
        Map<String, Set<Long>> mapUserId = new HashMap<>();
        List<Country> listCountry = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            String[] mas = line.split(";");

            if (mas.length == 3 && checkLong(mas[0]) && checkLong(mas[1])) {
                String country = mas[2];
                long count = Long.parseLong(mas[1]);
                long userId = Long.parseLong(mas[0]);

                listCountry.add(new Country(country, userId, count));
            }
        }
        reader.close();

        for (Country country : listCountry) {
            String countryy = country.getCountry();
            long count = country.getCount();
            long userId = country.getUserId();

            if (!map.containsKey(countryy)) {
                Set<Long> set = new HashSet<>();
                set.add(userId);
                map.put(countryy, new Long[]{count, (long) set.size()});
                mapUserId.put(countryy, set);
            } else {
                Long[] mas = map.get(countryy);
                mas[0] = mas[0] + count;
                Set<Long> set = mapUserId.get(countryy);
                set.add(userId);
                mas[1] = (long) set.size();
                map.put(countryy, mas);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Long[]> pair : map.entrySet()) {
            stringBuilder.append(pair.getKey()).append(" ").append(pair.getValue()[0]).append(" ").append(pair.getValue()[1]).append("\n");
        }

        writeFile(stringBuilder.toString(), false);

        ////////////////////////////////////////////

        reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/resources/result.txt")));
        List<String[]> listResult = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] mas = line.split(" ");

            if (mas.length == 3 && checkLong(mas[1]) && checkLong(mas[2])) {
                listResult.add(mas);
            }
        }
        reader.close();

        listResult.sort(new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                if (!o1[1].equals(o2[1]))
                    return (int) (Long.parseLong(o1[1]) - Long.parseLong(o2[1]));
                else
                    return (int) (Long.parseLong(o1[2]) - Long.parseLong(o2[2]));
            }
        });

        stringBuilder = new StringBuilder();
        for (String[] strings : listResult) {
            stringBuilder.append(strings[0]).append(" ").append(strings[1]).append(" ").append(strings[2]).append("\n");
        }

        writeFile(stringBuilder.toString(), true);
    }

    private static boolean checkLong(String s) {
        try {
            Long.parseLong(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static void writeFile(String s, boolean flag) {
        try (FileWriter writer = new FileWriter("src/resources/result.txt", flag)){
            writer.write("country sum(count) count_uniq(user_id)\n");
            writer.write(s);
            writer.write("-------------\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
