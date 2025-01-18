import java.nio.charset.*;
import java.util.function.Consumer;
import java.util.function.BiConsumer;

void ascii() {
    System.out.printf("<ASCII>\n    ");
    for (var i = 0x0; i <= 0xF; i++) { System.out.printf("   0x%X", i); }
    for (var i = 0x20; i <= 0x7E; i++) {
        var leadByte = (byte) (i >> 4);
        if ((i & 0xF) == 0) System.out.printf("\n0x%X0", leadByte);
        System.out.printf("%6c", (char) i);
    }
    System.out.println();

    BiConsumer<String, String> printCodes = (from, to) -> {
        System.out.printf("\n<%s> -> <%s>", from, to);
        var charsetFrom = Charset.forName(from);
        var charsetTo = Charset.forName(to);
        for (var i = 0x20; i <= 0x7E; i++) {
            if ((i & 0xF) == 0) System.out.printf("\n    ");
            var bytes = new byte[] { (byte) i };
            var cp930Bytes = new String(bytes, charsetFrom).getBytes(charsetTo);
            var cp930Hex = switch (cp930Bytes.length) {
                case 1 ->String.format(" 0x%X", cp930Bytes[0]);
                case 2 ->String.format(" 0x%X%X", cp930Bytes[0], cp930Bytes[1]);
                default ->"NG";
            };
            System.out.printf(" %s", cp930Hex);
        }
        System.out.println();
    };
    printCodes.accept("UTF-8", "Cp930");
    printCodes.accept("UTF-8", "Cp939");

    Consumer<String> printBytes = to -> {
        var charset = Charset.forName(to);
        BiConsumer<Integer, Integer> printLine = (start, end) -> {
            System.out.printf("0x%X", start.byteValue());
            for (var i = start; i <= end; i++) {
                var cp930Bytes = new byte[] { i.byteValue() };
                System.out.printf("  %4s", new String(cp930Bytes, charset));
            }
            System.out.println();
        };
        System.out.printf("\n<%s>\n    ", to);
        for (var i = 0x0; i <= 0xF; i++) { System.out.printf("   0x%X", i); }
        System.out.println();
        printLine.accept(0x40, 0x4F);
        printLine.accept(0x50, 0x5F);
        printLine.accept(0x60, 0x6F);
        printLine.accept(0x70, 0x7F);
        printLine.accept(0x80, 0x8F);
        printLine.accept(0x90, 0x9F);
        printLine.accept(0xA0, 0xAF);
        printLine.accept(0xB0, 0xBF);
        printLine.accept(0xC0, 0xC9);
        printLine.accept(0xD0, 0xD9);
        printLine.accept(0xE0, 0xE9);
        printLine.accept(0xF0, 0xF9);
    };
    printBytes.accept("Cp930");
    printBytes.accept("Cp939");
}
ascii()
/exit
