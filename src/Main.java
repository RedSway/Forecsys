import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/files/input.txt")));
        String line;
        Map<String, Long[]> map = new HashMap<>();
        Map<String, Set<Long>> uniqCountryId = new HashMap<>();
        List<Country> listCountry = new ArrayList<>();


        while ((line = reader.readLine()) != null) {
            String[] mas = line.split(";");

            if (mas.length == 3 && checkString(mas[1]) && checkString(mas[0])) {
                String country = mas[2];
                long count = Long.valueOf(mas[1]);
                long userId = Long.valueOf(mas[0]);

                listCountry.add(new Country(country, count, userId));
            }
        }
        reader.close();

        /*
        смотрим весь список стран и добавляем в мап ту страну которой еще в ней нет,
        так же добавляем отдельно уникальный countyId в соответсвующий мап uniqCountryId
                иначе
        получаем из uniqCountryId список уникальных countyId для текущей страны, получаем из мап
        количество уникальных countyId и смотрим, если в списоке уникальных countyId нет подобных countyId
        , то добавляем его в этот список и увеличиваем счетчик
        */
        for (Country country : listCountry) {
            if (!map.containsKey(country.getCountry())) {
                uniqCountryId.put(country.getCountry(), new HashSet<>());
                uniqCountryId.get(country.getCountry()).add(country.getUserId());
                map.put(country.getCountry(), new Long[]{country.getCount(), 1L});
            } else {

                Set<Long> sets = uniqCountryId.get(country.getCountry());
                Long[] longs = map.get(country.getCountry());
                long id;
                if (sets.contains(country.getUserId()))
                    id = longs[1];
                else {
                    id = longs[1] + 1;
                    uniqCountryId.get(country.getCountry()).add(country.getUserId());
                }

                map.put(country.getCountry(), new Long[]{country.getCount() + longs[0], id});
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Long[]> pair : map.entrySet()) {
            stringBuilder.append(pair.getKey()).append(";").append(pair.getValue()[0]).append(";").append(pair.getValue()[1]).append("\n");
        }

        fileWrite(stringBuilder.toString(), false);

        ////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////

        List<String[]> sortList = new ArrayList<>();
        boolean flag = false;
        reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/files/result.txt")));

        while ((line = reader.readLine()) != null) {
            if (!flag)
                flag = true;
            else {
                String[] mas = line.split(";");
                sortList.add(mas);
            }
        }
        reader.close();

        sortList.sort((o1, o2) -> {
            if (!o1[1].equals(o2[1]))
                return Integer.parseInt(o1[1]) - Integer.parseInt(o2[1]);
            else
                return Integer.parseInt(o1[2]) - Integer.parseInt(o2[2]);
        });

        stringBuilder = new StringBuilder();
        for (String[] strings : sortList) {
            stringBuilder.append(strings[0]).append(";").append(strings[1]).append(";").append(strings[2]).append("\n");
        }

        fileWrite(stringBuilder.toString(), true);
    }

    private static boolean checkString(String string) {
        try {
            Integer.parseInt(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static void fileWrite(String s, boolean flag) {
        try (FileWriter fileWriter = new FileWriter("src/files/result.txt", flag)) {
            fileWriter.write("country;sum(count);count_uniq(user_id)\n");
            fileWriter.write(s);

            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
