import java.nio.charset.*;
import java.util.*;
import java.util.stream.Collectors;

void toCp930() {

    var testAscii = "TEST";
    var testKanji = "てすと";
    var texts = Arrays.asList(
        String.format("%s", testAscii),
        String.format("%s", testKanji),
        String.format("%s%s", testAscii, testKanji),
        String.format("%s%s", testKanji, testAscii),
        String.format("%s%s%s", testAscii, testKanji, testAscii)
    );

    var charset = Charset.forName("Cp930");
    texts.stream()
        .forEach( str -> {
            var bytes = str.getBytes(charset);
            var hexStrings = IntStream.range(0, bytes.length)
                .mapToObj(i -> String.format("0x%02X", bytes[i]))
                .collect(Collectors.joining(", "));
            System.out.printf("%-16s%s\n", "\"" + str + "\"", hexStrings);
        });
}
toCp930()
/exit
