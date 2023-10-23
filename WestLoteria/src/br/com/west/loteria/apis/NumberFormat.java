package br.com.west.loteria.apis;

public class NumberFormat {

    public static String format(Double value) {
        String[] suffix = {
                "K", "M", "B", "T", "Q", "QQ", "S", "SS", "OC", "N",
                "D", "UN", "DD",
                "TR", "QT", "QN", "SD", "SSD", "OD", "ND",
                "VG", "UVG", "DVG", "TVG", "QVG", "QVN", "SEV", "SPV", "OVG",
                "NVG",
                "TG"};
        int size = (value.intValue() != 0) ? (int) Math.log10(value.doubleValue()) : 0;
        if (size >= 3)
            while (size % 3 != 0)
                size--;
        double notation = Math.pow(10.0D, size);
        String result = (size >= 3) ? (
                String.valueOf(Math.round(value.doubleValue() / notation * 100.0D) / 100.0D) + suffix[size / 3 - 1]) : (
                new StringBuilder(String.valueOf(value.doubleValue()))).toString();
        return result;
    }
}
