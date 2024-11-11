import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SwMikolaj implements Inwentaryzator {

    private static final Set<String> INTERESTING_FIELDS = new HashSet<>(Arrays.asList(
            "bombki", "lancuchy", "cukierki", "prezenty", "szpice", "lampki"));

    @Override
    public Map<String, Integer> inwentaryzacja(List<String> listaKlas) {
        Map<String, Integer> wyniki = new HashMap<>();

        for (String nazwaKlasy : listaKlas) {
            try {
                Class<?> clazz = Class.forName(nazwaKlasy);
                Object instance = clazz.getDeclaredConstructor().newInstance();

                Field[] fields = clazz.getDeclaredFields();

                for (Field field : fields) {
                    if (field.getType() == int.class &&
                            !java.lang.reflect.Modifier.isStatic(field.getModifiers()) &&
                            java.lang.reflect.Modifier.isPublic(field.getModifiers()) &&
                            INTERESTING_FIELDS.contains(field.getName())) {
                        field.setAccessible(true);

                        try {
                            int value = (int) field.get(instance);
                            wyniki.put(field.getName(), wyniki.getOrDefault(field.getName(), 0) + value);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return wyniki;
    }
}
