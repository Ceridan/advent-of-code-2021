package aoc2021;

import java.io.FileNotFoundException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;

public class Day16 {

    public static void main(String[] args) throws FileNotFoundException {
        String data = Utils.readInputAsString("day16.txt");

        System.out.printf("Day 16, part 1: %d\n", part1(data));
        System.out.printf("Day 16, part 2: %d\n", part2(data));
    }

    static int part1(String data) {
        BitsDecoder bd = new BitsDecoder();
        Packet packet = bd.decode(data);
        return packet.getVersionSum();
    }

    static long part2(String data) {
        BitsDecoder bd = new BitsDecoder();
        Packet packet = bd.decode(data);
        return packet.getValue();
    }

    private static class Packet
    {
        private final int version;
        private final int type;
        private final List<Packet> subPackets = new ArrayList<>();

        public Packet(int version, int type) {
            this.version = version;
            this.type = type;
        }

        public int getVersionSum() {
            int versionSum = version;

            for (Packet subPacket : subPackets) {
                versionSum += subPacket.getVersionSum();
            }

            return versionSum;
        }

        public long getValue() {
            switch (type) {
                case 0:
                    return subPackets.stream().mapToLong(Packet::getValue).sum();
                case 1:
                    return subPackets.stream().mapToLong(Packet::getValue).reduce(1L, (a, b) -> a * b);
                case 2:
                    return subPackets.stream().mapToLong(Packet::getValue).min().orElseThrow(NoSuchElementException::new);
                case 3:
                    return subPackets.stream().mapToLong(Packet::getValue).max().orElseThrow(NoSuchElementException::new);
                case 5:
                    return subPackets.get(0).getValue() > subPackets.get(1).getValue() ? 1L : 0L;
                case 6:
                    return subPackets.get(0).getValue() < subPackets.get(1).getValue() ? 1L : 0L;
                case 7:
                    return subPackets.get(0).getValue() == subPackets.get(1).getValue() ? 1L : 0L;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

    private static class Literal extends Packet {
        private final long value;

        public Literal(int version, int type, long value) {
            super(version, type);
            this.value = value;
        }

        @Override
        public long getValue() {
            return value;
        }
    }

    private static class BitsDecoder {
        private final Map<Character, String> hexDecodeMap = new HashMap<>();

        public BitsDecoder() {
            initializeHexDecodeMap();
        }

        public Packet decode(String hexEncoded) {
            String binaryEncoded = decodeHexEncoded(hexEncoded);
            CharacterIterator it = new StringCharacterIterator(binaryEncoded);
            return decodeImpl(it);
        }

        private Packet decodeImpl(CharacterIterator it) {
            int version = Integer.parseInt("" + it.current() + it.next() + it.next(), 2);
            int type = Integer.parseInt("" + it.next() + it.next() + it.next(), 2);

            if (type == 4) {
                return new Literal(version, type, parseValue(it));
            }

            Packet packet = new Packet(version, type);

            char identifier = it.next();

            if (identifier == '0') {
                char[] lengthArray = new char[15];
                for (int i = 0; i < 15; i++) {
                    lengthArray[i] = it.next();
                }

                int length = Integer.parseInt(new String(lengthArray), 2);
                int startIndex = it.getIndex();
                while (length > 0) {
                    it.next();
                    Packet newPacket = decodeImpl(it);
                    packet.subPackets.add(newPacket);

                    int endIndex = it.getIndex();
                    length -= endIndex - startIndex;
                    startIndex = endIndex;
                }
            } else {
                char[] countArray = new char[11];
                for (int i = 0; i < 11; i++) {
                    countArray[i] = it.next();
                }

                int count = Integer.parseInt(new String(countArray), 2);
                for (int i = 0; i < count; i++) {
                    it.next();
                    Packet newPacket = decodeImpl(it);
                    packet.subPackets.add(newPacket);
                }
            }

            return packet;
        }

        private long parseValue(CharacterIterator it) {
            List<Character> digits = new ArrayList<>();
            char hasNext;

            do {
                hasNext = it.next();
                for (int i = 0; i < 4; i++) {
                    digits.add(it.next());
                }
            } while (hasNext == '1');

            return Long.parseLong(digits.toString().replaceAll("[,\\s\\[\\]]", ""), 2);
        }

        private String decodeHexEncoded(String hexEncoded) {
            StringBuilder sb = new StringBuilder();

            for (char hex : hexEncoded.toCharArray()) {
                sb.append(hexDecodeMap.get(hex));
            }

            return sb.toString();
        }

        private void initializeHexDecodeMap() {
            hexDecodeMap.put('0', "0000");
            hexDecodeMap.put('1', "0001");
            hexDecodeMap.put('2', "0010");
            hexDecodeMap.put('3', "0011");
            hexDecodeMap.put('4', "0100");
            hexDecodeMap.put('5', "0101");
            hexDecodeMap.put('6', "0110");
            hexDecodeMap.put('7', "0111");
            hexDecodeMap.put('8', "1000");
            hexDecodeMap.put('9', "1001");
            hexDecodeMap.put('A', "1010");
            hexDecodeMap.put('B', "1011");
            hexDecodeMap.put('C', "1100");
            hexDecodeMap.put('D', "1101");
            hexDecodeMap.put('E', "1110");
            hexDecodeMap.put('F', "1111");
        }
    }
}
