package ThreadJoinTestUpdated;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class TestDataFactory {
    private TestDataFactory() {
    }

    public static List<String> getInput(String filename, String splitter) {
        try {
            URI uri = ClassLoader.getSystemResource(filename).toURI();

            String bytes = new String(Files.readAllBytes(Paths.get(uri)));

            return
                    Pattern.compile(splitter).splitAsStream(bytes)
                            .filter(((Predicate<String>) String::isEmpty).negate())

                            .collect(toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
