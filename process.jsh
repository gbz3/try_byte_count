import java.nio.channels.*;
import java.nio.*;
import java.nio.charset.*;

void file() throws IOException {
    try (var inChannel = FileChannel.open(Path.of("input.txt"), StandardOpenOption.READ)) {
        var buffer = ByteBuffer.allocate(37);
        while (inChannel.read(buffer) != -1) {
            buffer.flip();
            System.out.println(new String(buffer.array()));
        }
    }
}

void cp930_1byte() throws IOException {
    System.out.println("    0x0 0x1 0x2 0x3 0x4 0x5 0x6 0x7 0x8 0x9 0xA 0xB 0xC 0xD 0xE 0xF");

    for (int i = 0; i <= 0xF; i++) {
        System.out.printf("0x%X", i);

        for (int j = 0; j <= 0xF; j++) {
            var bytes = new byte[]{(byte) (i << 4 | j)};
            if (i <= 0x3) {
                System.out.printf("  <>");
            } else {
                System.out.printf("   %s", new String(bytes, "Cp930"));
            }
        }

        System.out.println();
    }
}
cp930_1byte()

System.out.println();

void cp930_2bytes() throws IOException {
    System.out.println("      0x0 0x1 0x2 0x3 0x4 0x5 0x6 0x7 0x8 0x9 0xA 0xB 0xC 0xD 0xE 0xF");
    var decoder = Charset.forName("Cp930").newDecoder();

    //for (int i = 0; i <= 0xFFF; i++) {
    for (int i = 0x414; i <= 0xFEF; i++) {
        var line = new StringBuilder();
        line.append(String.format("0x%03X", i));
        var isAnyMappable = false;
        for (int j = 0; j <= 0xF; j++) {
            // 0x0E  シフトイン
            // 0x0F  シフトアウト
            var bb = ByteBuffer.wrap(new byte[]{(byte) 0x0E, (byte) ((i & 0xFF0) >> 4), (byte) ((i & 0xF) << 4 | j), (byte) 0x0F});
            if (decoder.decode(bb, CharBuffer.allocate(1), true).isUnmappable()) {
                line.append("  <>");
            } else {
                isAnyMappable = isAnyMappable || true;
                line.append(String.format("   %s", new String(bb.array(), "Cp930")));
            }
        }
        //if (isAnyMappable) {
            System.out.println(line.toString());
        //}
    }
}
cp930_2bytes()
/exit
