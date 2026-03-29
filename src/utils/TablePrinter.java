package utils;

import java.util.List;

public class TablePrinter {
    public static void printTable(String[] headers, List<String[]> rows) {
        int[] colWidths = new int[headers.length];

        // Tính độ rộng lớn nhất mỗi cột
        for (int i = 0; i < headers.length; i++) {
            colWidths[i] = headers[i].length();
        }

        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                if (row[i] != null) {
                    colWidths[i] = Math.max(colWidths[i], row[i].length());
                }
            }
        }

        printLine(colWidths);
        printRow(headers, colWidths, true);
        printLine(colWidths);

        for (String[] row : rows) {
            printRow(row, colWidths, false);
        }

        printLine(colWidths);
    }

    private static void printLine(int[] colWidths) {
        System.out.print("+");
        for (int width : colWidths) {
            System.out.print("-".repeat(width + 2) + "+");
        }
        System.out.println();
    }

    private static void printRow(String[] row, int[] colWidths, boolean isHeader) {
        System.out.print("|");
        for (int i = 0; i < row.length; i++) {
            String value = row[i] == null ? "" : row[i];

            if (isHeader) {
                System.out.print(" " + Color.BOLD + value + Color.RESET);
            } else {
                System.out.print(" " + value);
            }

            System.out.print(" ".repeat(colWidths[i] - value.length() + 1) + "|");
        }
        System.out.println();
    }

}
